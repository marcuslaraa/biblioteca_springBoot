package com.example.biblioteca.dao;

import com.example.biblioteca.model.Book;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public class BookDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void create(Book book) {
    System.out.println("DAO: criando o livro");
    entityManager.persist(book);
  }

  public Book findById(Long id) {
    return entityManager.find(Book.class, id);
  }

  public List<Book> findAll() {
    System.out.println("entrou");
    return entityManager.createQuery("from Book", Book.class).getResultList();
  }

  @Transactional
  public void update(Book book) {
    System.out.println("entrou em atualizar?");
    entityManager.merge(book);
  }

  @Transactional
  public void delete(Long id) {
    Book book = entityManager.find(Book.class, id);
    if (book != null) {
      entityManager.remove(book);
    }
  }
}
