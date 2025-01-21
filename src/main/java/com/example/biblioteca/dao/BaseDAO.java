package com.example.biblioteca.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BaseDAO<T> {

  @PersistenceContext
  protected EntityManager entityManager;

  private Class<T> entityClass;

  public BaseDAO(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  @Transactional
  public void create(T entity) {
    entityManager.persist(entity);
  }

  public T findById(Long id) {
    return entityManager.find(entityClass, id);
  }

  public List<T> findAll() {
    return entityManager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
  }

  @Transactional
  public void update(T entity) {
    entityManager.merge(entity);
  }

  @Transactional
  public void delete(Long id) {
    T entity = entityManager.find(entityClass, id);
    if (entity != null) {
      entityManager.remove(entity);
    }
  }
}