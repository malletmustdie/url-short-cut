package ru.elias.urlshortcut.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel("Объект ответа с jwt-токеном пользователя после авторизации")
@Data
@Builder
public class AuthResponseDto {

    @ApiModelProperty("Имя пользователя")
    private String username;

    @ApiModelProperty("Токен пользователя")
    private String token;

}
