package com.example.springboot.services;

import com.example.springboot.models.Productos;
import com.example.springboot.repository.IProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
public class ProductosService {

    @Autowired
    private IProductosRepository repository;

    // LISTAR PRODUCTOS ORDENADOS POR SU "ID" //
    @Transactional(readOnly = true)
    public List<Productos> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Productos findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Productos guardar(Productos productos) {
        return repository.save(productos);
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
