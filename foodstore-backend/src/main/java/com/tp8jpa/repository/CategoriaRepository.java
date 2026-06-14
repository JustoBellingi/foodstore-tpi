package com.tp8jpa.repository;

import com.tp8jpa.entities.Categoria;

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }

}