package com.example.springboot.models;

import jakarta.persistence.*;

@Entity
@Table( name = "categoria" )
public class Categorias {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;
    private String nombre;
    private String slug;

    public Categorias() {
    }

    public Categorias(String nombre, String slug) {
        this.nombre = nombre;
        this.slug = slug;
    }

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
}
