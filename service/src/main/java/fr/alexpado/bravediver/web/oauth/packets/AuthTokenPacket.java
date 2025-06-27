package fr.alexpado.bravediver.web.oauth.packets;

import fr.alexpado.bravediver.web.oauth.DiscordConfiguration;
import fr.alexpado.bravediver.web.oauth.results.UserToken;
import fr.alexpado.lib.rest.RestAction;
import fr.alexpado.lib.rest.enums.RequestMethod;
import fr.alexpado.lib.rest.interfaces.IRestResponse;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenPacket extends RestAction<UserToken> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenPacket.class);

    private final DiscordConfiguration configuration;
    private final String               code;

    public AuthTokenPacket(DiscordConfiguration configuration, String code) {

        this.configuration = configuration;
        this.code          = code;
    }

    @Override
    public @NotNull RequestMethod getRequestMethod() {

        return RequestMethod.POST;
    }

    @Override
    public @NotNull String getRequestURL() {

        return "https://discordapp.com/api/oauth2/token";
    }

    @Override
    public @NotNull Map<String, String> getRequestHeaders() {

        return new HashMap<>() {{
            this.put("User-Agent", "Bravediver/JavaSpring");
            this.put("Accept-Language", "en-US,en;q=0.5");
            this.put("Content-Type", "application/x-www-form-urlencoded");
        }};
    }

    @Override
    public @NotNull String getRequestBody() {

        return "client_id=%s&client_secret=%s&grant_type=authorization_code&code=%s&redirect_uri=%s&scope=%s".formatted(
                this.configuration.getClientId(),
                this.configuration.getClientSecret(),
                this.code,
                URLEncoder.encode(this.configuration.getRedirectUri(), StandardCharsets.UTF_8),
                this.configuration.getScope()
        );
    }

    @Override
    public UserToken convert(IRestResponse response) {

        byte[]     body    = response.getBody();
        String     content = new String(body);
        JSONObject json    = new JSONObject(content);
        return new UserToken(json);
    }

}
