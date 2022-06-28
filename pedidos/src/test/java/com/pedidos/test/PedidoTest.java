package com.pedidos.test;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.pedidos.dto.PedidoDTO;
import com.pedidos.dto.PedidoItemDTO;
import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.service.ProdutoServicoService;
import com.pedidos.utils.RestResponsePage;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class PedidoTest {
	
	@Autowired
	ProdutoServicoService produtoServicoService;
	
	private static final String URL_RETORNO_PEDIDO = "api/pedido/retornarPedido";
	private static final String URL_INSERIR_PEDIDO = "api/pedido/gravarPedido";
	private static final String URL_RETORNO_PEDIDO_ITEM = "api/pedidoItem/retornarPedidoItem";
	private static final String URL_INSERIR_PEDIDO_ITEM  = "api/pedidoItem/gravarPedidoItem";
	private static final String URL_RETORNO_PRODUTO_SERVICO = "api/produtoServico/retornarProdutoServico";
	
	private static final String IP_PORTA = "http://localhost:8080/";
	
	@Test
	public void GravarPedido() throws Exception{
		//ADD CAPA DO PEDIDO
		PedidoDTO pedidoDTO = new PedidoDTO();
		pedidoDTO.setPercentualDesconto(10);
		pedidoDTO.setNumero_pedido(99l);
		RestTemplate restTemplate = new RestTemplate();		
		HttpEntity<PedidoDTO> request = new HttpEntity<PedidoDTO>(pedidoDTO);
		ResponseEntity<PedidoDTO> result  = restTemplate.exchange(IP_PORTA.concat(URL_INSERIR_PEDIDO), HttpMethod.POST, request, PedidoDTO.class);
	
		//ADD ITENS DO PEDIDO
		PedidoItemDTO pedidoItemDTO = new PedidoItemDTO();
		pedidoItemDTO.setPedido(result.getBody());
		
		if (!this.RetornarProdutoServicos().getDescricao().isEmpty()) {
			pedidoItemDTO.setProdutoServico(this.RetornarProdutoServicos());
			pedidoItemDTO.setQuantidade(1d);
			pedidoItemDTO.setValor_unitario(50d);
			restTemplate = new RestTemplate();		
			HttpEntity<PedidoItemDTO> requestItem = new HttpEntity<PedidoItemDTO>(pedidoItemDTO);
			restTemplate.exchange(IP_PORTA.concat(URL_INSERIR_PEDIDO_ITEM), HttpMethod.POST, requestItem, PedidoItemDTO.class);		
		} else {
			System.out.println("RORDAR A ROTINA DO ITENS DO PRODUTO ANTES");
		}
	}
	
	public ProdutoServicoDTO RetornarProdutoServicos()  {
		ProdutoServicoDTO filtros = new ProdutoServicoDTO();
		RestTemplate restTemplate = new RestTemplate();		
		ParameterizedTypeReference<RestResponsePage<ProdutoServicoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<ProdutoServicoDTO>>() { };
		HttpEntity<ProdutoServicoDTO> entity = new HttpEntity<ProdutoServicoDTO>(filtros);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IP_PORTA.concat(URL_RETORNO_PRODUTO_SERVICO));
		
		//OBTENDO RETORNO DA PÁGINA COM PAGINAÇÃO
		ResponseEntity<RestResponsePage<ProdutoServicoDTO>> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, responseType);
		List<ProdutoServicoDTO> resultados = result.getBody().getContent();

		if (Objects.nonNull(resultados)) {
			return resultados.get(0);					
		}		
		return 	new ProdutoServicoDTO();
	}
	
	@Test
	public void RetornarPedido() throws Exception {
		//PREPARANDO FILTROS
		PedidoDTO filtros = new PedidoDTO();
		//filtros.setSituacao("ABERTO");
		
		//CHAMANDO O SERVIÇO REST
		RestTemplate restTemplate = new RestTemplate();		
		ParameterizedTypeReference<RestResponsePage<PedidoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<PedidoDTO>>() { };
		HttpEntity<PedidoDTO> entity = new HttpEntity<PedidoDTO>(filtros);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IP_PORTA.concat(URL_RETORNO_PEDIDO));
		
		//OBTENDO RETORNO DA PÁGINA COM PAGINAÇÃO
		ResponseEntity<RestResponsePage<PedidoDTO>> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, responseType);
		List<PedidoDTO> resultados = result.getBody().getContent();
		
		//PERCORRENDO O RETORNO
		for (PedidoDTO pedido : resultados) {
			System.out.println("-----------CAPA DO PEDIDO------------------");
			System.out.println(pedido.getId());
			System.out.println(pedido.getSituacao());
			System.out.println("------------------------------------------");
			System.out.println("----------ITENS DO PEDIDO----------------");
			
			//BUSCANDO OS ITENS DO PEDIDO
			PedidoItemDTO pedidoItemDTO = new PedidoItemDTO();
			pedidoItemDTO.setPedido(pedido);
			restTemplate = new RestTemplate();		
			ParameterizedTypeReference<RestResponsePage<PedidoItemDTO>> responseTypeItem = new ParameterizedTypeReference<RestResponsePage<PedidoItemDTO>>() { };
			HttpEntity<PedidoItemDTO> entityItem = new HttpEntity<PedidoItemDTO>(pedidoItemDTO);
			UriComponentsBuilder builderItem = UriComponentsBuilder.fromHttpUrl(IP_PORTA.concat(URL_RETORNO_PEDIDO_ITEM));
			
			ResponseEntity<RestResponsePage<PedidoItemDTO>> resultItem = restTemplate.exchange(builderItem.toUriString(), HttpMethod.POST, entityItem, responseTypeItem);
			List<PedidoItemDTO> resultadosItem = resultItem.getBody().getContent();
			for (PedidoItemDTO dto: resultadosItem) {
				System.out.println(dto.getId());
				System.out.println(dto.getProdutoServico().getDescricao());
				System.out.println(dto.getValor_unitario());
				System.out.println(dto.getQuantidade());
				System.out.println(dto.getValor_total());
			}
			System.out.println("------------------------------------------");	
		}
	}
}
