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

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.pedidos.dto.PedidoDTO;
import com.pedidos.model.Pedido;
import com.pedidos.models.enums.SitucaoPedidoEnum;

@Repository
public class PedidoDaoImpl implements PedidoDao {
	
	@Autowired
	EntityManager em;

	@Override
	public Page<Pedido> retornarListaPedido(PedidoDTO filtros, Pageable page) {	
		CriteriaBuilder criterio = this.em.getCriteriaBuilder();
		CriteriaQuery<Pedido> sql = criterio.createQuery(Pedido.class);
		Root<Pedido> root = sql.from(Pedido.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (Objects.nonNull(filtros.getNumero_pedido()) && filtros.getNumero_pedido() > NumberUtils.LONG_ZERO) {
			predicates.add(criterio.equal(root.get("numero_pedido"), filtros.getNumero_pedido()));
		}
		if (Objects.nonNull(filtros.getSituacao())) {
			predicates.add(criterio.equal(root.<SitucaoPedidoEnum>get("situacao"), filtros.getSituacao()));
		}
		Predicate[] predArray = new Predicate[predicates.size()];
		predicates.toArray(predArray);
		sql.where(predArray);
		sql.toString();
		List<Order> orders = new ArrayList<Order>(2);
		orders.add(criterio.desc(root.get("data_pedido")));
		sql.orderBy(orders);
		TypedQuery<Pedido> query = this.em.createQuery(sql);
		int totalRows = query.getResultList().size();
		query.setFirstResult(page.getPageNumber() * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		Page<Pedido> result = new PageImpl<Pedido>(query.getResultList(), page, totalRows);
		return result;
	}
}
