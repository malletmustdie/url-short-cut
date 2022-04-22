package ru.elias.urlshortcut.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import ru.elias.urlshortcut.dto.ErrorResponseDto;

@Slf4j
@ControllerAdvice
public class Http401EntryPoint implements AuthenticationEntryPoint {

    public static Http401EntryPoint unauthorizedHandler() {
        return new Http401EntryPoint();
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream()
                .println(mapper.writeValueAsString(
                        ErrorResponseDto.builder()
                                        .status("unauthorized")
                                        .msg("invalid request")
                                        .build()
                         )
                );
    }

}
