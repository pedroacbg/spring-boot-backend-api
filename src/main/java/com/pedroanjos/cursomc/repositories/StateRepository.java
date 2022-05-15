package com.pedroanjos.cursomc.repositories;

import com.pedroanjos.cursomc.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroanjos.cursomc.entities.State;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

    @Transactional(readOnly = true)
    public List<State> findAllByOrderByName();

}
