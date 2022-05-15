package com.pedroanjos.cursomc.services;

import com.pedroanjos.cursomc.dto.CityDTO;
import com.pedroanjos.cursomc.dto.StateDTO;
import com.pedroanjos.cursomc.entities.City;
import com.pedroanjos.cursomc.entities.State;
import com.pedroanjos.cursomc.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    public List<CityDTO> findAll(Long stateId){
        List<City> list = repository.findCities(stateId);
        return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
    }


}
