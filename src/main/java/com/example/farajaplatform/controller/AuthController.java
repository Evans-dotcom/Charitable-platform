package com.example.farajaplatform.controller;

import com.example.farajaplatform.dto.*;
import com.example.farajaplatform.exception.EmailFailureException;
import com.example.farajaplatform.exception.UserAlreadyExistsException;
import com.example.farajaplatform.model.Admin;
import com.example.farajaplatform.model.Person;
import com.example.farajaplatform.model.UserType;
import com.example.farajaplatform.repository.AdminRepository;
import com.example.farajaplatform.repository.PersonRepository;
import com.example.farajaplatform.security.JWTGenerator;
import com.example.farajaplatform.service.CustomUserDetailsService;
//import com.example.farajaplatform.service.FileUploaderService;
//import com.example.farajaplatform.service.MapperService;
import com.example.farajaplatform.service.FileUploaderService;
import com.example.farajaplatform.service.MapperService;
import com.example.farajaplatform.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class AuthController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    Person person;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    MapperService mapperService;
    @Autowired
    FileUploaderService fileUploaderService;
    @Autowired
    PersonService personService;

    @PostMapping("api/v1/adminRegister")
    public ResponseEntity<String> adminRegister(@RequestBody AdminDto adminDto) {
        if(adminRepository.existsByUsername(adminDto.getUsername())) {
            return new ResponseEntity<String>("Username is taken !! ", HttpStatus.BAD_REQUEST);
        }
        Admin admin = new Admin();
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));

        adminRepository.save(admin);
        return new ResponseEntity<String>("User Registered successfully !! ", HttpStatus.CREATED);
    }

    @PostMapping("api/v1/adminLogin")
    public ResponseEntity<AdminLoginResponseDto> login(@RequestBody AdminDto adminDto) {
        System.out.println("adminLogin");
        customUserDetailsService.setUserType(UserType.ADMIN);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminDto.getUsername(), adminDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication,UserType.ADMIN.toString());
        AdminLoginResponseDto responseDto = new AdminLoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("login successful !!");
        responseDto.setToken(token);
        Admin admin = adminRepository.findByUsername(adminDto.getUsername()).orElseThrow();
        responseDto.setAdmin(admin.getUsername(), admin.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/api/v1/personRegister")
    public ResponseEntity<SuccessandMessageDto> registerPerson(@Valid @RequestPart("data") String data, @RequestPart("file") MultipartFile file) {
        try {
            SuccessandMessageDto response= new SuccessandMessageDto();
            Person person = mapperService.mapData(data);
            person.setFileName(fileUploaderService.uploadFile(file));
            personService.registerPerson(person);
            response.setMessage("Member Registered Successfully !!");
            response.setSuccess(true);
            return new ResponseEntity<SuccessandMessageDto>(response,HttpStatus.OK);
        } catch (UserAlreadyExistsException | EmailFailureException ex) {
            SuccessandMessageDto response = new SuccessandMessageDto();
            response.setMessage("Email Already Taken");
            response.setSuccess(true);
            return new ResponseEntity<SuccessandMessageDto>(response,HttpStatus.OK);
        }
    }
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        if (personService.verifyPerson(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("api/v1/personLogin")
    public ResponseEntity<PersonLoginResponseDto> PersonLogin(@RequestBody PersonLoginDto personLoginDto) {
        System.out.println("personLogin");
        customUserDetailsService.setUserType(UserType.PERSON);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(personLoginDto.getEmail(), personLoginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication, UserType.PERSON.toString());
        PersonLoginResponseDto responseDto = new PersonLoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("login successful !!");
        responseDto.setToken(token);
       Person person = personRepository.findByEmailIgnoreCase(personLoginDto.getEmail()).orElseThrow();
        responseDto.setPerson(person.getId(),person.getFirstName(),person.getLastName(), person.getEmail(), person.getPassword());
        return new ResponseEntity<PersonLoginResponseDto>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/api/v1/updatePerson/{id}")
    public ResponseEntity<SuccessandMessageDto>updatePerson(@PathVariable Integer id,@RequestBody PersonUpdate personUpdate){
        System.out.println("personUpdate");
        SuccessandMessageDto response=new SuccessandMessageDto();
        if (!(personRepository.existsById(id))){
            response.setMessage("Person does Not exist");
            response.setSuccess(false);
            return new ResponseEntity<SuccessandMessageDto>(response,
                    HttpStatus.BAD_REQUEST);
        }
        Optional<Person> Person = personRepository.findById(id);
        Person person = Person.get();
        person.setFirstName(personUpdate.getFirstName());
        person.setLastName(personUpdate.getLastName());
        person.setEmail(personUpdate.getEmail());
        person.setPassword(passwordEncoder.encode(person.getPassword()));

        person.setStatus(true);
        personRepository.save(person);
        response.setMessage("Person updated successfully");
        response.setSuccess(true);
        return new ResponseEntity<SuccessandMessageDto>(response,HttpStatus.OK);
    }

}
