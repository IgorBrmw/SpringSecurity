package com.example.IgorWebApp30.configs;

import com.example.IgorWebApp30.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationSuccessHandler successHandler;

    public WebSecurityConfig(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

//    @Bean
//    public SecurityFilterChain mySpringSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
//                        .requestMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
//                        .requestMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
//                        .requestMatchers("/delete/**").hasAuthority("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(login -> login.permitAll())
//                .logout(logout -> logout.permitAll())
//                .exceptionHandling(eh -> eh.accessDeniedPage("/403"))
//        ;
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public/**").permitAll() // Разрешить доступ всем
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // Доступ для USER и ADMIN
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Доступ только для ADMIN
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                )
                .formLogin(login -> login.successHandler(successHandler)
                        // Не указываем loginPage, чтобы использовать стандартную страницу логина
                        .permitAll() // Разрешить доступ к странице логина всем
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Перенаправление после выхода на главную страницу
                        .permitAll() // Разрешить выход всем
                )
                .exceptionHandling(eh -> eh
                        .accessDeniedPage("/403") // Страница для ошибки доступа
                );

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
