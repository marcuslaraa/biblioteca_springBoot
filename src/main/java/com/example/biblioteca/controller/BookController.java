package com.example.biblioteca.controller;

import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.ErrorResponse;
import com.example.biblioteca.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.example.biblioteca.ErrorType;

@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookService bookService;

  // [Post] create
  @PostMapping
  public CompletableFuture<ResponseEntity<?>> createBook(@RequestBody Book book) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        bookService.createBook(book);
        return ResponseEntity.ok(book);
      } catch (Exception e) {
        System.out.println(e);
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  // [Get] findAll
  @GetMapping
  public CompletableFuture<ResponseEntity<?>> getAllBooks() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        List<Book> books = bookService.findAllBooks();
        return ResponseEntity.ok(books);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  // [Get] findById
  @GetMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> getBookById(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Book foundedBook = bookService.findBookById(id);
        return ResponseEntity.ok(foundedBook);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
      }
    });
  }

  // [Put] updateById
  @PutMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
    return CompletableFuture.supplyAsync(() -> {
      try {

        bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok("Livro atualizado com sucesso");
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(400).body(errorResponse);
      }
    });
  }

  // [Delete] deleteById
  @DeleteMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> deleteBook(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Livro deletado com sucesso");
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
      }
    });
  }
}