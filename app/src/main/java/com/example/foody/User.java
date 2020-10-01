package com.example.foody;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String image;

    public User() {}

    public User(String name, String email, String phoneNumber, String password, String image) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.image = image;
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

    public String getImage() { return image; }
}
