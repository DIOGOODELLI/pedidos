package com.pedidos.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pedidos.model.Pedido;

public interface PedidoDaoPage extends PagingAndSortingRepository<Pedido, Long> {
	@Query("SELECT COUNT(c) FROM PedidoItem c WHERE c.produtoServico.id = :idProduto AND c.pedido.situacao = 'ABERTO' ")
	public Long retornarExistePedidoAtivoProduto (@Param("idProduto") UUID idProduto);
}
