package app.config;

import app.service.security.AccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new AccountDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/main/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()

                        .requestMatchers("/api/v1/edit-concerts/get-all/**").permitAll()
                        .requestMatchers("/api/v1/edit-concerts/get/**").permitAll()
                        .requestMatchers("/api/v1/edit-concerts/add/**").authenticated()
                        .requestMatchers("/api/v1/edit-concerts/delete/**").authenticated()
                        .requestMatchers("/api/v1/edit-concerts/update/**").authenticated()

                        .requestMatchers("/api/v1/edit-music/get-all/**").permitAll()
                        .requestMatchers("/api/v1/edit-music/get/**").permitAll()
                        .requestMatchers("/api/v1/edit-music/add/**").authenticated()
                        .requestMatchers("/api/v1/edit-music/delete/**").authenticated()
                        .requestMatchers("/api/v1/edit-music/update/**").authenticated()

                        .requestMatchers("/api/v1/edit-users/**").authenticated()

                        .requestMatchers("/api/v1/edit-shop/get-all/**").permitAll()
                        .requestMatchers("/api/v1/edit-shop/get/**").permitAll()
                        .requestMatchers("/api/v1/edit-shop/add/**").authenticated()
                        .requestMatchers("/api/v1/edit-shop/delete/**").authenticated()
                        .requestMatchers("/api/v1/edit-shop/update/**").authenticated()

                        .requestMatchers("/api/v1/s3bucket-storage/download/**").permitAll()
                        .requestMatchers("/api/v1/s3bucket-storage/upload/**").authenticated()
                        .requestMatchers("/api/v1/s3bucket-storage/delete/**").authenticated()

                        .requestMatchers("/api/v1/").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
