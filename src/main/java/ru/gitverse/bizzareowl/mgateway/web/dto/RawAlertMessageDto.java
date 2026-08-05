package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Schema(
        name = "RawAlertMessageDto",
        description = "Необработанное чрезвычайное сообщение от канала"
)
public record RawAlertMessageDto(
        @Schema(description = "Текст чрезвычайного сообщения")
        @NotNull
        String message,

        @Schema(description = "Идентификатор сообщения в системе-источнике")
        @NotNull
        @NotBlank
        String messageId,

        @Schema(
                description = "Время отправки сообщения в системе-источнике",
                example = "2026-08-03T09:15:00+03:00"
        )
        @NotNull
        Instant sentAt,

        @Schema(
                description = "Идентификатор сообщения",
                example = "818888389123"
        )
        String messageId,

        @Schema(
                description = "Время отправки сообщения в формате ISO с часовой зоной UTC+0",
                example = "2023-01-01T12:23:43Z"
        )
        Instant sentAt,

        @Schema(
                description = "Тело сообщения в сериализованном формате",
                implementation = MessageSourceDataDto.class
        )
        @NotNull
        MessageSourceDataDto messageData) {
}
