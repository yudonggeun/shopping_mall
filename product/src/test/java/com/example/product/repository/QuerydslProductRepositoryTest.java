package com.example.product.repository;

import com.example.product.domain.Product;
import com.example.product.domain.QProduct;
import com.example.product.dto.request.ProductCondition;
import com.example.product.dto.request.ProductListConditionRequest;
import com.example.product.status.ProductSellStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuerydslProductRepositoryTest {

    @Autowired
    QuerydslProductRepository repository;
    @Autowired
    EntityManager em;

    @DisplayName("QProduct Q object 가 정상적으로 인식할 수 있는가?")
    @Test
    void queryDsl_import_test() {
        //given
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();
        em.persist(product);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;
        //when
        Product result = query.selectFrom(qProduct).fetchOne();
        //then
        assertThat(result).isEqualTo(product);
    }

    @DisplayName("getList 조건이 없다면 기본 정책을 사용한다.")
    @Test
    void getList_without_condition(){
        //given
        ProductListConditionRequest condition = null;
        List<Product> samples = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            samples.add(Product.builder()
                    .name("test product" + i).detail("임시 테스트 상품" + i)
                    .status(ProductSellStatus.SELL).stock(100 * i)
                    .price(1000 * i).build());
        }
        samples.forEach(product -> em.persist(product));
        //when
        Page<Product> productsPage = repository.getList(condition);
        //then
        assertThat(productsPage.getContent()).containsAll(samples.subList(0, 9));
        assertThat(productsPage.getSize()).isEqualTo(ProductListConditionRequest.DEFAULT.getPageSize());
        assertThat(productsPage.getPageable().getOffset()).isEqualTo(ProductListConditionRequest.DEFAULT.getPageIndex());
        assertThat(productsPage.getTotalElements()).isEqualTo(20);
    }

    @DisplayName("디폴트 조건을 이용하여 상품 조회")
    @Test
    void getList_10_products_from_0_page() {
        //given
        ProductListConditionRequest condition = new ProductListConditionRequest();
        List<Product> samples = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            samples.add(Product.builder()
                    .name("test product" + i).detail("임시 테스트 상품" + i)
                    .status(ProductSellStatus.SELL).stock(100 * i)
                    .price(1000 * i).build());
        }
        samples.forEach(product -> em.persist(product));
        //when
        Page<Product> productsPage = repository.getList(condition);
        //then
        assertThat(productsPage.getContent()).containsAll(samples.subList(0, 9));
        assertThat(productsPage.getSize()).isEqualTo(condition.getPageSize());
        assertThat(productsPage.getPageable().getOffset()).isEqualTo(condition.getPageIndex());
        assertThat(productsPage.getTotalElements()).isEqualTo(20);
    }

    @DisplayName("전체 상품 수를 조회할 수 있다.")
    @Test
    void totalCount() {
        //given
        ProductCondition condition = new ProductCondition(Set.of(ProductSellStatus.SELL));
        for (int i = 0; i < 21; i++) {
            em.persist(Product.builder()
                    .name("test product" + i).detail("임시 테스트 상품" + i)
                    .status(ProductSellStatus.SELL).stock(100 * i)
                    .price(1000 * i).build());
        }
        //when
        long count = repository.totalCount(condition);
        //then
        assertThat(count).isEqualTo(21);
    }

    @DisplayName("product condition 이 null 인 경우 " +
            "product condition 의 기본 설정을 조건으로 사용한다.")
    @Test
    void totalCount_without_condition(){
        //given
        ProductCondition condition = null;
        for (int i = 0; i < 22; i++) {
            em.persist(Product.builder()
                    .name("test product" + i).detail("임시 테스트 상품" + i)
                    .status(ProductSellStatus.HIDE).stock(100 * i)
                    .price(1000 * i).build());
        }
        //when
        long count = repository.totalCount(condition);
        //then
        assertThat(count).isEqualTo(0);
    }
}