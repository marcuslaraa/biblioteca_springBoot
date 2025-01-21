package com.example.biblioteca.controller;

import com.example.biblioteca.model.Loan;
import com.example.biblioteca.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController extends BaseController<Loan> {

  @Autowired
  private LoanService loanService;

  @Override
  protected void createEntity(Loan loan) {
    loanService.createLoan(loan);
  }

  @Override
  protected Loan findEntityById(Long id) {
    return loanService.findLoanById(id);
  }

  @Override
  protected List<Loan> findAllEntities() {
    return loanService.findAllLoans();
  }

  @Override
  protected void updateEntity(Long id, Loan loanDetails) {
    loanService.updateLoan(id, loanDetails);
  }

  @Override
  protected void deleteEntity(Long id) {
    loanService.deleteLoan(id);
  }
}