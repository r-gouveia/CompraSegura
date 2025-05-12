package com.jwt.CompraSegura.dto;

import com.jwt.CompraSegura.entity.User;

public class UserDTO {
    private String nome;
    private String email;
    private String password;
    private String regiao;

    public UserDTO(User user) {
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.regiao = user.getRegiao();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
