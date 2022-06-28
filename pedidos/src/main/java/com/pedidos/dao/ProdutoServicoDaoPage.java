package com.pedidos.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pedidos.model.ProdutoServico;

public interface ProdutoServicoDaoPage extends PagingAndSortingRepository<ProdutoServico, Long> {}
