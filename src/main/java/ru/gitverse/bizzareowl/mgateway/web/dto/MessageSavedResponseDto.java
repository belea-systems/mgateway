package ru.gitverse.bizzareowl.mgateway.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(
        name = "MessageSavedResponseDto",
        description = "Ответ в случае успешно принятого чрезвычайного сообщения"
)
public record MessageSavedResponseDto(
        @Schema(
                description = "UUID принятого чрезвычайного сообщения",
                example = "1ef2963b-90c8-4a41-8e12-e9dda3ebaea3"
        ) UUID messageId
) {
}
