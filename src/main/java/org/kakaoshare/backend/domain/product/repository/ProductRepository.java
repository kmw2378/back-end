package org.kakaoshare.backend.domain.product.repository;

import org.kakaoshare.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}