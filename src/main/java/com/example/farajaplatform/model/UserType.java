package com.example.farajaplatform.model;

public enum UserType {
    ADMIN("ADMIN"),  PERSON("PERSON");

    private final String type;

    UserType(String string) {
        type = string;
    }

    @Override
    public String toString() {
        return type;
    }
}
