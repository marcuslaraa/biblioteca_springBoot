package com.example.biblioteca.controller;

import com.example.biblioteca.model.Loan;
import com.example.biblioteca.service.LoanService;
import com.example.biblioteca.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/loans")
public class LoanController {

  private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

  @Autowired
  private LoanService loanService;

  @PostMapping
  public CompletableFuture<ResponseEntity<?>> createLoan(@RequestBody Loan loan) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        loanService.createLoan(loan);
        logger.info("Emprestimo criado com sucesso");
        return ResponseEntity.ok(loan);
      } catch (Exception e) {
        logger.error("Error creating loan", e);
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao criar o empréstimo");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  @GetMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> getLoanById(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Loan loan = loanService.findLoanById(id);
        if (loan != null) {
          return ResponseEntity.ok(loan);
        } else {
          ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "Empréstimo não encontrado");
          return ResponseEntity.status(404).body(errorResponse);
        }
      } catch (Exception e) {
        logger.error("Error retrieving loan", e);
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao buscar o empréstimo");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  @GetMapping
  public CompletableFuture<ResponseEntity<?>> getAllLoans() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        List<Loan> loans = loanService.findAllLoans();
        return ResponseEntity.ok(loans);
      } catch (Exception e) {
        logger.error("Error retrieving loans", e);
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao buscar os empréstimos");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  @PutMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Loan loan = loanService.findLoanById(id);
        if (loan == null) {
          ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "Empréstimo não encontrado");
          return ResponseEntity.status(404).body(errorResponse);
        }
        loan.setUser(loanDetails.getUser());
        loan.setLoanDate(loanDetails.getLoanDate());
        loan.setReturnDate(loanDetails.getReturnDate());
        loan.setBooks(loanDetails.getBooks());
        loanService.updateLoan(loan);
        return ResponseEntity.ok(loan);
      } catch (Exception e) {
        logger.error("Error updating loan", e);
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao atualizar o empréstimo");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  @DeleteMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> deleteLoan(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        loanService.deleteLoan(id);
        return ResponseEntity.ok("Empréstimo deletado com sucesso");
      } catch (Exception e) {
        logger.error("Error deleting loan", e);
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao deletar o empréstimo");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }
}