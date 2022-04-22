package ru.elias.urlshortcut.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.elias.urlshortcut.config.SpringMapperConfig;
import ru.elias.urlshortcut.dto.LinkRedirectResponseDto;
import ru.elias.urlshortcut.dto.LinkRequestDto;
import ru.elias.urlshortcut.dto.LinkResponseDto;
import ru.elias.urlshortcut.dto.StatisticResponseDto;
import ru.elias.urlshortcut.model.Link;

@Mapper(config = SpringMapperConfig.class)
public interface LinkMapper {

    @Mapping(source = "dto.url", target = "url")
    Link toEntity(LinkRequestDto dto);

    @Mapping(source = "entity.encodedUrl", target = "encodedUrl")
    LinkResponseDto toResponseDto(Link entity);

    @Mapping(source = "entity.url", target = "url")
    LinkRedirectResponseDto toRedirectResponseDto(Link entity);

    @Mapping(source = "entity.url", target = "url")
    @Mapping(source = "entity.total", target = "total")
    StatisticResponseDto toStatisticResponseDto(Link entity);

}
