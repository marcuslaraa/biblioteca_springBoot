package com.example.biblioteca.controller;

import com.example.biblioteca.model.Loan;
import com.example.biblioteca.service.LoanService;
import com.example.biblioteca.ErrorType;
import com.example.biblioteca.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/loans")
public class LoanController {

  @Autowired
  private LoanService loanService;

  @PostMapping
  public CompletableFuture<ResponseEntity<?>> createLoan(@RequestBody Loan loan) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        loanService.createLoan(loan);
        return ResponseEntity.ok(loan);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(400).body(errorResponse);
      }
    });
  }

  @GetMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> getLoanById(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Loan loan = loanService.findLoanById(id);
        return ResponseEntity.ok(loan);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
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
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  @PutMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
    return CompletableFuture.supplyAsync(() -> {
      try {

        loanService.updateLoan(id, loanDetails);

        return ResponseEntity.ok("Empréstimo atualizado com sucesso");
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(400).body(errorResponse);
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
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
      }
    });
  }
}