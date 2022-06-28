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

import com.pedidos.dto.PedidoItemDTO;
import com.pedidos.model.PedidoItem;

@Repository
public class PedidoItemDaoImpl implements PedidoItemDao {
	
	@Autowired
	EntityManager em;

	@Override
	public Page<PedidoItem> retornarListaPedidoItem(PedidoItemDTO filtros, Pageable page) {	
		CriteriaBuilder criterio = this.em.getCriteriaBuilder();
		CriteriaQuery<PedidoItem> sql = criterio.createQuery(PedidoItem.class);
		Root<PedidoItem> root = sql.from(PedidoItem.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (Objects.nonNull(filtros.getProdutoServico().getDescricao()) && StringUtils.isNotEmpty(filtros.getProdutoServico().getDescricao())) {
			predicates.add(criterio.like(criterio.lower(root.<String>get("descricao")), filtros.getProdutoServico().getDescricao().toLowerCase() ));
		}
		Predicate[] predArray = new Predicate[predicates.size()];
		predicates.toArray(predArray);
		sql.where(predArray);
		sql.toString();
		List<Order> orders = new ArrayList<Order>(2);
		orders.add(criterio.desc(root.get("id")));
		sql.orderBy(orders);
		TypedQuery<PedidoItem> query = this.em.createQuery(sql);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		Page<PedidoItem> result = new PageImpl<PedidoItem>(query.getResultList(), page, totalRows);
		return result;
	}
}
