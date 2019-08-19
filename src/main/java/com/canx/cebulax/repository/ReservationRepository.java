package com.canx.cebulax.repository;

import com.canx.cebulax.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
