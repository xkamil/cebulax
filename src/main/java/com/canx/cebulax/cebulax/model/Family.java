package com.canx.cebulax.cebulax.model;

import com.canx.cebulax.cebulax.dto.UserCreateDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Family {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "family")
    private List<User> users;

    public static Family fromDTO(UserCreateDTO userCreateDTO) {
        return new Family(userCreateDTO.getFamilyName());
    }

    public Family() {
    }

    public Family(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
