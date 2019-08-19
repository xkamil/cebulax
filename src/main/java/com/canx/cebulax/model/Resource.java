package com.canx.cebulax.model;

import com.google.common.collect.Lists;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "resource_reservation",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private List<Reservation> reservations = Lists.newArrayList();

    public Resource(String name, Integer slots, Group group) {
        this.name = name;
        this.slots = slots;
        this.group = group;
    }
}
