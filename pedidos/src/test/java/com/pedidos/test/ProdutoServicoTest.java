package com.pedidos.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.utils.RestResponsePage;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServicoTest {
	
	private static final String URL_RETORNO_PRODUTO_SERVICO = "api/produtoServico/retornarProdutoServico";
	private static final String URL_INSERIR_PRODUTO_SERVICO = "api/produtoServico/gravarProdutoServico";
	private static final String IP_PORTA = "http://localhost:8080/";
	
	@Test
	public void GravarProdutoServico() throws Exception{
		ProdutoServicoDTO produtoServicoDTO = new ProdutoServicoDTO();
		produtoServicoDTO.setDescricao("TERRA PLANAJEM");
		produtoServicoDTO.setEnabled(false);
		produtoServicoDTO.setTipo("SERVICO");
		//this.chamarServicoGravarProduto(produtoServicoDTO);
	}

	private void chamarServicoGravarProduto(ProdutoServicoDTO produtoServicoDTO) {
		RestTemplate restTemplate = new RestTemplate();		
		HttpEntity<ProdutoServicoDTO> request = new HttpEntity<ProdutoServicoDTO>(produtoServicoDTO);
		restTemplate.exchange(IP_PORTA.concat(URL_INSERIR_PRODUTO_SERVICO), HttpMethod.PUT, request, ProdutoServicoDTO.class);
	}

	@Test
	public void RetornarProdutoServicos() throws Exception {
		//PREPARANDO FILTROS
		ProdutoServicoDTO filtros = new ProdutoServicoDTO();
		//filtros.setEnabled(true);
		//filtros.setDescricao("TIRAGEM DE AMOSTRAS");
		//filtros.setTipo("SERVICO");
		
		//CHAMANDO O SERVIÇO REST
		RestTemplate restTemplate = new RestTemplate();		
		ParameterizedTypeReference<RestResponsePage<ProdutoServicoDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<ProdutoServicoDTO>>() { };
		HttpEntity<ProdutoServicoDTO> entity = new HttpEntity<ProdutoServicoDTO>(filtros);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(IP_PORTA.concat(URL_RETORNO_PRODUTO_SERVICO));
		
		//OBTENDO RETORNO DA PÁGINA COM PAGINAÇÃO
		ResponseEntity<RestResponsePage<ProdutoServicoDTO>> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, responseType);
		List<ProdutoServicoDTO> resultados = result.getBody().getContent();
		
		//PERCORRENDO O RETORNO
		for (ProdutoServicoDTO produtoServico : resultados) {
			System.out.println("-----------------------------");
			System.out.println(produtoServico.getId());
			System.out.println(produtoServico.getEnabled());
			System.out.println(produtoServico.getDescricao());
			System.out.println("-----------------------------");
			produtoServico.setEnabled(true);//INATIVAR UM PRODUTO JÁ CADASTRADO
			this.chamarServicoGravarProduto(produtoServico);
		}
	}
}
