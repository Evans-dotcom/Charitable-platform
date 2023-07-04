package com.example.farajaplatform.service;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.farajaplatform.model.Person;
import com.example.farajaplatform.repository.PersonRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

   @Autowired
   JWTService jwtService;
    /** The Local User DAO. */
    @Autowired
    PersonRepository personRepository;

    /**
     * Constructor for spring injection.
     * @param jwtService
     * @param personRepository
     */
    public JWTRequestFilter(JWTService jwtService, PersonRepository personRepository) {
        this.jwtService = jwtService;
        this.personRepository = personRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String email = jwtService.getFirstname(token);
                Optional<Person> opPerson = personRepository.findByEmailIgnoreCase(email);
                if (opPerson.isPresent()) {
                    Person person= (Person) opPerson.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(person, null, new ArrayList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException ex) {
            }
        }
        filterChain.doFilter(request, response);
    }

}
