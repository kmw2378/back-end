package org.kakaoshare.backend.fixture;

import org.kakaoshare.backend.domain.brand.entity.Brand;
import org.kakaoshare.backend.domain.product.entity.Product;

public enum ProductFixture {
    TEST_PRODUCT("Test Product", 999L, "Test Type"),
    CAKE("케이크", 10_000L, "Dessert"),
    COFFEE("커피", 3_000L, "Beverage");

    private final String name;
    private final Long price;
    private final String type;

    ProductFixture(String name, Long price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Product 생성() {
        return 생성(null);
    }

    public Product 생성(final Long productId) {
        return 생성(productId, null);
    }

    public Product 생성(final Long productId, final Brand brand) {
        return 생성(productId, brand, this.price);
    }

    public Product 생성(final Long productId, final Brand brand, final BigDecimal price) {
        return Product.builder()
                .productId(productId)
                .brand(brand)
                .name(name)
                .price(price)
                .type(type)
                .build();
    }

    public Product 브랜드_설정_생성(final Brand brand) {
        return 생성(null, brand);
    }

    public Product 가격_설정_생성(final BigDecimal price) {
        return 생성(null, null, price);
    }

    public Product 브랜드_가격_설정_생성(final Brand brand, final BigDecimal price) {
        return 생성(null, brand, price);
    }
}
