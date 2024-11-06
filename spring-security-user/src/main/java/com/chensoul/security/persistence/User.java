package com.chensoul.security.persistence;

import com.chensoul.security.web.validation.PasswordConstraint;
import com.chensoul.security.web.validation.PasswordMatches;
import java.util.Calendar;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

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
        this.secret = UUID.randomUUID().toString();
    }

}
