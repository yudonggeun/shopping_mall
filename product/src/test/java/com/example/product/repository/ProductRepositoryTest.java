package com.example.product.repository;

import com.example.product.domain.Product;
import com.example.product.status.ProductSellStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @DisplayName("product를 저장할 수 있다.")
    @Test
    void save() {
        //given
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();
        //when //then
        repository.save(product);
    }

    @DisplayName("저장한 product entity 는 id, createdAt, updatedAt 정보를 가지고 있어야한다.")
    @Test
    void saveProduct() {
        //given
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();
        //when
        Product savedProduct = repository.saveAndFlush(product);
        //then
        assertThat(savedProduct).extracting("id", "createdAt", "updatedAt")
                .doesNotContainNull();
    }

    @DisplayName("id를 통해서 product 를 조회할 수 있다.")
    @Test
    void getOneProductById(){
        //given
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();
        product = repository.saveAndFlush(product);
        //when
        Optional<Product> optionalProduct = repository.findById(product.getId());
        //then
        assertThat(optionalProduct.get())
                .extracting("name", "detail", "status", "price", "stock")
                .containsExactly(product.getName(), product.getDetail(), product.getStatus(), product.getPrice(), product.getStock());
    }

    @DisplayName("모든 product를 동시에 조회할 수 있다.")
    @Test
    void getProductList(){
        //given
        Product product1 = Product.builder()
                .name("test product1").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();

        Product product2 = Product.builder()
                .name("test product2").detail("임시 테스트 상품")
                .status(ProductSellStatus.SELL).stock(100)
                .price(1000).build();

        repository.saveAll(List.of(product1, product2));
        //when
        List<Product> products = repository.findAll();

        //then
        assertThat(products).hasSize(2);
    }

    @DisplayName("product의 id를 통해서 product 를 삭제할 수 있다.")
    @Test
    void deleteProduct(){
        //given
        Product product = repository.saveAndFlush(
                Product.builder()
                        .name("test product").detail("임시 테스트 상품")
                        .status(ProductSellStatus.SELL).stock(100)
                        .price(1000).build()
        );
        //when
        repository.deleteById(product.getId());
        Optional<Product> optionalProduct = repository.findById(product.getId());
        //then
        assertThat(optionalProduct.isEmpty()).isTrue();
    }
}