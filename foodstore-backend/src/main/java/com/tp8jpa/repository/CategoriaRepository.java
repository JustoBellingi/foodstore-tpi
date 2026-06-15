package com.tp8jpa.repository;

import com.tp8jpa.entities.Categoria;
import com.tp8jpa.entities.Producto;
import com.tp8jpa.utils.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CategoriaRepository extends BaseRepository<Categoria> {

    public CategoriaRepository() {
        super(Categoria.class);
    }

    public void agregarProducto(Long idCategoria, Producto producto) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin();

            Categoria categoria = em.find(Categoria.class, idCategoria);

            categoria.getProductos().add(producto);

            em.merge(categoria);

            tx.commit();

        } catch (Exception e) {

            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {

            em.close();
        }
    }
}