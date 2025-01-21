package com.example.biblioteca.dao;

import com.example.biblioteca.model.Loan;
import org.springframework.stereotype.Repository;

@Repository
public class LoanDAO extends BaseDAO<Loan> {

  public LoanDAO() {
    super(Loan.class);
  }
}