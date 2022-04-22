package ru.elias.urlshortcut.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.urlshortcut.dto.RegRequestDto;
import ru.elias.urlshortcut.dto.RegResponseDto;
import ru.elias.urlshortcut.service.UserService;
import ru.elias.urlshortcut.util.ApiPathConstants;

@Api(tags = "Эндпоинты управления пользователями")
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.USER)
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPathConstants.CREATE_USER)
    public ResponseEntity<RegResponseDto> saveUser(@RequestBody RegRequestDto userDto) {
        return userService.save(userDto);
    }

}
