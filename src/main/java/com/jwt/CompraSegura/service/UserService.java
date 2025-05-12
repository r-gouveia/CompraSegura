package com.jwt.CompraSegura.service;

import com.jwt.CompraSegura.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<com.jwt.CompraSegura.entity.User> userOptional = userRepository.findByEmail(username);

         if(userOptional.isEmpty()){
                throw new UsernameNotFoundException("Email nao encontrado");
         }

        com.jwt.CompraSegura.entity.User user = userOptional.get();
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
