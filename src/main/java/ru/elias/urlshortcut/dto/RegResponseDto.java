package ru.elias.urlshortcut.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegResponseDto {

    private Boolean isRegistration;

    private String login;

    private String password;

}
