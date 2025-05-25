package com.jwt.CompraSegura.repository;

import com.jwt.CompraSegura.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Override
    Optional<Produto> findById(UUID uuid);
}
