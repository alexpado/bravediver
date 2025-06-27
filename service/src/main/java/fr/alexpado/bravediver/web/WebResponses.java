package fr.alexpado.bravediver.web;

import fr.alexpado.bravediver.entities.Stratagem;
import fr.alexpado.bravediver.entities.User;
import fr.alexpado.lib.rest.exceptions.RestException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class WebResponses {

    public @NotNull String applicationData(Iterable<Stratagem> stratagems) {

        JSONObject json           = new JSONObject();
        JSONArray  stratagemArray = new JSONArray();
        JSONArray  weaponArray    = new JSONArray();

        for (Stratagem stratagem : stratagems) {
            stratagemArray.put(stratagem.toJson());
        }

        json.put("stratagems", stratagemArray);
        json.put("weapons", weaponArray);
        return json.toString();
    }

    public @NotNull String userProfile(@Nullable User user) {

        JSONObject json           = new JSONObject();
        JSONArray  stratagemArray = new JSONArray();
        JSONArray  weaponArray    = new JSONArray();

        json.put("authenticated", Objects.nonNull(user));

        if (user != null) {
            user.getStratagems()
                .stream()
                .map(Stratagem::getId)
                .forEach(stratagemArray::put);
        }

        json.put("stratagems", stratagemArray);
        json.put("weapons", weaponArray);
        return json.toString();
    }

    public @NotNull String guest(String message) {

        JSONObject json = new JSONObject();
        json.put("authenticated", false);
        json.put("message", message);
        return json.toString();
    }

    public @NotNull String error(String message) {

        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("message", message);
        return json.toString();
    }

    public @NotNull String authResult(@NotNull UUID uuid) {

        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("authenticated", true);
        json.put("session", uuid.toString());
        return json.toString();
    }

    public @NotNull String authResult(@NotNull Exception ex) {

        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("authenticated", false);
        json.put("message", ex.getMessage());

        if (ex instanceof RestException re) {
            json.put("details", new JSONObject(new String(re.getBody())));
        }

        return json.toString();
    }

}
