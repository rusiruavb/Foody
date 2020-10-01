package com.example.foody;

public class Admin {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String imageUrl;

    public Admin() {}

    public Admin(String name, String email, String phoneNumber, String password, String imageUrl) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
