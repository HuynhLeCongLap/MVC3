package com.abc.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;







@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String passWord;
    private String createdAt;

    @Email(message = "Email không hợp lệ")
    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String avatar; // đường dẫn ảnh đã upload

    // --- Constructors ---
    public User() {}

    public User(int id, String username, String passWord, String createdAt) {
        this.id = id;
        this.username = username;
        this.passWord = passWord;
        this.createdAt = createdAt;
    }

    public User(String username, String passWord) {
        this.username = username;
        this.passWord = passWord;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassWord() { return passWord; }
    public void setPassWord(String passWord) { this.passWord = passWord; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
