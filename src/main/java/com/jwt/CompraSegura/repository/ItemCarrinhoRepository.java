package com.jwt.CompraSegura.repository;

import com.jwt.CompraSegura.entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, UUID> {

    @Override
    Optional<ItemCarrinho> findById(UUID uuid);
}
