package com.tp8jpa.repository;

import com.tp8jpa.entities.Pedido;
import com.tp8jpa.entities.enums.Estado;
import com.tp8jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PedidoRepository extends BaseRepository<Pedido> {

    public PedidoRepository() {
        super(Pedido.class);
    }

    public List<Pedido> buscarPorUsuario(Long idUsuario) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            // Consulta JPQL: retorna todos los pedidos activos de un usuario

            String jpql = """
                    SELECT p
                    FROM Usuario u
                    JOIN u.pedidos p
                    WHERE u.id = :uid
                    AND p.eliminado = false
                    """;

            return em.createQuery(jpql, Pedido.class)
                    .setParameter("uid", idUsuario)
                    .getResultList();

        } finally {

            em.close();

        }
    }

    public List<Pedido> buscarPorEstado(Estado estado) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            // Consulta JPQL: retorna todos los pedidos activos con un estado específico

            String jpql = """
                    SELECT p
                    FROM Pedido p
                    WHERE p.estado = :estado
                    AND p.eliminado = false
                    """;

            return em.createQuery(jpql, Pedido.class)
                    .setParameter("estado", estado)
                    .getResultList();

        } finally {

            em.close();

        }
    }
}