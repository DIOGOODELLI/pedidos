package com.pedidos.api.converter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.pedidos.dto.PedidoDTO;
import com.pedidos.model.Pedido;
import com.pedidos.utils.ModelMapperConverter;

@Component
public class PedidoConverter {
	
	@Autowired
	private ModelMapperConverter converter;

	public Pedido convertPedidoDTOtoPedido(PedidoDTO pedidoDTO) {
		return new ModelMapper().map(pedidoDTO, Pedido.class);
	}
	
	public PedidoDTO convertPedidoToPedidoDTO(Pedido pedido) {
		return new ModelMapper().map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> convertListToListDTO(List<Pedido> listaPedido) {
		List<PedidoDTO> lista = listaPedido.stream().map(new Function<Pedido, PedidoDTO>() {
			@Override
			public PedidoDTO apply(Pedido pedido) {
				PedidoDTO dto = PedidoConverter.this.converter.converter(pedido, PedidoDTO.class);
				return dto;
			}
		}).collect(Collectors.toList());
		return lista;
	}

	public Page<PedidoDTO> convertListPageToListPageDTO(Page<Pedido> listaPedido) {
		Page<PedidoDTO> listaPedidoDTO = listaPedido.map(new Function<Pedido, PedidoDTO>() {
			@Override
			public PedidoDTO apply(Pedido pedido) {
				return PedidoConverter.this.converter.converter(pedido, PedidoDTO.class);
			}
		});
		return listaPedidoDTO;
	}
}
