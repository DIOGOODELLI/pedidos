package com.pedidos.service.impl;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
import com.pedidos.models.enums.SitucaoPedidoEnum;
import com.pedidos.models.enums.TipoProdutoEnum;
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
		return ! Objects.equals(pedidoDTO.getSituacao(), SitucaoPedidoEnum.FECHADO);
	}

	@Override
	public PedidoDTO processarValoresCapaPedido(PedidoDTO pedidoDTO) {
		if (this.retornarPedidoAndamento(pedidoDTO)) {	
			if  (Objects.isNull(pedidoDTO.getData_pedido())) {
				pedidoDTO.setData_pedido(new Date());
			}	
			pedidoDTO.setValor_total(this.retornarValorTotem(pedidoDTO));			
		}
		return this.converter.convertPedidoToPedidoDTO(this.pedidoDaoPage.save(this.converter.convertPedidoDTOtoPedido(pedidoDTO)));
	}
	
	@Override
	public void processsarDesconto (PedidoDTO pedidoDTO) {
		if (! Objects.equals(pedidoDTO.getSituacao(), SitucaoPedidoEnum.ABERTO)) {
			throw new ExceptionsService("N??o ?? poss??vel inserir desconto se o pedido n??o estiver em Aberto");
		}
		this.processarValoresCapaPedido(pedidoDTO);
	}
	
	private Double retornarValorTotem(PedidoDTO pedidoDTO) {
		return this.retornarValorFinalProdutos(pedidoDTO,  this.retornarValorDesconto(pedidoDTO)) + this.retornarValorTotal(pedidoDTO.getId(), TipoProdutoEnum.SERVICO);
	}

	private Double retornarValorFinalProdutos(PedidoDTO pedidoDTO, Double desconto) {
		return retornarValorTotal(pedidoDTO.getId(), TipoProdutoEnum.PRODUTO) - desconto;
	}

	private double retornarValorDesconto(PedidoDTO pedidoDTO) {
		return this.retornarValorTotal(pedidoDTO.getId(), TipoProdutoEnum.PRODUTO)  * pedidoDTO.getPercentualDesconto() / 100;
	}
	
	private boolean retornarPedidoAndamento(PedidoDTO pedidoDTO) {
		return Objects.nonNull(pedidoDTO.getSituacao()) && Objects.equals(pedidoDTO.getSituacao(), SitucaoPedidoEnum.ABERTO);
	}
	
	private Double retornarValorTotal(UUID uuid, TipoProdutoEnum tipoProduto) {
		return this.pedidoItemServiceImpl.retornarValorTotalItem( uuid, tipoProduto);
	}
	
	private String retornarPedidoValido(PedidoDTO pedidoDTO) {
		if (Objects.isNull(pedidoDTO)) {
			return "Pedido inv??lido (null)";
		}
		if (Objects.isNull(pedidoDTO.getPercentualDesconto())) {
			return "Porcentagem de desconto inv??lida (null)";
		}
		return StringUtils.EMPTY;
	}

	private void processarSituacaoAtualPedido(PedidoDTO pedidoDTO) {
		if (Objects.isNull(pedidoDTO.getSituacao())) {
			pedidoDTO.setSituacao(SitucaoPedidoEnum.ABERTO);
		} else if (Objects.equals(pedidoDTO.getSituacao(), SitucaoPedidoEnum.ABERTO)) {
			pedidoDTO.setSituacao(SitucaoPedidoEnum.FECHADO);
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
