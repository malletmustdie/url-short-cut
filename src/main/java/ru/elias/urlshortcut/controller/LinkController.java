package ru.elias.urlshortcut.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.urlshortcut.dto.LinkRedirectResponseDto;
import ru.elias.urlshortcut.dto.LinkRequestDto;
import ru.elias.urlshortcut.dto.LinkResponseDto;
import ru.elias.urlshortcut.dto.StatisticResponseDto;
import ru.elias.urlshortcut.service.LinkService;
import ru.elias.urlshortcut.service.UserService;
import ru.elias.urlshortcut.util.ApiPathConstants;

@Api(tags = "Эндпоинты для управления ссылками")
@RequiredArgsConstructor
@RestController
@RequestMapping(ApiPathConstants.API_V_1 + ApiPathConstants.LINK)
public class LinkController {

    private final UserService userService;

    private final LinkService linkService;

    @ApiOperation("Регистрация URL")
    @PostMapping(value = ApiPathConstants.CONVERT_URL)
    public ResponseEntity<LinkResponseDto> convertUrl(@RequestBody LinkRequestDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        var user = userService.findByLogin(currentPrincipalName);
        return linkService.save(dto, user);
    }

    @ApiOperation("Переадресация URL (Выполняется без авторизации)")
    @GetMapping(value = ApiPathConstants.REDIRECT)
    public ResponseEntity<LinkRedirectResponseDto> redirect(@RequestParam("code") String code) {
        return linkService.redirect(code);
    }

    @ApiOperation("Статистика")
    @GetMapping(value = ApiPathConstants.STATISTIC)
    public ResponseEntity<List<StatisticResponseDto>> statistic() {
        return linkService.getStatistic();
    }

}
