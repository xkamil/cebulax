package com.canx.cebulax.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTo;

    @ManyToOne
    @JsonIgnore
    private Resource resource;

    @ManyToOne
    @JsonIgnore
    private User user;

    public Reservation(LocalDateTime dateTo, Resource resource, User user) {
        this.dateTo = dateTo;
        this.resource = resource;
        this.user = user;
    }

    public Long getUserId(){
        return user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
