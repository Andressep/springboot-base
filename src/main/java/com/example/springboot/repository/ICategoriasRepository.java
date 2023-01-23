package com.example.springboot.repository;

import com.example.springboot.models.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriasRepository extends JpaRepository<Categorias, Integer> {
}
