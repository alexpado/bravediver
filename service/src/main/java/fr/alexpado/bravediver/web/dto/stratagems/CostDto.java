package fr.alexpado.bravediver.web.dto.stratagems;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.alexpado.bravediver.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cost", description = "Represents the cost of something.")
public record CostDto(
        @Schema(description = "The currency category type.")
        Currency currency,
        @Schema(description = "The amount of the currency needed to meet the cost requirement.")
        int amount
) {

}
