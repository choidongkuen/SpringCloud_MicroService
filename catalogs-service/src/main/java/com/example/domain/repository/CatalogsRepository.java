package com.example.domain.repository;

import com.example.domain.entity.Catalogs;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CatalogsRepository extends JpaRepository<Catalogs,Long> {
    Optional<Catalogs> findByProductId(String productId);
}
