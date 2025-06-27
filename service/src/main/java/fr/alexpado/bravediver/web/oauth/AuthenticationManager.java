package fr.alexpado.bravediver.web.oauth;

import fr.alexpado.bravediver.entities.User;
import fr.alexpado.bravediver.web.oauth.packets.AuthTokenPacket;
import fr.alexpado.bravediver.web.oauth.packets.RefreshTokenPacket;
import fr.alexpado.bravediver.web.oauth.packets.UserInfoPacket;
import fr.alexpado.bravediver.web.oauth.results.DiscordIdentity;
import fr.alexpado.bravediver.web.oauth.results.UserToken;
import fr.alexpado.bravediver.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthenticationManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManager.class);

    private static final long INACTIVITY_TIMEOUT  = Duration.of(1, ChronoUnit.HOURS).getSeconds();
    private static final long TOKEN_REFRESH_LIMIT = Duration.of(10, ChronoUnit.MINUTES).getSeconds();

    private final DiscordConfiguration configuration;
    private final UserService          userService;

    private final Map<UUID, UserToken>       tokens     = new HashMap<>();
    private final Map<UUID, DiscordIdentity> identities = new HashMap<>();

    public AuthenticationManager(DiscordConfiguration configuration, UserService userService) {

        this.configuration = configuration;
        this.userService   = userService;
    }

    @Scheduled(cron = "0 */5 * * * *")
    private void checkTokens() {

        Collection<UUID> remove = new ArrayList<>();

        for (Map.Entry<UUID, UserToken> entry : this.tokens.entrySet()) {
            UUID      uuid      = entry.getKey();
            UserToken userToken = entry.getValue();

            if (userToken.isInactive(INACTIVITY_TIMEOUT)) {
                // TODO: Invalidate token
                remove.add(uuid);
                continue;
            }

            if (userToken.willExpire(TOKEN_REFRESH_LIMIT)) {
                try {
                    new RefreshTokenPacket(userToken).complete();
                } catch (Exception e) {
                    LOGGER.warn("Could not refresh the session {}, logging out...", uuid);
                    remove.add(uuid);
                }
            }
        }

        // Do not keep invalid session in cache.
        remove.forEach(this.tokens::remove);
        remove.forEach(this.identities::remove);
    }

    public UUID authenticate(String code) throws Exception {

        LOGGER.info("Authentication request using code {}", code);
        AuthTokenPacket authTokenPacket = new AuthTokenPacket(this.configuration, code);
        UserToken       userToken       = authTokenPacket.complete();

        LOGGER.info("Authentication successful: {}", userToken);
        LOGGER.info("Requesting user identity...");

        UserInfoPacket  userInfoPacket = new UserInfoPacket(userToken);
        DiscordIdentity identity       = userInfoPacket.complete();

        LOGGER.info("Welcome ! ({})", identity);
        LOGGER.info("Ensuring database sync...");

        this.userService.ensureUserExists(identity);

        UUID uuid = UUID.randomUUID();

        LOGGER.info("Session {} created.", uuid);

        this.tokens.put(uuid, userToken);
        this.identities.put(uuid, identity);
        return uuid;
    }

    public Optional<User> getUserFromSession(String session) {

        try {
            UUID uuid = UUID.fromString(session);
            return Optional.ofNullable(this.identities.get(uuid))
                           .flatMap(this.userService::findFromIdentity);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
