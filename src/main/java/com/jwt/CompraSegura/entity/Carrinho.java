package com.jwt.CompraSegura.entity;

import com.jwt.CompraSegura.roles.StatusCarrinho;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "carrinhos")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCarrinho status;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Carrinho(LocalDateTime dataCriacao, StatusCarrinho status) {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusCarrinho.ATIVO;
    }
    public void adicionarItem(ItemCarrinho item) {
        boolean itemExistente = false;
        for (ItemCarrinho existingItem : this.itens) {
            if (existingItem.getProduto().getId().equals(item.getProduto().getId())) {
                existingItem.setQuantidade(existingItem.getQuantidade() + item.getQuantidade());
                itemExistente = true;
                break;
            }
        }
        if (!itemExistente) {
            this.itens.add(item);
            item.setCarrinho(this);
        }
    }

    public void removerItem(UUID produtoId) {
        this.itens.removeIf(item -> item.getProduto().getId().equals(produtoId));
    }

    public BigDecimal calcularTotal(){
        return itens.stream()
                .map(ItemCarrinho::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusCarrinho getStatus() {
        return status;
    }

    public void setStatus(StatusCarrinho status) {
        this.status = status;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrinho carrinho = (Carrinho) o;
        return Objects.equals(id, carrinho.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}





