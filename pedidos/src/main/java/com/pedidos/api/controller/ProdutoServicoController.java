package com.pedidos.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.service.ProdutoServicoService;

@RestController
public class ProdutoServicoController {

	private static final String URL_RETORNO_PRODUTO_SERVICO = "api/produtoServico/retornarProdutoServico";
	private static final String URL_INSERIR_PRODUTO_SERVICO = "api/produtoServico/gravarProdutoServico";
	
	@Autowired
	private ProdutoServicoService service; 
	
	@PutMapping(URL_INSERIR_PRODUTO_SERVICO)
	public void gravarProdutoServico(@RequestBody ProdutoServicoDTO dto) throws Exception {
		this.service.salvar(dto);
	}
		
	@PostMapping(URL_RETORNO_PRODUTO_SERVICO)
	public Page<ProdutoServicoDTO> retornarProdutoServico(@RequestBody ProdutoServicoDTO filtros, Pageable page) throws Exception {	
		return this.service.retornarProdutoServico(filtros, page);
	}
}
