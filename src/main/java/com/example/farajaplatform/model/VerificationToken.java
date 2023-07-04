package com.example.farajaplatform.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * Token that has been sent to the users email for verification.
 */
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    /**
     * The unique id for the record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     * The token that was sent to the user.
     */
    @Lob
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    /**
     * The timestamp of when the token was created.
     */
    @Column(name = "created_timestamp", nullable = false)
    private Timestamp createdTimestamp;
    /**
     * The user this verification token is for.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the id.
     *
     * @return The id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id The id.
     */
    public void setId(Long id) {
        this.id = id;
    }
}


