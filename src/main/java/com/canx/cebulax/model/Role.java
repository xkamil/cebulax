package com.canx.cebulax.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserRole role;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    public Role(UserRole role) {
        this.role = role;
    }
}
