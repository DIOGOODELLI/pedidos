package com.pedidos.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pedidos.dto.PedidoDTO;
import com.pedidos.model.Pedido;

@Repository
public interface PedidoDao {
	Page<Pedido> retornarListaPedido(PedidoDTO pedido, Pageable page);
}
