package ru.elias.urlshortcut.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import ru.elias.urlshortcut.dto.LinkRedirectResponseDto;
import ru.elias.urlshortcut.dto.LinkRequestDto;
import ru.elias.urlshortcut.dto.LinkResponseDto;
import ru.elias.urlshortcut.dto.StatisticResponseDto;
import ru.elias.urlshortcut.model.User;

public interface LinkService {

    ResponseEntity<LinkResponseDto> save(LinkRequestDto link, User user);

    ResponseEntity<LinkRedirectResponseDto> redirect(String code);

    ResponseEntity<List<StatisticResponseDto>> getStatistic();

    void updateLinkStatistic(Long id);

}
