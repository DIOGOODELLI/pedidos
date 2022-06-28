package com.pedidos.service.impl;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pedidos.api.converter.PedidoConverter;
import com.pedidos.dao.PedidoDaoImpl;
import com.pedidos.dao.PedidoDaoPage;
import com.pedidos.dto.PedidoDTO;
import com.pedidos.exceptions.ExceptionsService;
import com.pedidos.model.Pedido;
import com.pedidos.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	PedidoDaoPage pedidoDaoPage;
	
	@Autowired
	PedidoDaoImpl pedidoDaoDaoImpl;
	
	@Autowired
	PedidoItemServiceImpl pedidoItemServiceImpl;
	
	@Autowired
	private PedidoConverter converter;
	
	@Override
	public PedidoDTO salvar(PedidoDTO pedidoDTO) throws Exception {
		String descricaoErro = this.retornarPedidoValido(pedidoDTO);
		if (! descricaoErro.isEmpty()) {
			throw new ExceptionsService(descricaoErro);
		} else {
			if (retornarPedidoAbertoParaProcessarValores(pedidoDTO)) {
				this.processarSituacaoAtualPedido(pedidoDTO);
				return 	this.processarValoresCapaPedido(pedidoDTO);
			}
			return pedidoDTO;
		}
	}

	private boolean retornarPedidoAbertoParaProcessarValores(PedidoDTO pedidoDTO) {
		return ! Objects.equals(pedidoDTO.getSituacao(), "FECHADO");
	}

	@Override
	public PedidoDTO processarValoresCapaPedido(PedidoDTO pedidoDTO) {
		if (this.retornarPedidoAndamento(pedidoDTO)) {	
			if  (Objects.isNull(pedidoDTO.getData_pedido())) {
				pedidoDTO.setData_pedido(new Date());
			}	
			pedidoDTO.setValor_produtos(this.retornarValorTotal(pedidoDTO.getId(), "PRODUTO"));
			pedidoDTO.setValor_servicos(this.retornarValorTotal(pedidoDTO.getId(), "SERVICO"));
			if (Objects.isNull(pedidoDTO.getPercentualDesconto()))
				pedidoDTO.setPercentualDesconto(NumberUtils.INTEGER_ZERO);
			pedidoDTO.setValor_total(this.retornarValorTotem(pedidoDTO));			
		}
		return this.converter.convertPedidoToPedidoDTO(this.pedidoDaoPage.save(this.converter.convertPedidoDTOtoPedido(pedidoDTO)));
	}
	
	@Override
	public void processsarDesconto (PedidoDTO pedidoDTO) {
		if (! Objects.equals(pedidoDTO.getSituacao(), "ABERTO")) {
			throw new ExceptionsService("Não é possível inserir desconto se o pedido não estiver em Aberto");
		}
		this.processarValoresCapaPedido(pedidoDTO);
	}
	
	private Double retornarValorTotem(PedidoDTO pedidoDTO) {
		Double desconto = this.retornarValorDesconto(pedidoDTO) ;
		Double valorFinalProdutos = this.retornarValorFinalProdutos(pedidoDTO, desconto);
		return valorFinalProdutos + pedidoDTO.getValor_servicos();
	}

	private Double retornarValorFinalProdutos(PedidoDTO pedidoDTO, Double desconto) {
		Double valorFinalProdutos = pedidoDTO.getValor_produtos() - desconto;
		return valorFinalProdutos;
	}

	private double retornarValorDesconto(PedidoDTO pedidoDTO) {
		return pedidoDTO.getValor_produtos()  * pedidoDTO.getPercentualDesconto() / 100;
	}
	

	private boolean retornarPedidoAndamento(PedidoDTO pedidoDTO) {
		return Objects.nonNull(pedidoDTO.getSituacao()) && Objects.equals(pedidoDTO.getSituacao(), "ABERTO");
	}
	
	private Double retornarValorTotal(UUID uuid, String tipoProduto) {
		return this.pedidoItemServiceImpl.retornarValorTotalItem( uuid, tipoProduto);
	}
	
	private String retornarPedidoValido(PedidoDTO pedidoDTO) {
		if (Objects.isNull(pedidoDTO)) {
			return "Pedido inválido (null)";
		}
		return StringUtils.EMPTY;
	}

	private void processarSituacaoAtualPedido(PedidoDTO pedidoDTO) {
		if (Objects.isNull(pedidoDTO.getSituacao()) || StringUtils.isEmpty(pedidoDTO.getSituacao())) {
			pedidoDTO.setSituacao("ABERTO");
		} else if (Objects.equals(pedidoDTO.getSituacao(), "ABERTO")) {
			pedidoDTO.setSituacao("FECHADO");
		}
	}
	
	@Override
	public Page<PedidoDTO> retornarListaPedido(PedidoDTO filtros, Pageable page) throws Exception {
		Page<Pedido> listaPedido = this.pedidoDaoDaoImpl.retornarListaPedido(filtros, page);
		if (Objects.nonNull(listaPedido)) {
			return this.converter.convertListPageToListPageDTO(listaPedido);
		}
		return null;
	}

	@Override
	public Long retornarExistePedidoAtivoProduto(UUID idProduto) {
		return pedidoDaoPage.retornarExistePedidoAtivoProduto(idProduto);
	}


}
