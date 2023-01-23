package com.example.springboot.controllers;

import com.example.springboot.models.Categorias;
import com.example.springboot.services.CategoriaServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaServices categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<List<Categorias>> findAllCategorias() {
        return new ResponseEntity<>( categoriaService.listar(), HttpStatus.OK);
    }

    // TODO: CREATE
    @PostMapping("/categorias")
    public ResponseEntity<?> createCategorias(@Valid @RequestBody Categorias categoria, BindingResult result) {
        Categorias newCategoria = null;
        Map<String, Object> response = new HashMap<>();

        if( result.hasErrors() ) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newCategoria = categoriaService.guardar(categoria);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al realizar insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("mensaje", "La categoria ha sido creada con exito.");
        response.put("categoria", newCategoria);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //TODO: READ
    @GetMapping("categorias/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        Categorias actualCategoria = null;
        Map<String, Object> response = new HashMap<>();
        try {
            actualCategoria = categoriaService.findById(id);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al buscar la categoria en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if( actualCategoria == null ) {
            response.put("mensaje", "La categoria ID: ".concat(id.toString().concat(" no ha sido encontrada en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("mensaje", "La categoria solicitada fue encontrada.");
        response.put("categoria", actualCategoria);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //TODO: UPDATE
    @PutMapping("categorias/{id}")
    public ResponseEntity<?> updateCategorias(@Valid @RequestBody Categorias categoria, BindingResult result, @PathVariable("id") Integer id) {
        Categorias actualCategoria = categoriaService.findById(id);
        Categorias newCategorias = null;
        Map<String, Object> response = new HashMap<>();

        if( result.hasErrors() ) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if( actualCategoria == null ) {
            response.put("mensaje", "La categoria".concat(id.toString().concat(" no existen en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            actualCategoria.setNombre(categoria.getNombre());
            actualCategoria.setSlug(categoria.getSlug());
            newCategorias = categoriaService.guardar(actualCategoria);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al intentar modificar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("mensaje", "Categoria actualizada con exito.");
        response.put("categoria", newCategorias);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: DELETE
    @DeleteMapping("categorias/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            categoriaService.delete(id);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al intentar borrar la categoria.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La categoria ha sido eliminada con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
