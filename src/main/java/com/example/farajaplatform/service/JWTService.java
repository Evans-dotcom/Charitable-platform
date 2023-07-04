package com.example.farajaplatform.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.farajaplatform.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

        /** The secret key to encrypt the JWTs with. */
        @Value("${jwt.algorithm.key}")
        private String algorithmKey;
        /** The issuer the JWT is signed with. */
        @Value("${jwt.issuer}")
        private String issuer;
        /** How many seconds from generation should the JWT expire? */
        @Value("${jwt.expiryInSeconds}")
        private int expiryInSeconds;
        /** The algorithm generated post construction. */
        private Algorithm algorithm;
        /** The JWT claim key for the username. */
        private static final String FIRSTNAME_KEY = "FIRSTNAME";
        private static final String EMAIL_KEY = "EMAIL";

        /**
         * Post construction method.
         */
        @PostConstruct
        public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }


        public String generateJWT(Person person) {
        return JWT.create()
                .withClaim(FIRSTNAME_KEY, person.getFirstName())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

        public String generateVerificationJWT(Person person) {
        return JWT.create()
                .withClaim(EMAIL_KEY, person.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

        /**
         * Gets the username out of a given JWT.
         * @param token The JWT to decode.
         * @return The username stored inside.
         */
        public String getFirstname(String token)
        {
            return JWT.decode(token).getClaim(FIRSTNAME_KEY).asString();
        }

    }
