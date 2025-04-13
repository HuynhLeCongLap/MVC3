package com.abc.entities;

import javax.persistence.*;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Table(name = "provinces")
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nameProvince")
    private String name;

    @OneToMany(mappedBy = "province")
    private List<User> users;

    // Getters and Setters
}
