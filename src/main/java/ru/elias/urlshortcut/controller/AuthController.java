package ru.elias.urlshortcut.controller;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.urlshortcut.dto.AuthRequestDto;
import ru.elias.urlshortcut.dto.AuthResponseDto;
import ru.elias.urlshortcut.dto.ErrorResponseDto;
import ru.elias.urlshortcut.model.User;
import ru.elias.urlshortcut.security.jwt.JwtTokenProvider;
import ru.elias.urlshortcut.service.UserService;
import ru.elias.urlshortcut.util.ApiPathConstants;

@Slf4j
@Api(tags = "Эндпоинт для получения токена пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.AUTHENTICATION)
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    @ApiOperation("Авторизация на сервере")
    @PostMapping(value = ApiPathConstants.LOGIN)
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        try {
            String login = requestDto.getLogin();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, requestDto.getPassword())
            );
            User user = userService.findByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "Person with login " + login + " not found"
                );
            }
            String token = jwtTokenProvider.createToken(login, user.getRoles());
            return ResponseEntity.status(HttpStatus.OK)
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(AuthResponseDto.builder()
                                                      .username(login)
                                                      .token(token)
                                                      .build());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
    public void exceptionHandler(Exception e, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ErrorResponseDto.builder()
                                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                        .msg(e.getMessage())
                                        .type(e.getClass().toString())
                                        .build()
                )
        );
        log.error(e.getLocalizedMessage());
    }

}
