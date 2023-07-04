package com.example.farajaplatform.repository;

import com.example.farajaplatform.model.Person;
import com.example.farajaplatform.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByToken(String token);

    void deleteByPerson(Person person);
}
