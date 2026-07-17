package org.hishatakaran.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public AuthenticationManager authenticationManager() {

    DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider(
            userDetailsService
        );

    provider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(provider);
  }


  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http
  ) {

    return http
        .csrf(csrf -> csrf
            .ignoringRequestMatchers(
                "/api/auth/login"
            )
            .csrfTokenRepository(
                CookieCsrfTokenRepository.withHttpOnlyFalse()
            )
        )
        .cors(cors -> {})
        .securityContext(context -> context
            .securityContextRepository(
                securityContextRepository()
            )
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/login",
                "/api/auth/csrf"
            )
            .permitAll()
            .requestMatchers(
                "/api/auth/me",
                "/api/auth/logout",
                "/api/admin/**"
            )
            .authenticated()
            .anyRequest()
            .permitAll()
        )
        .build();
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {

    return new HttpSessionSecurityContextRepository();

  }
}