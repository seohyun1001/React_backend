package org.zerock.backendshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.backendshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
