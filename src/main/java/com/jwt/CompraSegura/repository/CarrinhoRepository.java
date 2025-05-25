package com.jwt.CompraSegura.repository;

import com.jwt.CompraSegura.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {
    @Override
    Optional<Carrinho> findById(UUID uuid);
}
