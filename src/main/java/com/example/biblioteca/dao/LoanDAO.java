package com.example.biblioteca.dao;

import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public class LoanDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void create(Loan loan) {
    entityManager.persist(loan);
  }

  public Loan findById(Long id) {
    return entityManager.find(Loan.class, id);
  }

  @Transactional
  public List<Loan> findAll() {
    return entityManager.createQuery("from loans", Loan.class).getResultList();
  }

  @Transactional
  public void update(Loan loan) {
    entityManager.merge(loan);
  }

  @Transactional
  public void delete(Long id) {
    Loan loan = entityManager.find(Loan.class, id);
    if (loan != null) {
      entityManager.remove(loan);
    }
  }
}