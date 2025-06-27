package fr.alexpado.bravediver.web.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bravediver.discord")
public class DiscordConfiguration {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;

    public String getClientId() {

        return this.clientId;
    }

    public void setClientId(String clientId) {

        this.clientId = clientId;
    }

    public String getClientSecret() {

        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {

        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {

        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {

        this.redirectUri = redirectUri;
    }

    public String getScope() {

        return this.scope;
    }

    public void setScope(String scope) {

        this.scope = scope;
    }

}
