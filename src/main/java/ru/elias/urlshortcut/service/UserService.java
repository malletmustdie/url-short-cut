package ru.elias.urlshortcut.service;

import org.springframework.http.ResponseEntity;
import ru.elias.urlshortcut.dto.RegRequestDto;
import ru.elias.urlshortcut.dto.RegResponseDto;
import ru.elias.urlshortcut.model.User;

public interface UserService {

    ResponseEntity<RegResponseDto> save(RegRequestDto userDto);

    User findByLogin(String login);

}
