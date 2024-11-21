package com.bits.oems.user.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document("users")
public class User {

    @Id
    private String id;

    private String username;

    private String password;

    private String email;

    private String securityQn;

    private String securityAnswer;

    private Set<String> roles;

    private Set<String> registeredEvents;

    private Set<String> paymentIds;

}
