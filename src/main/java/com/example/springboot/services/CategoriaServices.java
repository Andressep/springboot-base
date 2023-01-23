package com.example.springboot.services;

import com.example.springboot.models.Categorias;
import com.example.springboot.repository.ICategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoriaServices {

    @Autowired
    private ICategoriasRepository repository;

    // RETORNA UNA LISTA DE CATEGORIAS ORDENADAS POR SU "ID" //
    public List<Categorias> listar() {
        return repository.findAll(Sort.by("id").descending());
    }

    public Categorias findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Categorias guardar(Categorias categoria) {
        return repository.save(categoria);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}

