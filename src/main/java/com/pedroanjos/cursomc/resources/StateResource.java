package com.pedroanjos.cursomc.resources;

import com.pedroanjos.cursomc.dto.CityDTO;
import com.pedroanjos.cursomc.dto.StateDTO;
import com.pedroanjos.cursomc.entities.City;
import com.pedroanjos.cursomc.entities.State;
import com.pedroanjos.cursomc.services.CityService;
import com.pedroanjos.cursomc.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "states")
public class StateResource {

    @Autowired
    private StateService service;

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<StateDTO>> findAll(){
        List<StateDTO> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{stateId}/cities")
    public ResponseEntity<List<CityDTO>> findCities(@PathVariable Long stateId){
        List<CityDTO> obj = cityService.findAll(stateId);
        return ResponseEntity.ok().body(obj);
    }

}
