package com.pedidos.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedidos.dto.PedidoDTO;

@Service
public interface PedidoService {	
	public PedidoDTO salvar (PedidoDTO pedidoDTO) throws Exception;		
	public Page<PedidoDTO> retornarListaPedido(PedidoDTO filtros, Pageable page) throws Exception;
	public Long retornarExistePedidoAtivoProduto(UUID idProduto);
	public PedidoDTO processarValoresCapaPedido(PedidoDTO pedidoDTO); 
	public void processsarDesconto (PedidoDTO pedidoDTO);
}
