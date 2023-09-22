package com.example.domain.repository;

import com.example.domain.entity.Catalogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogsRepository extends JpaRepository<Catalogs,Long> {
}
