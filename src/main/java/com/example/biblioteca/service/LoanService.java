package com.example.biblioteca.service;

import com.example.biblioteca.dao.LoanDAO;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class LoanService {

  @Autowired
  private LoanDAO loanDAO;

  @Transactional
  public void createLoan(Loan loan) {
    if (loan.getUser() == null || loan.getUser().isEmpty()) {
      throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio");
    }

    if (loan.getLoanDate() == null) {
      throw new IllegalArgumentException("Data de retirada não pode ser nula");
    }

    if (loan.getReturnDate() == null) {
      throw new IllegalArgumentException("Data de retorno não pode ser nula");
    }

    Set<Book> books = loan.getBooks();
    if (books == null) {
      throw new IllegalArgumentException("Books cannot be null");
    }

    loanDAO.create(loan);
  }

  @Transactional
  public Loan findLoanById(Long id) {
    Loan loan = loanDAO.findById(id);
    if (loan == null) {
      throw new IllegalArgumentException("Empréstimo não encontrado");
    }
    return loan;
  }

  @Transactional
  public List<Loan> findAllLoans() {
    List<Loan> loan = loanDAO.findAll();

    if (loan == null) {
      throw new IllegalArgumentException("Empréstimos não encontrados");
    }
    return loan;
  }

  @Transactional
  public void updateLoan(Long id, Loan updatedLoan) {
    Loan existingLoan = loanDAO.findById(id);
    if (existingLoan == null) {
      throw new IllegalArgumentException("Empréstimo não encontrado");
    }

    if (updatedLoan.getUser() == null || updatedLoan.getUser().isEmpty()) {
      throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio");
    }

    if (updatedLoan.getLoanDate() == null) {
      throw new IllegalArgumentException("Data de retirada não pode ser nula");
    }

    if (updatedLoan.getReturnDate() == null) {
      throw new IllegalArgumentException("Data de retorno não pode ser nula");
    }

    Set<Book> books = updatedLoan.getBooks();
    if (books == null) {
      throw new IllegalArgumentException("Books cannot be null");
    }

    existingLoan.setUser(updatedLoan.getUser());
    existingLoan.setLoanDate(updatedLoan.getLoanDate());
    existingLoan.setReturnDate(updatedLoan.getReturnDate());
    existingLoan.setBooks(updatedLoan.getBooks());

    loanDAO.update(existingLoan);
  }

  @Transactional
  public void deleteLoan(Long id) {
    Loan existingLoan = loanDAO.findById(id);
    if (existingLoan == null) {
      throw new IllegalArgumentException("Empréstimo não encontrado");
    }
    loanDAO.delete(id);
  }
}