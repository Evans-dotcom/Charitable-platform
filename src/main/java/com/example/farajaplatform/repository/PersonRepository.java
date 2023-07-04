package com.example.farajaplatform.repository;

import com.example.farajaplatform.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Integer> {

    Optional<Person> findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
    Optional<Person> findById(Integer id);
}
