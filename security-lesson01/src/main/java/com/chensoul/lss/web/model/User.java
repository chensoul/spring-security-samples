package com.chensoul.lss.web.model;

import com.chensoul.lss.web.validation.PasswordConstraint;
import com.chensoul.lss.web.validation.PasswordMatches;
import lombok.Data;
import org.jboss.aerogear.security.otp.api.Base32;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Calendar;

@Data
@Entity
@PasswordMatches
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is required.")
    private String username;

    @PasswordConstraint
    @NotEmpty(message = "Password is required.")
    private String password;

    @Transient
    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;

    private Calendar created = Calendar.getInstance();

    @Transient
    private String secret;

    public User() {
        super();
        this.secret = Base32.random();
    }

}
