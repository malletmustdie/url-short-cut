package ru.elias.urlshortcut.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elias.urlshortcut.dto.LinkRedirectResponseDto;
import ru.elias.urlshortcut.dto.LinkRequestDto;
import ru.elias.urlshortcut.dto.LinkResponseDto;
import ru.elias.urlshortcut.dto.StatisticResponseDto;
import ru.elias.urlshortcut.mapper.LinkMapper;
import ru.elias.urlshortcut.model.User;
import ru.elias.urlshortcut.repository.LinkRepository;
import ru.elias.urlshortcut.service.LinkService;
import ru.elias.urlshortcut.util.EncodeUtil;

@RequiredArgsConstructor
@Service
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    private final LinkMapper linkMapper;

    @Transactional
    @Override
    public ResponseEntity<LinkResponseDto> save(LinkRequestDto linkDto, User user) {
        var linkEntity = linkMapper.toEntity(linkDto);
        linkEntity.setEncodedUrl(EncodeUtil.encryptMD5(linkDto.getUrl()));
        linkEntity.setTotal(0L);
        user.addLink(linkEntity);
        var link = linkRepository.save(linkEntity);
        return ResponseEntity.ok(linkMapper.toResponseDto(link));
    }

    @Transactional
    @Override
    public ResponseEntity<LinkRedirectResponseDto> redirect(String code) {
        var link = linkRepository.findByEncodedUrl(code);
        updateLinkStatistic(link.getId());
        var result = linkMapper.toRedirectResponseDto(link);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @Transactional
    @Override
    public ResponseEntity<List<StatisticResponseDto>> getStatistic() {
        return ResponseEntity.ok(linkRepository.findAll()
                                               .stream()
                                               .map(linkMapper::toStatisticResponseDto)
                                               .collect(Collectors.toList())
        );
    }

    @Transactional
    @Override
    public void updateLinkStatistic(Long id) {
        linkRepository.incrementTotalColumn(id);
    }

}
