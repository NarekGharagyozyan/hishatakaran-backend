package org.hishatakaran.backend.controller;

import java.util.Map;

import org.hishatakaran.backend.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;


    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @RequestBody LoginRequest request,
        HttpServletRequest httpRequest,
        HttpServletResponse httpResponse

    ) {

        Authentication authentication =
            authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                    request.login(),
                    request.password()
                )
            );

        SecurityContext context =
            SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
            context,
            httpRequest,
            httpResponse
        );

        return ResponseEntity.ok().build();
    }



    @PostMapping("/logout")
    public ResponseEntity<Void> logout(

        HttpServletRequest request,

        HttpServletResponse response,

        Authentication authentication

    ) {

        new SecurityContextLogoutHandler()
            .logout(
                request,
                response,
                authentication
            );

        return ResponseEntity.ok().build();
    }



    @GetMapping("/me")
    public ResponseEntity<?> me(
        Authentication authentication
    ) {

        return ResponseEntity.ok(
            Map.of(
                "login",
                authentication.getName()
            )
        );
    }

}