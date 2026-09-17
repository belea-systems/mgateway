package ru.gitverse.bizzareowl.mgateway.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSavedResponseDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;


@OpenAPIDefinition(
        info = @Info(
                title = "mgateway",
                description = "Специализированное API для получения оповещений об атаках на Белгородскую область из различных источников.",
                version = "0.1.0",
                contact = @Contact(
                        name = "bizzare_owl",
                        url = "https://gitverse.ru/bizzare_owl",
                        email = "yummypotatopie@yandex.ru"
                )
        ),
        security = @SecurityRequirement(name = "jwt_auth_req")
)
@Tag(
        name = "Отправка сообщений",
        description = "Эндпоинты для отправки чрезвычайных сообщений в систему"
)
public interface MessageController {

    @Operation(
            summary = "Отправка чрезвычайного сообщения в систему",
            description = "Отправка чрезвычайного сообщения в систему, для последующего сохранения и анализа",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ответ при успешно сохраненном сообщении",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageSavedResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ответ в случае если сообщение содержит ошибки и не может быть принято",
                            content = @Content(
                                    schema = @Schema(description = "Problem Details RFC 9457"),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    ResponseEntity<MessageSavedResponseDto> sendMessage(
            @RequestBody(
                    description = "Чрезвычайное сообщение с сериализованным телом сообщения",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RawAlertMessageDto.class)
                    )
            ) RawAlertMessageDto rawAlertMessageDto
    );

}
