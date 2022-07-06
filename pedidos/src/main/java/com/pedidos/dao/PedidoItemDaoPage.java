package com.pedidos.dao;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.pedidos.model.PedidoItem;
import com.pedidos.models.enums.TipoProdutoEnum;

public interface PedidoItemDaoPage extends PagingAndSortingRepository<PedidoItem, Long> {
	@Query("SELECT p FROM PedidoItem p WHERE p.pedido.id = :idPedido")
	public Page<PedidoItem> retornarItensPedido(@Param("idPedido") UUID uuid, Pageable page);

	@Query("SELECT SUM(p.valor_total) FROM PedidoItem p WHERE p.pedido.id = :idPedido AND p.produtoServico.tipo = :tipoProduto ")
	public Double retornarValorTotalItem(@Param("idPedido") UUID uuid, @Param("tipoProduto") TipoProdutoEnum tipoProduto);
}
