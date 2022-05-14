package com.pedroanjos.cursomc.repositories;

import com.pedroanjos.cursomc.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroanjos.cursomc.entities.Order;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    @Transactional(readOnly = true)
    Page<Order> findByClient(Client client, Pageable pageable);

}
