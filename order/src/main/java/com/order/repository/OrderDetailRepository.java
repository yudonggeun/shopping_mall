package com.order.repository;

import com.order.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderCode(Long orderCode);
    List<OrderDetail> findAllByOrderCodeIn(Collection<Long> orderCodes);
}
