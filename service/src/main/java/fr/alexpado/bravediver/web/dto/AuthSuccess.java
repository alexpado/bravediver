package fr.alexpado.bravediver.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthSuccess", description = "Represents the success of an authentication attempt.")
public record AuthSuccess(
        @Schema(description = "The session id to use in the Authorization headers.", example = "3B36A22F-BE57-4341-B397-A62C68065FE5")
        String session
) {

}
