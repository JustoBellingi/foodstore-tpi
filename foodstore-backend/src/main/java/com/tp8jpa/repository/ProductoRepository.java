package com.tp8jpa.repository;

import com.tp8jpa.entities.Producto;
import com.tp8jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            // Consulta JPQL: retorna productos activos de una categoría específica
            // Filtra por categoria.id y por eliminado = false

            String jpql = """
                    SELECT p
                    FROM Categoria c
                    JOIN c.productos p
                    WHERE c.id = :catId
                    AND p.eliminado = false
                    """;

            return em.createQuery(jpql, Producto.class)
                    .setParameter("catId", categoriaId)
                    .getResultList();

        } finally {

            em.close();

        }
    }
}
