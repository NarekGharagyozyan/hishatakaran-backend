package org.hishatakaran.backend.service;

import org.hishatakaran.backend.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {


    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String login)
        throws UsernameNotFoundException {


        var admin = adminRepository
            .findByLogin(login)
            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "Admin not found"
                )
            );


        return User.builder()
            .username(admin.getLogin())
            .password(admin.getPasswordHash())
            .build();
    }
}