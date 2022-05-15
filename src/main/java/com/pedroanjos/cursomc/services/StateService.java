package com.pedroanjos.cursomc.services;

import com.pedroanjos.cursomc.dto.CategoryDTO;
import com.pedroanjos.cursomc.dto.StateDTO;
import com.pedroanjos.cursomc.entities.Category;
import com.pedroanjos.cursomc.entities.State;
import com.pedroanjos.cursomc.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateService {

    @Autowired
    private StateRepository repository;

    public List<StateDTO> findAll(){
        List<State> list = repository.findAll();
        return list.stream().map(x -> new StateDTO(x)).collect(Collectors.toList());
    }

}
