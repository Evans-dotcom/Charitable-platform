package com.example.farajaplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
    @NotEmpty
    private String fileName;
    private boolean status;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<VerificationToken> verificationTokens = new ArrayList<>();
    /** Has the users email been verified? */
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private WidowProfile widowProfile;

    public Person() {
    }

    public Person(WidowProfile widowProfile) {
        this.widowProfile = widowProfile;
    }

    public Person(Integer id, String firstName,
                  String lastName, String email, String password, String fileName, boolean status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.fileName=fileName;
        this.status = status;
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

    public String getFileName() {return fileName;}

    public void setFileName(String fileName) {this.fileName = fileName;}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getId() {
        return null;
    }

    public Person(List<VerificationToken> verificationTokens, Boolean emailVerified) {
        this.verificationTokens = verificationTokens;
        this.emailVerified = emailVerified;
    }

    public List<VerificationToken> getVerificationTokens() {
        return verificationTokens;
    }

    public void setVerificationTokens(List<VerificationToken> verificationTokens) {
        this.verificationTokens = verificationTokens;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public WidowProfile getWidowProfile() {
        return widowProfile;
    }

    public void setWidowProfile(WidowProfile widowProfile) {
        this.widowProfile = widowProfile;
    }

    public Boolean isEmailVerified() {
        return emailVerified;
    }
}
