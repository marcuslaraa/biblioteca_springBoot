package com.example.biblioteca.service;

import com.example.biblioteca.dao.LoanDAO;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LoanService {

  @Autowired
  private LoanDAO loanDAO;

  @Transactional
  public void createLoan(Loan loan) {
    Set<Book> books = loan.getBooks();
    if (books != null) {
      for (Book book : books) {
        if (book.getLoans() == null) {
          book.setLoans(new HashSet<>());
        }
        book.getLoans().add(loan);
      }
    }
    loanDAO.create(loan);
  }

  @Transactional
  public Loan findLoanById(Long id) {
    return loanDAO.findById(id);
  }

  @Transactional
  public List<Loan> findAllLoans() {
    return loanDAO.findAll();
  }

  @Transactional
  public void updateLoan(Loan loan) {
    loanDAO.update(loan);
  }

  @Transactional
  public void deleteLoan(Long id) {
    loanDAO.delete(id);
  }
}