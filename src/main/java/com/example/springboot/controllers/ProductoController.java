package com.example.springboot.controllers;
import com.example.springboot.models.Categorias;
import com.example.springboot.models.Productos;
import com.example.springboot.services.CategoriaServices;
import com.example.springboot.services.ProductosService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    //TODO: PRODUCTOS CONTROLLER //
    @Autowired
    private ProductosService productosService;
    @Autowired
    private CategoriaServices categoriaService;


    /* FORMATO DEL JSON //
    * {
        "nombre": "Teléfono inteligente",
        "slug":"telefono-inteligente-pantalla-grande",
        "descripcion":"teléfono inteligente con pantalla grande",
        "precio": 500,
        "foto":"telefono.jpg",
        "categoriaId": {
            "id": 1
            }
      }
    * */
    // TODO: CREATE
    @PostMapping("/productos")
    public ResponseEntity<?> createProductos(@Valid @RequestBody Productos producto, BindingResult result) {
        Optional<Categorias> categoria = Optional.ofNullable(categoriaService.findById(producto.getCategoriaId().getId()));
        Map<String, Object> response = new HashMap<>();
        Productos productoNuevo = null;

        if(!categoria.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if( result.hasErrors() ) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            producto.setCategoriaId(categoria.get());
            productoNuevo = productosService.guardar(producto);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al realizar insert en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("mensaje", "Producto creado con exito");
        response.put("producto", productoNuevo);
        return new ResponseEntity<>(productoNuevo, HttpStatus.CREATED);
    }

    //TODO: READ
    @GetMapping("/productos")
    public ResponseEntity<List<Productos>> findAllProductos() {
        return new ResponseEntity<>( productosService.listar(), HttpStatus.OK);
    }
    @GetMapping("productos/{id}")
    public ResponseEntity<?> findOneProducto(@PathVariable("id") Integer id){
        Productos producto = null;
        Map<String, Object> response = new HashMap<>();

        try {
            producto = productosService.findById(id);
        } catch( DataAccessException e ) {
            response.put("mensaje", "El producto no pudo ser encontrado en la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if( producto == null ){
            response.put("mensaje", "El producto: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: UPDATE
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> updateProductos(@Valid @RequestBody Productos producto, BindingResult result, @PathVariable("id") Integer id) {
        Productos productoActual = productosService.findById(id);
        Productos productoNuevo = null;
        Map<String, Object> response = new HashMap<>();

        if( result.hasErrors() ) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if ( productoActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            productoActual.setNombre(producto.getNombre());
            productoActual.setSlug(producto.getSlug());
            productoActual.setDescripcion(producto.getSlug());
            productoActual.setPrecio(producto.getPrecio());
            productoActual.setFoto(producto.getFoto());
            productoActual.setCategoriaId(producto.getCategoriaId());

            productoNuevo = productosService.guardar(productoActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Producto actualizado con exito.");
        response.put("producto", productoNuevo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO: DELETE
    public ResponseEntity<?> deleteProducto(@PathVariable("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            productosService.deleteById(id);
        } catch( DataAccessException e ) {
            response.put("mensaje", "Error al intentar eliminar el producto.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
