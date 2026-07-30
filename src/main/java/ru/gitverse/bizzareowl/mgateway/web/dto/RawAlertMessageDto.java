package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "RawAlertMessageDto",
        description = "Необработанное чрезвычайное сообщение от канала"
)
public record RawAlertMessageDto(
        @Schema(description = "Текст чрезвычайного сообщения")
        String message,

        @Schema(
                description = "Тело сообщения в сериализованном формате"
        )
        @NotNull
        MessageSourceDataDto messageData) {
}
