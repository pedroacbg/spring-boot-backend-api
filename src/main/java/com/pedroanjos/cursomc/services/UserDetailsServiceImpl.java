package com.pedroanjos.cursomc.services;

import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if(client == null){
            throw new UsernameNotFoundException(email);
        }
        return new UserSS(client.getId(), client.getEmail(), client.getPassword(), client.getProfiles());
    }
}
