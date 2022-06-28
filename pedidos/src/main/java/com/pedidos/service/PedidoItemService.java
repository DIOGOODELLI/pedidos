package com.pedidos.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedidos.dto.PedidoItemDTO;

@Service
public interface PedidoItemService {
	public void salvar (PedidoItemDTO PedidoItemDTO) throws Exception;	
	public void cancelarPedidoItem (PedidoItemDTO PedidoItemDTO) throws Exception;	
	public Page<PedidoItemDTO> retornarListaPedidoItem(PedidoItemDTO filtros, Pageable page) throws Exception;	
	public Double retornarValorTotalItem(UUID uuid, String tipoProduto);
}


