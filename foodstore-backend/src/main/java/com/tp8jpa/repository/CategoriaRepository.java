package com.tp8jpa.repository;

import com.tp8jpa.entities.Categoria;


// Repositorio específico para la entidad Categoria
public class CategoriaRepository extends BaseRepository<Categoria> {

    // Constructor
    public CategoriaRepository() {

        // Le pasa la clase Categoria al BaseRepository
        super(Categoria.class);
    }
}