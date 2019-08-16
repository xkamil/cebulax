package com.canx.cebulax.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    private String password;

    @ManyToMany(mappedBy = "users")
    @Transient
    @JsonIgnore
    private Set<Group> groups;

    @Transient
    @JsonIgnore
    private List<String> roles = new ArrayList<String>() {{
        add("USER");
    }};

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
