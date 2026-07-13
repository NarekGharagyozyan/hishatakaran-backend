package org.hishatakaran.backend.configuration;

import java.time.LocalDateTime;

import org.hishatakaran.backend.entity.Admin;
import org.hishatakaran.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {


    private final AdminRepository repository;

    private final PasswordEncoder encoder;


    @Override
    public void run(String... args){


        if(repository.findByLogin("admin").isEmpty()){


            Admin admin = new Admin();

            admin.setLogin("admin");

            admin.setPasswordHash(
                encoder.encode(
                    "ChangeMe123!"
                )
            );

            repository.save(admin);

        }

    }

}