package com.pedroanjos.cursomc.resources;

import com.pedroanjos.cursomc.dto.EmailDTO;
import com.pedroanjos.cursomc.security.JWTUtil;
import com.pedroanjos.cursomc.security.UserSS;
import com.pedroanjos.cursomc.services.AuthService;
import com.pedroanjos.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value="/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-header", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO dto) {
        service.sendNewPassword(dto.getEmail());
        return ResponseEntity.noContent().build();
    }

}
