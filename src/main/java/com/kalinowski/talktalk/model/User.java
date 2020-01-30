package com.kalinowski.talktalk.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Email can't be empty")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Username can't be empty")
    private String userName;

    @NotNull(message = "Password can't be empty")
    @Length(min = 3, message = "Password should be at least 3 characters")
    private String password;

    private boolean active;
    private String roles;
    private String profilePictureUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_contact_link",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "connected_user_id"))
    private Set<User> contacts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_contact_link",
            joinColumns = @JoinColumn(name = "connected_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> contactOf;
}
