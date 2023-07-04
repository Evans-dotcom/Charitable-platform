package com.example.farajaplatform.service;

import com.example.farajaplatform.model.Admin;
import com.example.farajaplatform.model.Person;
import com.example.farajaplatform.model.UserType;
import com.example.farajaplatform.repository.AdminRepository;
import com.example.farajaplatform.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PersonRepository personRepository;

    private UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(userType);
        if(userType==UserType.ADMIN) {

            Admin admin = adminRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Admin Username "+ username+ "not found"));

            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(UserType.ADMIN.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(adminAuthority);
            return new User(admin.getUsername(), admin.getPassword(), authorities);
        } else if(userType == UserType.PERSON) {
           Person person = personRepository.findByEmailIgnoreCase(username).orElseThrow(()-> new UsernameNotFoundException("Person Email "+ username+ "not found"));

            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(UserType.PERSON.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(adminAuthority);
            return new User(person.getEmail(), person.getPassword(), authorities);
        }
        return null;
    }
}
