package com.example.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table( name = "productos" )
public class Productos {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;
    @NotEmpty( message = "El campo no puede estar vacio." )
    private String nombre;
    private String slug;
    @NotEmpty( message = "El campo no puede estar vacio." )
    private String descripcion;
    @NotNull( message = "El campo no puede ser nulo." )
    private Integer precio;
    private String foto;
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "categoria_id", referencedColumnName = "id" )
    private Categorias categoriaId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Categorias getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Categorias categoriaId) {
        this.categoriaId = categoriaId;
    }
}
