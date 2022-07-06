package com.pedidos.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedidos.api.converter.PedidoItemConverter;
import com.pedidos.dao.PedidoItemDaoPage;
import com.pedidos.dto.PedidoItemDTO;
import com.pedidos.exceptions.ExceptionsService;
import com.pedidos.model.PedidoItem;
import com.pedidos.models.enums.TipoProdutoEnum;
import com.pedidos.service.PedidoItemService;

@Service
public class PedidoItemServiceImpl implements PedidoItemService {

	@Autowired
	PedidoItemDaoPage pedidoItemDaoPage;
	
	@Autowired
	PedidoServiceImpl pedidoServiceImpl;
	
	@Autowired
	PedidoItemConverter converter;

	@Override
	public void salvar(PedidoItemDTO pedidoItemDTO) throws Exception {
		if (! pedidoItemDTO.getProdutoServico().getEnabled()) {
			throw new ExceptionsService("Não é possível inserir um item INATIVO");
		}
		this.calcularValorTotemDoItem(pedidoItemDTO);
		PedidoItem pedidoItem = converter.convertPedidoItemDTOtoPedidoItem(pedidoItemDTO);
		this.pedidoItemDaoPage.save(pedidoItem);
		this.pedidoServiceImpl.processarValoresCapaPedido(pedidoItemDTO.getPedido());
	}

	private void calcularValorTotemDoItem(PedidoItemDTO pedidoItemDTO) {
		Double valor = pedidoItemDTO.getQuantidade() * pedidoItemDTO.getValor_unitario();
		pedidoItemDTO.setValor_total(valor);
	}
 
	@Override
	public void cancelarPedidoItem(PedidoItemDTO PedidoItemDTO) throws Exception {
		PedidoItem pedidoItem = converter.convertPedidoItemDTOtoPedidoItem(PedidoItemDTO);
		this.pedidoItemDaoPage.delete(pedidoItem);
	}

	@Override
	public Page<PedidoItemDTO> retornarListaPedidoItem(PedidoItemDTO filtros, Pageable page) throws Exception {
		Page<PedidoItem> pedidoItem = this.pedidoItemDaoPage.retornarItensPedido(filtros.getPedido().getId(), page);
		if (Objects.nonNull(pedidoItem)) {
			return this.converter.convertListPageToListPageDTO(pedidoItem);
		}
		return null;
	}

	@Override
	public Double retornarValorTotalItem(UUID uuid, TipoProdutoEnum tipoProduto) {
		Double valor = this.pedidoItemDaoPage.retornarValorTotalItem(uuid, tipoProduto);
		if (Objects.nonNull(valor)) {
			return valor;
		}
		return NumberUtils.DOUBLE_ZERO;
	}
}
