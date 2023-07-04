package com.order.repository;

import common.dto.OrderDto;
import common.request.OrderListGetRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.order.domain.QOrder.*;

@Repository
@Transactional
public class CustomOrderRepository {

    private final JPAQueryFactory query;

    @Autowired
    public CustomOrderRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public Page<OrderDto> findOrders(OrderListGetRequest request) {

        Pageable pagable = PageRequest.of(request.getPageIndex(), request.getPageSize());

        List<OrderDto> content = query.select(Projections.bean(OrderDto.class,
                        order.id.as("code"),
                        order.totalPrice,
                        order.status,
                        order.userCode,
                        order.address))
                .from(order)
                .where(order.userCode.eq(request.getUserCode()))
                .offset(pagable.getPageNumber())
                .limit(pagable.getPageSize())
                .fetch();

        Long totalCount = query.select(order.count())
                .from(order)
                .where(order.userCode.eq(request.getUserCode()))
                .fetchOne();
        return new PageImpl<>(content, pagable, totalCount);
    }
}
