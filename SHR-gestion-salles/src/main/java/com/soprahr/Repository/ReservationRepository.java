package com.soprahr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprahr.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}
