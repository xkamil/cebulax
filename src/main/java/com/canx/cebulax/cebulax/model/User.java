package com.canx.cebulax.cebulax.model;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @JsonIgnore
    private String password;
    private Boolean familyOwner;

    @OneToOne
    private Family family;

    public static User fromDTO(UserCreateDTO userCreateDTO) {
        return new User(userCreateDTO.getName(), userCreateDTO.getPassword());
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Boolean getFamilyOwner() {
        return familyOwner;
    }

    public void setFamilyOwner(Boolean familyOwner) {
        this.familyOwner = familyOwner;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", familyOwner=" + familyOwner +
                ", family=" + family +
                '}';
    }
}
