package com.canx.cebulax.cebulax.model;

import com.canx.cebulax.cebulax.dto.FamilyDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Family {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public static Family fromDTO(FamilyDTO familyDTO) {
        return new Family(familyDTO.getName());
    }

    public Family() {
    }

    public Family(String name) {
        this.name = name;
    }

    public Family(Long id, String name) {
        this.id = id;
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
}
