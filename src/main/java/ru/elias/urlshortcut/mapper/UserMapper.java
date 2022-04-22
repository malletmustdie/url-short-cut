package ru.elias.urlshortcut.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.elias.urlshortcut.config.SpringMapperConfig;
import ru.elias.urlshortcut.dto.RegRequestDto;
import ru.elias.urlshortcut.dto.RegResponseDto;
import ru.elias.urlshortcut.model.User;

@Mapper(config = SpringMapperConfig.class)
public interface UserMapper {

    @Mapping(source = "dto.username", target = "username")
    User toEntity(RegRequestDto dto);

    @Mapping(source = "entity.login", target = "login")
    @Mapping(source = "entity.password", target = "password")
    RegResponseDto toResponseDto(User entity);

}
