package fr.alexpado.bravediver.web.oauth.results;

import org.json.JSONObject;

public class DiscordIdentity {

    private final long   id;
    private final String username;
    private final String discriminator;
    private final String global_name;
    private final String avatar;

    public DiscordIdentity(JSONObject json) {

        this.id            = Long.parseLong(json.getString("id"));
        this.username      = json.getString("username");
        this.discriminator = json.getString("discriminator");
        this.global_name   = json.getString("global_name");
        this.avatar        = json.getString("avatar");
    }

    public long getId() {

        return this.id;
    }

    public String getUsername() {

        return this.username;
    }

    public String getDiscriminator() {

        return this.discriminator;
    }

    public String getGlobal_name() {

        return this.global_name;
    }

    public String getAvatar() {

        return this.avatar;
    }

    @Override
    public String toString() {

        return "DiscordIdentity{id=%d, username='%s', discriminator='%s', global_name='%s', avatar='%s'}".formatted(
                this.id,
                this.username,
                this.discriminator,
                this.global_name,
                this.avatar
        );
    }

}
