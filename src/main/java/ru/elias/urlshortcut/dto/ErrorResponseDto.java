package ru.elias.urlshortcut.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String status;

    private String msg;

    private String type;

}
