package com.bits.oems.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ForgotPasswordRequest {
    
    @NotBlank
    private String securityAnswer;
    
    @NotBlank
    @Size(min = 8, max = 12)
    private String newPassword;

    public String getSecurityAnswer() {
        return this.securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ForgotPasswordRequest securityAnswer(String securityAnswer) {
        setSecurityAnswer(securityAnswer);
        return this;
    }

    public ForgotPasswordRequest newPassword(String newPassword) {
        setNewPassword(newPassword);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            ", securityAnswer='" + getSecurityAnswer() + "'" +
            ", newPassword='" + getNewPassword() + "'" +
            "}";
    }

}
