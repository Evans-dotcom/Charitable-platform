package com.example.farajaplatform.dto;

class AdminDetails {
    String username;
    int id;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}

public class AdminLoginResponseDto {
    private boolean success;
    private String message;
    private String token;
    private AdminDetails admin;
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
    public AdminDetails getAdmin() {
        return admin;
    }
    public void setAdmin(String username, Integer id) {
        this.admin = new AdminDetails();
        this.admin.setUsername(username);
        this.admin.setId(id);
    }

}
