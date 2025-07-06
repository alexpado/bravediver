package fr.alexpado.bravediver.web.dto;

import fr.alexpado.bravediver.entities.Stratagem;
import fr.alexpado.bravediver.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Profile", description = "Represents a user profile.")
public record ProfileDto(
        @Schema(description = "The user's discord identifier", example = "149279150648066048")
        String id,
        @Schema(description = "The user's discord username", example = "akionakao")
        String username,
        @Schema(description = "The user's list of unlocked stratagems identifiers")
        List<Integer> stratagems
) {

    public static ProfileDto from(User user) {

        return new ProfileDto(
                String.valueOf(user.getId()),
                user.getUsername(),
                user.getStratagems().stream().map(Stratagem::getId).toList()
        );
    }

}
