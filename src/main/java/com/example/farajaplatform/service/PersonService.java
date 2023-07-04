package com.example.farajaplatform.service;

import com.example.farajaplatform.exception.EmailFailureException;
import com.example.farajaplatform.exception.UserAlreadyExistsException;
import com.example.farajaplatform.model.Person;
import com.example.farajaplatform.model.VerificationToken;
import com.example.farajaplatform.repository.PersonRepository;
import com.example.farajaplatform.repository.VerificationTokenRepository;
import com.example.farajaplatform.security.SecurityConfig;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    Person person;
    @Autowired
    EmailService emailService;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    SecurityConfig securityConfig;

    public Person registerPerson(Person person) throws UserAlreadyExistsException, EmailFailureException {
        if (personRepository.findByEmailIgnoreCase(person.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        person.setPassword(securityConfig.passwordEncoder().encode(person.getPassword()));
        VerificationToken verificationToken = createVerificationToken(person);
        emailService.sendVerificationEmail(verificationToken);
        return personRepository.save(person);
    }

    /**
     * Creates a VerificationToken object for sending to the person.
     * @param person The person the token is being generated for.
     * @return The object created.
     */
    private VerificationToken createVerificationToken(Person person) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(person));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setPerson(person);
        person.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    @Transactional
    public boolean verifyPerson(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            Person person = verificationToken.getPerson();
            if (!person.isEmailVerified()) {
                person.setEmailVerified(true);
                personRepository.save(person);
                verificationTokenRepository.deleteByPerson(person);
                return true;
            }
        }
        return false;
    }
}
