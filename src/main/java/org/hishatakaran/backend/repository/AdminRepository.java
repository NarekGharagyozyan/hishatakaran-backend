package org.hishatakaran.backend.repository;

import java.util.Optional;

import org.hishatakaran.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByLogin(String login);

}