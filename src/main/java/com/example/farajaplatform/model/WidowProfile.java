package com.example.farajaplatform.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;

import java.sql.Date;

@Entity
@Table(name = "profile")
public class WidowProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String BriefDescription;
    private Integer nationalID;
    private String county;
    private String subcounty;
    private Integer Amount;
    private Date date;
    private String email;
    private Integer phoneNo;
    private String FbAccount;
    private String TwitterAccount;
    private String fileName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;


    public WidowProfile(Person person) {
        this.person = person;
    }

    public WidowProfile() {
    }

    public WidowProfile(String title, String briefDescription, Integer nationalID, String county, String subcounty, Integer amount, Date date, String email,
                        Integer phoneNo, String fbAccount, String twitterAccount, String fileName) {
        this.title = title;
        BriefDescription = briefDescription;
        this.nationalID = nationalID;
        this.county = county;
        this.subcounty = subcounty;
        Amount = amount;
        this.date = date;
        this.email = email;
        this.phoneNo = phoneNo;
        FbAccount = fbAccount;
        TwitterAccount = twitterAccount;
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefDescription() {
        return BriefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        BriefDescription = briefDescription;
    }

    public Integer getNationalID() {
        return nationalID;
    }

    public void setNationalID(Integer nationalID) {
        this.nationalID = nationalID;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public void setSubcounty(String subcounty) {
        this.subcounty = subcounty;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Integer phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFbAccount() {
        return FbAccount;
    }

    public void setFbAccount(String fbAccount) {
        FbAccount = fbAccount;
    }

    public String getTwitterAccount() {
        return TwitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        TwitterAccount = twitterAccount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
