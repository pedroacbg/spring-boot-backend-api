package com.pedroanjos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroanjos.cursomc.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
