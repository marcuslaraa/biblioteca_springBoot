package com.example.biblioteca.controller;

import com.example.biblioteca.dao.BookDAO;
import com.example.biblioteca.model.Book;
import com.example.biblioteca.model.ErrorResponse;
import com.example.biblioteca.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookDAO bookDAO;
  private final BookService bookService = new BookService(bookDAO);

  // [Post] create
  @PostMapping
  public CompletableFuture<ResponseEntity<?>> createBook(@RequestBody Book book) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        bookDAO.create(book);
        // return this.bookService.createBook(book).thenApply(createdBook -> {
        // return ResponseEntity.ok(createdBook);
        // }).join();
        return ResponseEntity.ok(book);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao criar o livro");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  // [Get] findAll
  @GetMapping
  public CompletableFuture<ResponseEntity<?>> getAllBooks() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        List<Book> books = bookDAO.findAll();
        if (books.isEmpty()) {
          ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "Não existem livros cadastrados");
          return ResponseEntity.status(404).body(errorResponse);
        }
        return ResponseEntity.ok(books);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao buscar os livros");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  // [Get] findById
  @GetMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> getBookById(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Book book = bookDAO.findById(id);
        if (book != null) {
          return ResponseEntity.ok(book);
        } else {
          ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "O livro não existe");
          return ResponseEntity.status(404).body(errorResponse);
        }
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao buscar o livro");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  // [Put] updateById
  @PutMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Book book = bookDAO.findById(id);
        if (book == null) {
          ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "O livro não existe");
          return ResponseEntity.status(404).body(errorResponse);
        }
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        bookDAO.update(book);
        return ResponseEntity.ok(book);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao atualizar o livro");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }

  // [Delete] deleteById
  @DeleteMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> deleteBook(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        Book book = bookDAO.findById(id);
        if (book == null) {
          ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "O livro não existe");
          return ResponseEntity.status(404).body(errorResponse);
        }
        bookDAO.delete(id);
        return ResponseEntity.ok("Livro deletado com sucesso");
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Erro ao deletar o livro");
        return ResponseEntity.status(500).body(errorResponse);
      }
    });
  }
}