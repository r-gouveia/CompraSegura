package com.jwt.CompraSegura.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CarrinhoDeCompras {

    Set<Produto> itens;

    public CarrinhoDeCompras(){
        this.itens = new HashSet<>();
    }

    public void adicionarItem(Produto produto){
        itens.add(produto);
    }

    public void removerProduto(UUID id){
        itens.removeIf(item -> item.getId().equals(id));
    }

    public Double calcularTotal(){
        return itens.stream().mapToDouble(Produto::getValor).sum();
    }

    public void exibirCarrinho(){
        if(itens.isEmpty()){
            System.out.println("Carrinho está vazio");
        }else{
            System.out.println("Itens carrinho:");
            for(Produto item : itens){
                System.out.println("----------------------------------------------------");
                System.out.println(item.getNome());
                System.out.println(item.getValor());
                System.out.println(item.getId());
                System.out.println("----------------------------------------------------");
            }
            System.out.println("valor da sua compra:" + calcularTotal());
        }
    }

    public Set<Produto> getItens() {
        return itens;
    }

    public void setItens(Set<Produto> itens) {
        this.itens = itens;
    }
}
