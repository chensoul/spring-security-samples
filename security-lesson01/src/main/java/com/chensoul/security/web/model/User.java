package com.chensoul.security.web.model;

import com.chensoul.security.web.validation.PasswordConstraint;
import com.chensoul.security.web.validation.PasswordMatches;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import java.util.Calendar;
import lombok.Data;
import org.jboss.aerogear.security.otp.api.Base32;

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
