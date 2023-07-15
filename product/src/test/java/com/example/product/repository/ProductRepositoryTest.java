package com.example.product.repository;

import com.example.product.domain.Product;
import common.status.productStatus.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static common.status.productStatus.ProductStatus.SELL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    Product product;
    @BeforeEach
    void saveProductEntity(){
        product = repository.save(Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(ProductStatus.SELL).stock(100)
                .price(1000).build());

        Product product1 = Product.builder()
                .name("test product1").detail("임시 테스트 상품")
                .status(ProductStatus.SELL).stock(100)
                .price(1000).build();

        Product product2 = Product.builder()
                .name("test product2").detail("임시 테스트 상품")
                .status(ProductStatus.SELL).stock(100)
                .price(1000).build();

        repository.saveAll(List.of(product1, product2));
        repository.flush();
    }

    @DisplayName("저장한 product entity 는 id, createdAt, updatedAt 정보를 가지고 있어야한다.")
    @Test
    void saveProduct() {
        //given //when //then
        assertThat(product).extracting("id", "createdAt", "updatedAt")
                .doesNotContainNull();
    }

    @DisplayName("id를 통해서 product 를 조회할 수 있다.")
    @Test
    void getOneProductById(){
        //given //when
        Product findProduct = repository.findById(product.getId()).get();
        //then
        assertThat(findProduct)
                .extracting("name", "detail", "status", "price", "stock")
                .containsExactly(product.getName(), product.getDetail(), product.getStatus(), product.getPrice(), product.getStock());
    }

    @DisplayName("모든 product를 동시에 조회할 수 있다.")
    @Test
    void getProductList(){
        //given //when
        List<Product> products = repository.findAll();
        //then
        assertThat(products).hasSize(3);
    }

    @DisplayName("product의 id를 통해서 product 를 삭제할 수 있다.")
    @Test
    void deleteProduct(){
        //given //when
        repository.deleteById(product.getId());
        Optional<Product> optionalProduct = repository.findById(product.getId());
        //then
        assertThat(optionalProduct.isEmpty()).isTrue();
    }


    @DisplayName("name 없이 product 객체를 저장할 수 없다.")
    @Test
    void create_product_without_name() {
        //given
        Product product = Product.builder()
                .detail("임시 테스트 상품")
                .status(SELL).stock(100)
                .price(1000).build();
        // when //then
        assertThatThrownBy(() -> repository.save(product));
    }

    @DisplayName("price 없이 product 객체를 저장할 수 없다.")
    @Test
    void create_product_without_price() {
        //given
        Product product = Product.builder()
                .name("test product").detail("임시 테스트 상품")
                .status(SELL).stock(100)
                .build();
        // when //then
        assertThatThrownBy(() -> repository.save(product));
    }
}