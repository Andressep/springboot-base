package com.example.springboot.repository;

import com.example.springboot.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductosRepository extends JpaRepository<Productos, Integer> {
}
