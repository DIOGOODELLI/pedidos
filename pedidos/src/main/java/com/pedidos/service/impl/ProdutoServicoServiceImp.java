package com.pedidos.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedidos.api.converter.ProdutoServicoConverter;
import com.pedidos.dao.ProdutoServicoDaoImpl;
import com.pedidos.dao.ProdutoServicoDaoPage;
import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.exceptions.ExceptionsService;
import com.pedidos.model.ProdutoServico;
import com.pedidos.service.ProdutoServicoService;

@Service
public class ProdutoServicoServiceImp implements ProdutoServicoService {
	
	@Autowired
	ProdutoServicoDaoPage produtoServicoDaoPage;
	
	@Autowired
	ProdutoServicoDaoImpl produtoServicoDaoImpl;
	
	@Autowired
	PedidoServiceImpl pedidoServiceImpl;
	
	@Autowired
	private ProdutoServicoConverter converter;
	
	@Override
	public void salvar(ProdutoServicoDTO produtoServicoDTO) throws Exception {
		try {
			String descricaoErro = this.retornarItemValido(produtoServicoDTO);
			if (! descricaoErro.isEmpty()) {
				throw new ExceptionsService(descricaoErro);
			}
			ProdutoServico produtoServico = this.converter.convertProdutoServicoDTOtoProdutoServico(produtoServicoDTO);
			this.produtoServicoDaoPage.save(produtoServico);
		} catch (Exception e) {
			throw new ExceptionsService(e.toString());
		}
	}

	private String retornarItemValido(ProdutoServicoDTO produtoServicoDTO) {
		if (Objects.isNull(produtoServicoDTO)) {
			return "Item inválido (null)";
		}
		if (Objects.isNull(produtoServicoDTO.getDescricao()) ||  Objects.equals(StringUtils.EMPTY, produtoServicoDTO.getDescricao())) {
			return "Descrição do item é obrigatória";
		}
		if (Objects.isNull(produtoServicoDTO.getEnabled())) {
			return "É necessário indicar item está ativo";
		}
		if (Objects.isNull(produtoServicoDTO.getTipo())) {
			return "Tipo do item é obrigatório";
		}
		if (! produtoServicoDTO.getEnabled() && pedidoServiceImpl.retornarExistePedidoAtivoProduto(produtoServicoDTO.getId()) > 0) {
			return "Não é possível inativar um item com pedido em aberto";
		}
		return StringUtils.EMPTY;
	}
	
	@Override
	public Page<ProdutoServicoDTO> retornarProdutoServico(ProdutoServicoDTO filtros, Pageable page) throws Exception {		
		Page<ProdutoServico> listaProdutoServico = this.produtoServicoDaoImpl.retornarListaProdutoServico(filtros, page);
		if (Objects.nonNull(listaProdutoServico)) {
			return this.converter.convertListPageToListPageDTO(listaProdutoServico);
		}
		return null;
	}
}
