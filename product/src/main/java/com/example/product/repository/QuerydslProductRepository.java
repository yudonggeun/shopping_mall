package com.example.product.repository;

import com.example.product.domain.Product;
import com.example.product.domain.QProduct;
import common.request.ProductCondition;
import common.request.ProductListConditionRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
@Transactional(readOnly = true)
public class QuerydslProductRepository {

    private final JPAQueryFactory query;
    private final QProduct table;

    public QuerydslProductRepository(EntityManager em) {
        query = new JPAQueryFactory(em);
        table = QProduct.product;
    }

    public Page<Product> getList(ProductListConditionRequest request) {

        if(request == null) request = ProductListConditionRequest.DEFAULT;
        ProductCondition condition = request.getCondition();

        Pageable pageable = request.getPageable();
        List<Product> result = query.selectFrom(table)
                .where(table.status.in(condition.status()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(result, pageable, totalCount(condition));
    }

    public long totalCount(ProductCondition condition) {
        if(condition == null) condition = ProductCondition.DEFAULT;
        return query.select(table.count())
                .from(table)
                .where(table.status.in(condition.status()))
                .fetchOne();
    }
}
