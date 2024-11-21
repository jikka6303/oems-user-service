package com.bits.oems.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsernameLoginRequest {

    @NotBlank
    @Size(min = 5, max = 12)
    private String username;
    @NotBlank
    @Size(min = 8, max = 12)
    private String password;

    public UsernameLoginRequest() {
    }

    public UsernameLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernameLoginRequest username(String username) {
        setUsername(username);
        return this;
    }

    public UsernameLoginRequest password(String password) {
        setPassword(password);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }



}
