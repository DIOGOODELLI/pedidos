package com.pedidos.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.dto.PedidoDTO;
import com.pedidos.service.PedidoService;

@RestController
public class PedidoController {
	
	private static final String URL_RETORNO_PEDIDO = "api/pedido/retornarPedido";
	private static final String URL_INSERIR_PEDIDO = "api/pedido/gravarPedido";
	private static final String URL_PROCESSAR_DESCONTO_PEDIDO = "api/pedido/processarDesconto";
	
	@Autowired
	private PedidoService service; 
	
	@PostMapping(URL_INSERIR_PEDIDO)
	public PedidoDTO gravarPedido(@RequestBody PedidoDTO dto) throws Exception {
		return this.service.salvar(dto);
	}
	
	@PostMapping(URL_RETORNO_PEDIDO)
	public Page<PedidoDTO> retornarPedido(@RequestBody PedidoDTO filtros, Pageable page) throws Exception {	
		return this.service.retornarListaPedido(filtros, page);
	}
	
	@PutMapping(URL_PROCESSAR_DESCONTO_PEDIDO)
	public void processsarDesconto(@RequestBody PedidoDTO pedidoDTO) throws Exception {	
		this.service.processsarDesconto(pedidoDTO);
	}	
}
