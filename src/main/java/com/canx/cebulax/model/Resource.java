package com.canx.cebulax.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer slots;

    @ManyToOne
    @JsonIgnore
    private Group group;

    @OneToMany(mappedBy = "resource")
    @JsonIgnore
    private Set<Reservation> reservations = Sets.newHashSet();

    public Resource(String name, Integer slots, Group group) {
        this.name = name;
        this.slots = slots;
        this.group = group;
    }

    public Set<Reservation> getActiveReservations() {
        LocalDateTime currentDate = LocalDateTime.now();
        return reservations.stream()
                .filter(r -> r.getDateTo().compareTo(currentDate) > 0)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
