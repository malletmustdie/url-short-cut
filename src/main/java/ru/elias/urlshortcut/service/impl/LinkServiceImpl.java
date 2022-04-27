package ru.elias.urlshortcut.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.elias.urlshortcut.aop.LogExecutionTime;
import ru.elias.urlshortcut.dto.LinkRedirectResponseDto;
import ru.elias.urlshortcut.dto.LinkRequestDto;
import ru.elias.urlshortcut.dto.LinkResponseDto;
import ru.elias.urlshortcut.dto.StatisticResponseDto;
import ru.elias.urlshortcut.mapper.LinkMapper;
import ru.elias.urlshortcut.model.Link;
import ru.elias.urlshortcut.model.User;
import ru.elias.urlshortcut.repository.LinkRepository;
import ru.elias.urlshortcut.service.LinkService;
import ru.elias.urlshortcut.util.EncodeUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    private final LinkMapper linkMapper;

    @Transactional
    @Override
    public ResponseEntity<LinkResponseDto> save(LinkRequestDto linkDto, User user) {
        return Optional.of(linkDto)
                       .map(dto -> getLinkEntity(user, dto))
                       .map(linkRepository::save)
                       .map(linkMapper::toResponseDto)
                       .map(linkResponseDto -> new ResponseEntity<>(linkResponseDto, HttpStatus.CREATED))
                       .orElseThrow();
    }

    @Override
    public ResponseEntity<LinkRedirectResponseDto> redirect(String code) {
        return linkRepository.findByEncodedUrl(code)
                             .map(link -> {
                                 updateLinkStatistic(link.getId());
                                 return linkMapper.toRedirectResponseDto(link);
                             })
                             .map(responseDto -> new ResponseEntity<>(responseDto, HttpStatus.FOUND))
                             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @LogExecutionTime
    @Override
    public ResponseEntity<List<StatisticResponseDto>> getStatistic() {
        return ResponseEntity.ok(linkRepository.findAll()
                                               .stream()
                                               .map(linkMapper::toStatisticResponseDto)
                                               .collect(Collectors.toList())
        );
    }

    @LogExecutionTime
    @Transactional
    @Override
    public void updateLinkStatistic(Long id) {
        linkRepository.incrementTotalColumn(id);
    }

    private Link getLinkEntity(User user, LinkRequestDto dto) {
        var linkEntity = linkMapper.toEntity(dto);
        linkEntity.setEncodedUrl(EncodeUtil.encryptMD5(dto.getUrl()));
        linkEntity.setTotal(0L);
        user.addLink(linkEntity);
        return linkEntity;
    }

}
