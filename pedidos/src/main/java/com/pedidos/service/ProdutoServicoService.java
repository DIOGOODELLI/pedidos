package com.pedidos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedidos.dto.ProdutoServicoDTO;

@Service
public interface ProdutoServicoService {
	
	public void salvar (ProdutoServicoDTO produtoServicoDTO) throws Exception;
	public Page<ProdutoServicoDTO> retornarProdutoServico(ProdutoServicoDTO filtros, Pageable page) throws Exception;
	
}
