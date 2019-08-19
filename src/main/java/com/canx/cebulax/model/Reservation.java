package com.canx.cebulax.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource resource;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Reservation(LocalDateTime dateTo, Resource resource, User user) {
        this.dateTo = dateTo;
        this.resource = resource;
        this.user = user;
    }
}
