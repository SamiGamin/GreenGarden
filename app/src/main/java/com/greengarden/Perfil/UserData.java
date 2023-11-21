package com.greengarden.Perfil;

public class UserData {
    private String name;
    private String email;

    private String apellidos;
    private String profileImage;

    public UserData() {
    }

    public UserData(String name, String email, String apellidos, String profileImage) {
        this.name = name;
        this.email = email;
        this.apellidos = apellidos;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
