package ru.elias.urlshortcut.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.elias.urlshortcut.dto.RegRequestDto;
import ru.elias.urlshortcut.dto.RegResponseDto;
import ru.elias.urlshortcut.mapper.UserMapper;
import ru.elias.urlshortcut.model.User;
import ru.elias.urlshortcut.repository.UserRepository;
import ru.elias.urlshortcut.service.UserService;
import ru.elias.urlshortcut.util.EncodeUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ApplicationContext applicationContext;

    @Transactional
    @Override
    public ResponseEntity<RegResponseDto> save(RegRequestDto userDto) {
        var encoder = applicationContext.getBean("passwordEncoder", BCryptPasswordEncoder.class);
        var userEntity = userMapper.toEntity(userDto);
        boolean isExist = userRepository.existsByUsername(userEntity.getUsername());
        if (!isExist) {
            var login = EncodeUtil.generateToken();
            var password = EncodeUtil.generateToken();
            userEntity.setLogin(login);
            userEntity.setPassword(encoder.encode(password));
            userEntity.setDecodedPassword(password);
            userRepository.save(userEntity);
            return ResponseEntity.ok(
                    RegResponseDto.builder()
                                  .isRegistration(isExist)
                                  .login(userEntity.getLogin())
                                  .password(userEntity.getDecodedPassword())
                                  .build()
            );
        } else {
            return new ResponseEntity<>(
                    RegResponseDto.builder()
                                  .isRegistration(isExist)
                                  .build(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
