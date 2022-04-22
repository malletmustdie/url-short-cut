package ru.elias.urlshortcut.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.elias.urlshortcut.model.Role;
import ru.elias.urlshortcut.model.User;

@UtilityClass
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                true,
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> personRoles) {
        return personRoles.stream()
                          .map(role -> new SimpleGrantedAuthority(role.getName()))
                          .collect(Collectors.toList());
    }

}
