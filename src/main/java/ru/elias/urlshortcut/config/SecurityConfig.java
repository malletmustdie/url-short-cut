package ru.elias.urlshortcut.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.elias.urlshortcut.security.Http401EntryPoint;
import ru.elias.urlshortcut.security.jwt.JwtConfigurer;
import ru.elias.urlshortcut.security.jwt.JwtTokenProvider;
import ru.elias.urlshortcut.util.ApiPathConstants;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(ApiPathConstants.AUTH_WHITELIST).permitAll()
                .antMatchers(ApiPathConstants.REG_ENDPOINT).permitAll()
                .antMatchers(ApiPathConstants.SIGN_UP_ENDPOINT).permitAll()
                .antMatchers(ApiPathConstants.REDIRECT_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(Http401EntryPoint.unauthorizedHandler());
    }

}
