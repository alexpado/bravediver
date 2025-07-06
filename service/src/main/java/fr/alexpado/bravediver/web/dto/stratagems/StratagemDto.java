package fr.alexpado.bravediver.web.dto.stratagems;

import fr.alexpado.bravediver.entities.Stratagem;
import fr.alexpado.bravediver.enums.StratagemType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Stratagem", description = "Represents details about a stratagem.")
public record StratagemDto(
        @Schema(description = "The stratagem's identifier.")
        int id,
        @Schema(description = "The stratagem's name.", example = "MG-43 Machine Gun")
        String name,
        @Schema(description = "The path leading to the stratagem's image (without the host).", example = "/assets/images/stratagems/Machine_Gun_Stratagem_Icon.webp")
        String image,
        @Schema(description = "The stratagem's category type.")
        StratagemType type,
        @Schema(description = "The stratagem's cost (null if free).")
        CostDto cost,
        @Schema(description = "The stratagem's unlock level. If set to 0 it means that the stratagem is unlocked on a new account.", minimum = "0", maximum = "150")
        int unlockLevel
) {

    public static StratagemDto from(Stratagem stratagem) {

        CostDto costDto = null;
        if (stratagem.getCost() > 0) {
            costDto = new CostDto(stratagem.getCurrency(), stratagem.getCost());
        }

        return new StratagemDto(
                stratagem.getId(),
                stratagem.getName(),
                String.format("/assets/images/stratagems/" + stratagem.getImage()),
                stratagem.getType(),
                costDto,
                stratagem.getUnlockLevel()
        );
    }

}
