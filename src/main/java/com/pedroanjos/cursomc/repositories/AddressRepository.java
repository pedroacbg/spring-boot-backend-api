package com.pedroanjos.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroanjos.cursomc.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
