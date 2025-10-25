package ru.arhipov.url_db_rest_max.config;

import ru.arhipov.url_db_rest_max.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("PasswordEncoder created");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        log.info("UserDetailsService created");
        return username -> {
            log.info("🔍 Searching for user: {}", username);
            return userRepository.findByUsername(username)
                    .map(user -> {
                        log.info("User found: {}, roles: {}", user.getUsername(), user.getRoles());
                        //преобразование ролей для Spring Security
                        String[] roles = user.getRoles().stream()
                                .map(role -> {
                                    if (role.equals("READ_ONLY")) return "READ_ONLY";
                                    if (role.equals("USER")) return "USER";
                                    if (role.equals("ADMIN")) return "ADMIN";
                                    return role;
                                })
                                .toArray(String[]::new);

                        log.info("Roles for Security: {}", String.join(", ", roles));

                        return org.springframework.security.core.userdetails.User.builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .roles(roles)
                                .build();
                    })
                    .orElseThrow(() -> {
                        log.error("User not found! {}", username);
                        return new UsernameNotFoundException("User not found: " + username);
                    });
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        log.info("DaoAuthenticationProvider created");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain");

        http.authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authz -> authz
                        // Разрешаем доступ к статическим ресурсам и основным страницам
                        .requestMatchers("/", "/home", "/about", "/css/**", "/js/**", "/images/**").permitAll()
                        // Разрешаем доступ к H2 console (для разработки)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Разрешаем доступ к Actuator endpoints
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        // Actuator endpoints только для ADMIN
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        // Регистрация и логин доступны всем
                        .requestMatchers("/register", "/login").permitAll()
                        // Админские пути только для ADMIN
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        //Операции создания/редактирования/удаления
                        .requestMatchers("/books/create", "/books/edit/**", "/books/delete/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/bookstores/create", "/bookstores/edit/**", "/bookstores/delete/**").hasAnyRole("USER", "ADMIN")

                        // Просмотр списков доступен всем аутентифицированным
                        .requestMatchers("/books", "/bookstores", "/calculations/**").authenticated()

                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // ✅ ИСПРАВЛЕНИЕ
                        .logoutSuccessUrl("/?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                // Отключаем CSRF для H2 console
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                // Разрешаем frames для H2 console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                );
        return http.build();
    }
}