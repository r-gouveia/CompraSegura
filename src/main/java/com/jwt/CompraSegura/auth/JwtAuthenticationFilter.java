package com.jwt.CompraSegura.auth;


import com.jwt.CompraSegura.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //Aqui pega o header Auth
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        //ele precisa estar presente e começar com Bearer
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7); // remove o bearer
            username = jwtTokenProvider.getUsernameFromToken(token); // pega o email do token
        }

        // se conseguiu extrair o user e ele ainda nao esta auth
        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userService.loadUserByUsername(username);

            //Valida se o token é realmente válido
            if(jwtTokenProvider.validateToken(token)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                // 7. Adiciona detalhes da requisição
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //registra o contxt de segurança
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }




}
