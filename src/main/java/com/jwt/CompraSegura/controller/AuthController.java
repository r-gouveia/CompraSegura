package com.jwt.CompraSegura.controller;

import com.jwt.CompraSegura.auth.JwtTokenProvider;
import com.jwt.CompraSegura.dto.AuthResponseDTO;
import com.jwt.CompraSegura.dto.LoginDTO;
import com.jwt.CompraSegura.dto.UserDTO;
import com.jwt.CompraSegura.entity.User;
import com.jwt.CompraSegura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody LoginDTO login ){
        Authentication authentication = authenticationManager.authenticate (new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenProvider.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UserDTO> cadastrar (@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSalvo = userRepository.save(user);
        UserDTO userDTO =  new UserDTO(userSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

}
