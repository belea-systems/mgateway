package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "MessageSourceDataDto",
        description = "Информация об источинике сообщений"
)
public record MessageSourceDataDto(
        @Schema(
                description = "Идентификатор источника сообщений в системе источника",
                example = "19933748182"
        )
        @NotBlank
        @NotBlank
        String id,

        @Schema(
                description = "Наименование источника сообщений",
                example = "Белгородец"
        )
        @NotNull
        @NotBlank
        String name,

        @Schema(
                description = "Значение перечисления, описывающее систему источника сообщений",
                example = "TELEGRAM",
                allowableValues = {
                        "TELEGRAM"
                }
        )
        @NotNull
        @NotBlank
        String source
) {
}
