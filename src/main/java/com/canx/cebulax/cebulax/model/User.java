package com.canx.cebulax.cebulax.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @JsonIgnore
    private String password;


    @OneToMany
    private Set<Family> families;

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

    public Set<Family> getFamilies() {
        return families;
    }

    public void setFamilies(Set<Family> families) {
        this.families = families;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", families=" + families +
                '}';
    }
}
