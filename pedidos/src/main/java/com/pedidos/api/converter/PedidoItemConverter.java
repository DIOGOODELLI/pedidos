package com.pedidos.api.converter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.pedidos.dto.PedidoItemDTO;
import com.pedidos.model.PedidoItem;
import com.pedidos.utils.ModelMapperConverter;

@Component
public class PedidoItemConverter {
	
	@Autowired
	private ModelMapperConverter converter;

	public PedidoItem convertPedidoItemDTOtoPedidoItem(PedidoItemDTO pedidoItemDTO) {
		return new ModelMapper().map(pedidoItemDTO, PedidoItem.class);
	}
	
	public PedidoItemDTO convertPedidoItemToPedidoItemDTO(PedidoItem pedidoItem) {
		return new ModelMapper().map(pedidoItem, PedidoItemDTO.class);
	}
	
	public List<PedidoItemDTO> convertListToListDTO(List<PedidoItem> listaPedidoItem) {
		List<PedidoItemDTO> lista = listaPedidoItem.stream().map(new Function<PedidoItem, PedidoItemDTO>() {
			@Override
			public PedidoItemDTO apply(PedidoItem pedidoItem) {
				PedidoItemDTO dto = PedidoItemConverter.this.converter.converter(pedidoItem, PedidoItemDTO.class);
				return dto;
			}
		}).collect(Collectors.toList());
		return lista;
	}

	public Page<PedidoItemDTO> convertListPageToListPageDTO(Page<PedidoItem> listaPedidoItem) {
		Page<PedidoItemDTO> listaPedidoItemDTO = listaPedidoItem.map(new Function<PedidoItem, PedidoItemDTO>() {
			@Override
			public PedidoItemDTO apply(PedidoItem pedidoItem) {
				return PedidoItemConverter.this.converter.converter(pedidoItem, PedidoItemDTO.class);
			}
		});
		return listaPedidoItemDTO;
	}
}
