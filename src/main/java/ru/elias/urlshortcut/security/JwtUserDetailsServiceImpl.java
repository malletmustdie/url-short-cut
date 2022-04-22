package ru.elias.urlshortcut.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.elias.urlshortcut.model.User;
import ru.elias.urlshortcut.security.jwt.JwtUser;
import ru.elias.urlshortcut.security.jwt.JwtUserFactory;
import ru.elias.urlshortcut.service.UserService;

@RequiredArgsConstructor
@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("loadUserByUsername - person with username: {} successfully loaded", username);
        return jwtUser;
    }

}
