package com.diogo.gastos.repository;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.diogo.gastos.dao.GastosDaoPage;
import com.diogo.gastos.dto.GastosDTO;
import com.diogo.gastos.service.GastosService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class GastoDaoPageTest {
	
	@Autowired
	GastosDaoPage gastosDaoPage;
	
	@Autowired
	private GastosService service; 
	
	@Test
	public void GravarGasto() {
		GastosDTO gasto = new GastosDTO();
		gasto.setDescricao("Compra de arroz");
		gasto.setNomePessoa("Diogo Odelli");
		gasto.setDataHora(new Date());
		gasto.setTags("Mercado;obrigatória");
		gasto.setValor(29.50);
		service.salvar(gasto);
	}
	
	
	@Test
	public void RetornarGastos() {
		service.retornarGastos();
	}

}
