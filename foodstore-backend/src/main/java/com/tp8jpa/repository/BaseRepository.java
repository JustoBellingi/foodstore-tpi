package com.tp8jpa.repository;

import com.tp8jpa.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

// Clase base para reutilizar operaciones CRUD
public abstract class BaseRepository<T> {

    // Guarda el tipo de entidad que maneja el repositorio
    private final Class<T> entityClass;

    // Constructor: recibe la clase de la entidad
    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // GUARDAR / ACTUALIZAR
    public T guardar(T entity) {

        // Obtiene un EntityManager desde JPAUtil
        EntityManager em = JPAUtil.getEntityManager();

        // Se manejan las tansiciones
        EntityTransaction tx = em.getTransaction();

        try {

            // Inicia la transacción
            tx.begin();

            // merge()
            // Si la entidad no existe -> insert
            // Si ya existe -> update
            T entidadGuardada = em.merge(entity);

            // Confirma los cambios en la base de datos
            tx.commit();

            return entidadGuardada;

        } catch (Exception e) {

            // Si ocurre un error los cambios se revierten
            if (tx.isActive()) {
                tx.rollback();
            }

            throw e;

        } finally {

            // Siempre cierra el EntityManager
            em.close();
        }
    }

    // BUSCAR POR ID

    public Optional<T> buscarPorId(Long id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            // Busca la entidad por clave primaria
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);

        } finally {

            em.close();
        }
    }

    // LISTAR SOLO ACTIVOS
    public List<T> listarActivos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {

            // JPQL dinámico:
            String jpql = "SELECT e FROM "
                    + entityClass.getSimpleName() // para que funcione con cualquier entidad
                    + " e WHERE e.eliminado = false";

            // Ejecuta la consulta y devuelve la lista
            return em.createQuery(jpql, entityClass)
                    .getResultList();

        } finally {

            em.close();
        }
    }

    // ELIMINACIÓN LÓGICA
    public boolean eliminarLogico(Long id) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {

            // Busca la entidad
            T entity = em.find(entityClass, id);

            // Si no existe, retorna false
            if (entity == null) {
                return false;
            }

            tx.begin();
            // Busca el método setEliminado(boolean) para poder usarlo en cualquier entidad
            Method setEliminado =
                    entityClass.getMethod("setEliminado", boolean.class);

            // Ejecuta setEliminado(true)
            setEliminado.invoke(entity, true);

            // Actualiza la entidad en la BD
            em.merge(entity);

            // Confirma cambios
            tx.commit();

            return true;

        } catch (Exception e) {

            // Revierte cambios si hubo error
            if (tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();

            return false;

        } finally {

            em.close();
        }
    }
}