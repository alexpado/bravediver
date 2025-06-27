package fr.alexpado.bravediver.web.oauth.packets;

import fr.alexpado.bravediver.web.oauth.results.DiscordIdentity;
import fr.alexpado.bravediver.web.oauth.results.UserToken;
import fr.alexpado.lib.rest.RestAction;
import fr.alexpado.lib.rest.enums.RequestMethod;
import fr.alexpado.lib.rest.interfaces.IRestResponse;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoPacket extends RestAction<DiscordIdentity> {

    private final UserToken token;

    public UserInfoPacket(UserToken token) {

        this.token = token;
    }

    @Override
    public @NotNull RequestMethod getRequestMethod() {

        return RequestMethod.GET;
    }

    @Override
    public @NotNull String getRequestURL() {

        return "https://discordapp.com/api/users/@me";
    }

    @Override
    public @NotNull Map<String, String> getRequestHeaders() {

        return new HashMap<>() {{
            this.put("User-Agent", "Bravediver/JavaSpring");
            this.put("Accept-Language", "en-US,en;q=0.5");
            this.put("Authorization", "Bearer " + UserInfoPacket.this.token.getAccessToken());
        }};
    }

    @Override
    public DiscordIdentity convert(IRestResponse response) {

        byte[]     body    = response.getBody();
        String     content = new String(body);
        JSONObject json    = new JSONObject(content);
        return new DiscordIdentity(json);
    }

}
