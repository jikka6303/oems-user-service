package com.bits.oems.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank
    @Email
    private String username;
    @NotBlank
    @Size(min = 8, max = 12)
    private String oldPassword;
    @NotBlank
    @Size(min = 8, max = 12)
    private String newPassword;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ResetPasswordRequest username(String username) {
        setUsername(username);
        return this;
    }

    public ResetPasswordRequest oldPassword(String oldPassword) {
        setOldPassword(oldPassword);
        return this;
    }

    public ResetPasswordRequest newPassword(String newPassword) {
        setNewPassword(newPassword);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " username='" + getUsername() + "'" +
            ", oldPassword='" + getOldPassword() + "'" +
            ", newPassword='" + getNewPassword() + "'" +
            "}";
    }


}
