package fr.alexpado.bravediver.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "PostProfile", description = "Used as request body to update a user preferences.")
public record PostProfileDto(
        @Schema(description = "The user's list of unlocked stratagems identifiers")
        List<Integer> stratagems
) {

}
