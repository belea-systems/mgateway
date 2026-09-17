package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "RawAlertMessageDto",
        description = "Необработанное чрезвычайное сообщение от канала "
)
public record RawAlertMessageDto(

    @Schema(
            description = "Идентификатор канала откуда поступило чрезвычайное сообщение",
            example = "678313"
    )
    int sourceId,

    @Schema(
            description = "Тело сообщения в сериализованном формате"
    )
    byte[] body) {
}
