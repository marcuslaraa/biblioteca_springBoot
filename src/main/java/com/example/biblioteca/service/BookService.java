package com.example.biblioteca.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BookDAO;
import com.example.biblioteca.model.Book;

import jakarta.transaction.Transactional;

@Service
public class BookService {

  private final BookDAO bookDAO;

  public BookService(BookDAO bookDAO) {
    this.bookDAO = bookDAO;
  }

  @Transactional
  public CompletableFuture<ResponseEntity<?>> createBook(Book book) {
    return CompletableFuture.supplyAsync(() -> {
      System.out.println(book);
      try {
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
          return ResponseEntity.badRequest().body("ISBN é obrigatório");
        }
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
          return ResponseEntity.badRequest().body("Título é obrigatório");
        }
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
          return ResponseEntity.badRequest().body("Autor é obrigatório");
        }

        System.out.println("ANTES DO DAO");
        this.bookDAO.create(book);
        System.out.println("DEPOIS DO DAO");

        return ResponseEntity.ok(book);
      } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body("ISBN já existe: " + book.getIsbn());
      } catch (Exception e) {
        return ResponseEntity.status(500).body("Erro ao criar o livro");
      }
    });
  }
}
