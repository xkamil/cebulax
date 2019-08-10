package com.canx.cebulax.cebulax.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Group {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String secret;
    private User owner;

    @ManyToMany
    private Set<User> users;

    public Group() {
    }

    public Group(String name, String secret, User owner, Set<User> users) {
        this.name = name;
        this.secret = secret;
        this.owner = owner;
        this.users = users;
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secret='" + secret + '\'' +
                ", owner=" + owner +
                ", users=" + users +
                '}';
    }
}
