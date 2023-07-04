package com.example.farajaplatform.repository;

import com.example.farajaplatform.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findByUsername(String username);
    Boolean existsByUsername(String username);
}
