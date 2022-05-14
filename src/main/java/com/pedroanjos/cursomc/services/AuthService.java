package com.pedroanjos.cursomc.services;

import com.pedroanjos.cursomc.entities.Client;
import com.pedroanjos.cursomc.repositories.ClientRepository;
import com.pedroanjos.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email){
        Client client = clientRepository.findByEmail(email);
        if (client == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        client.setPassword(encoder.encode(newPass));

        clientRepository.save(client);
        emailService.sendNewPasswordEmail(client, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if(opt == 0){ //gera um dígito
            return (char) (rand.nextInt(10) + 48);
        } else if(opt == 1){ //gera letra maiúscula
            return (char) (rand.nextInt(26) + 65);
        } else { //gera letra minúscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
