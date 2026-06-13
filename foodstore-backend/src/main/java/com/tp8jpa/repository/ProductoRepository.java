package com.tp8jpa.repository;

import com.tp8jpa.entities.Producto;
import com.tp8jpa.utils.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

// Repositorio específico para la entidad Producto
public class ProductoRepository extends BaseRepository<Producto> {

    // Constructor
    public ProductoRepository() {

        // Le indica al BaseRepository que maneja la clase Producto
        super(Producto.class);
    }
    // BUSCAR PRODUCTOS POR CATEGORÍA

    // Busca productos activos pertenecientes usando JPQL 
    public List<Producto> buscarPorCategoria(Long categoriaId) {

        // Obtiene el EntityManager
        EntityManager em = JPAUtil.getEntityManager();

        try {

            // Busca productos
            // PARA NAVEGAR LA RELACION, SE UTILIZA p.categoria.id
            String jpql = """
                    SELECT p
                    FROM Producto p
                    WHERE p.categoria.id = :categoriaId
                    AND p.eliminado = false
                    """;

            // Devuelve la consulta y devuelve objetos Producto
            TypedQuery<Producto> query =
                    em.createQuery(jpql, Producto.class);

            // Asigna valor al parámetro :categoriaId
            query.setParameter("categoriaId", categoriaId);

            // Ejecuta la consulta y devuelve la lista
            return query.getResultList();

        } finally {

            // Cierra el EntityManager
            em.close();
        }
    }
}