package com.canx.cebulax.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer slots;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;

    public Resource(String name, Integer slots, Group group) {
        this.name = name;
        this.slots = slots;
        this.group = group;
    }
}
