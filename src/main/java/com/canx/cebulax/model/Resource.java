package com.canx.cebulax.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer slots;

    @OneToOne(fetch = FetchType.EAGER)
    private Group group;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "resource")
    private List<Reservation> reservations;

    public Resource(String name, Integer slots, Group group) {
        this.name = name;
        this.slots = slots;
        this.group = group;
    }
}
