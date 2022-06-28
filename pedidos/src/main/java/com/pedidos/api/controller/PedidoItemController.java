package com.pedidos.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.dto.PedidoItemDTO;
import com.pedidos.service.PedidoItemService;

@RestController
public class PedidoItemController {
	
	private static final String URL_RETORNO_PEDIDO_ITEM = "api/pedidoItem/retornarPedidoItem";
	private static final String URL_INSERIR_PEDIDO_ITEM  = "api/pedidoItem/gravarPedidoItem";
	
	@Autowired
	private PedidoItemService service; 
	
	@PostMapping(URL_INSERIR_PEDIDO_ITEM )
	public void gravarPedido(@RequestBody PedidoItemDTO dto) throws Exception {
		this.service.salvar(dto);
	}
	
	@PostMapping(URL_RETORNO_PEDIDO_ITEM )
	public Page<PedidoItemDTO> retornarPedidoItem(@RequestBody PedidoItemDTO filtros, Pageable page) throws Exception {	
		return this.service.retornarListaPedidoItem(filtros, page);
	}
}
