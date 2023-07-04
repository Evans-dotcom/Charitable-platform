package com.example.farajaplatform.dto;

class PersonDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public PersonDetails(Long id, String firstName,
                         String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

public class PersonLoginResponseDto {
    private boolean success;
    private String message;
    private String token;
    private PersonDetails person;
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public PersonDetails getPerson() {
        return person;
    }
    public void setPerson(Long id,String firstName,String lastName, String email, String password) {
        this.person = new PersonDetails(id,firstName,lastName, email, password);
    }
}
