package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "MessageSourceDataDto",
        description = "Информация об источинике сообщений"
)
public record MessageSourceDataDto(
        @Schema(
                description = "Идентификатор источника сообщений в системе источника",
                example = "19933748182"
        )
        String id,

        @Schema(
                description = "Наименование источника сообщений",
                example = "Белгородец"
        )
        String name,

        @Schema(
                description = "Значение перечисления, описывающее систему источника сообщений",
                example = "TELEGRAM",
                allowableValues = {
                        "TELEGRAM"
                }
        )
        String source
) {
}
