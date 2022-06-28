package com.pedidos.api.converter;

import java.util.List;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.model.ProdutoServico;
import com.pedidos.utils.ModelMapperConverter;

import java.util.stream.Collectors;

@Component
public class ProdutoServicoConverter {
	
	@Autowired
	private ModelMapperConverter converter;

	public ProdutoServico convertProdutoServicoDTOtoProdutoServico(ProdutoServicoDTO produtoServicoDTO) {
		return new ModelMapper().map(produtoServicoDTO, ProdutoServico.class);
	}
	
	public ProdutoServicoDTO convertProdutoServicoToProdutoServicoDTO(ProdutoServico produtoServico) {
		return new ModelMapper().map(produtoServico, ProdutoServicoDTO.class);
	}
	
	public List<ProdutoServicoDTO> convertListToListDTO(List<ProdutoServico> listaProdutoServico) {
		List<ProdutoServicoDTO> lista = listaProdutoServico.stream().map(new Function<ProdutoServico, ProdutoServicoDTO>() {
			@Override
			public ProdutoServicoDTO apply(ProdutoServico ProdutoServico) {
				ProdutoServicoDTO dto = ProdutoServicoConverter.this.converter.converter(ProdutoServico, ProdutoServicoDTO.class);
				return dto;
			}
		}).collect(Collectors.toList());
		return lista;
	}

	public Page<ProdutoServicoDTO> convertListPageToListPageDTO(Page<ProdutoServico> listaProdutoServico) {
		Page<ProdutoServicoDTO> listaProdutoServicoDTO = listaProdutoServico.map(new Function<ProdutoServico, ProdutoServicoDTO>() {
			@Override
			public ProdutoServicoDTO apply(ProdutoServico produtoServico) {
				return ProdutoServicoConverter.this.converter.converter(produtoServico, ProdutoServicoDTO.class);
			}
		});
		return listaProdutoServicoDTO;
	}
}
