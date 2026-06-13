package com.tp8jpa.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Categoria extends Base {

    private String nombre;

    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Set<Producto> productos = new HashSet<>();

    public Categoria() {
    }

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public void addProducto(Producto producto) {
        this.productos.add(producto);
    }
}
