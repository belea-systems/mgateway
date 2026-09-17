package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(
        name = "ErrorResponseDto",
        description = "Ответ в случае возникновения ошибок"
)
public record ErrorResponseDto(
        @Schema(
                description = "Время возникновения ошибки отно",
                example = "2007-12-03T10:15:30"
        )
        Instant occurredAt,

        @Schema(
                description = "Описание ошибки",
                example = "Получене неверный формат данных. Необходимо предоставить сведения в правильном виде"
        )
        String description
) {
        public ErrorResponseDto(String description) {
            this(Instant.now(), description);
        }
}
