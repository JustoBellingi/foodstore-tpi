package com.tp8jpa.repository;
import com.tp8jpa.entities.Usuario;
import com.tp8jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Optional<Usuario> buscarPorMail(String mail) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            // Consulta JPQL: busca un usuario activo por su dirección de correo
            // Retorna Optional para manejar el caso en que el mail no esté registrado

            String jpql = """
                    SELECT u
                    FROM Usuario u
                    WHERE u.mail = :mail
                    AND u.eliminado = false
                    """;

            List<Usuario> usuarios = em.createQuery(jpql, Usuario.class)
                    .setParameter("mail", mail)
                    .getResultList();

            if (usuarios.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(usuarios.get(0));

        } finally {

            em.close();

        }
    }
}