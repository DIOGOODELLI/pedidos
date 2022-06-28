package com.pedidos.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.model.ProdutoServico;

@Repository
public interface ProdutoServicoDao {
	Page<ProdutoServico> retornarListaProdutoServico(ProdutoServicoDTO produtoServico, Pageable page);
}
