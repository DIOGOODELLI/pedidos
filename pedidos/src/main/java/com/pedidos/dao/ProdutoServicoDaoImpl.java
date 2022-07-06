package com.pedidos.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pedidos.dto.ProdutoServicoDTO;
import com.pedidos.model.ProdutoServico;
import com.pedidos.models.enums.TipoProdutoEnum;

@Repository
public class ProdutoServicoDaoImpl implements ProdutoServicoDao {
	
	@Autowired
	EntityManager em;

	@Override
	public Page<ProdutoServico> retornarListaProdutoServico(ProdutoServicoDTO filtros, Pageable page) {	
		CriteriaBuilder criterio = this.em.getCriteriaBuilder();
		CriteriaQuery<ProdutoServico> sql = criterio.createQuery(ProdutoServico.class);
		Root<ProdutoServico> root = sql.from(ProdutoServico.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (Objects.nonNull(filtros.getDescricao()) && StringUtils.isNotEmpty(filtros.getDescricao())) {
			predicates.add(criterio.like(criterio.lower(root.<String>get("descricao")), "%" + filtros.getDescricao().toLowerCase() + "%"));
		}
		if (Objects.nonNull(filtros.getTipo())) {
			predicates.add(criterio.equal(root.<TipoProdutoEnum>get("tipo"), filtros.getTipo()));
		}
		if (Objects.nonNull(filtros.getEnabled())) {
			predicates.add(criterio.equal(root.<Boolean>get("enabled"), filtros.getEnabled()));
		}
		Predicate[] predArray = new Predicate[predicates.size()];
		predicates.toArray(predArray);
		sql.where(predArray);
		sql.toString();
		List<Order> orders = new ArrayList<Order>(2);
		orders.add(criterio.asc(root.get("id")));
		sql.orderBy(orders);
		TypedQuery<ProdutoServico> query = this.em.createQuery(sql);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		Page<ProdutoServico> result = new PageImpl<ProdutoServico>(query.getResultList(), page, totalRows);
		return result;
	}
}
