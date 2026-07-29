package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(
        name = "RawAlertMessageDto",
        description = "Необработанное чрезвычайное сообщение от канала "
)
public record RawAlertMessageDto(

    @Schema(
            description = "Идентификатор канала откуда поступило чрезвычайное сообщение",
            example = "678313"
    )
    @Positive
    int sourceId,

    @Schema(
            description = "Тело сообщения в сериализованном формате"
    )
    @NotNull
    byte[] body) {
}
