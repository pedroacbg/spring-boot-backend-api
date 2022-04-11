package com.pedroanjos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroanjos.cursomc.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long>{

}
