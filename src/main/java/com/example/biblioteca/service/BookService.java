package com.example.biblioteca.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BookDAO;
import com.example.biblioteca.model.Book;

import jakarta.transaction.Transactional;

@Service
public class BookService {

  @Autowired
  private BookDAO bookDAO;

  @Transactional
  public void createBook(Book book) {
    if (book.getTitle() == null || book.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
    }

    if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
      throw new IllegalArgumentException("Autor não pode ser nulo ou vazio");
    }

    if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
      throw new IllegalArgumentException("ISBN não pode ser nulo ou vazio");
    }
    bookDAO.create(book);
  }

  @Transactional
  public void updateBook(Long id, Book updatedBook) {
    Book existingBook = bookDAO.findById(id);
    if (existingBook == null) {
      throw new IllegalArgumentException("Livro não encontrado");
    }

    if (updatedBook.getTitle() == null || updatedBook.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
    }

    if (updatedBook.getAuthor() == null || updatedBook.getAuthor().isEmpty()) {
      throw new IllegalArgumentException("Autor não pode ser nulo ou vazio");
    }

    if (updatedBook.getIsbn() == null || updatedBook.getIsbn().isEmpty()) {
      throw new IllegalArgumentException("ISBN não pode ser nulo ou vazio");
    }

    existingBook.setTitle(updatedBook.getTitle());
    existingBook.setAuthor(updatedBook.getAuthor());
    existingBook.setIsbn(updatedBook.getIsbn());
    bookDAO.update(existingBook);
  }

  @Transactional
  public void deleteBook(Long id) {
    Book existingBook = bookDAO.findById(id);
    if (existingBook == null) {
      throw new IllegalArgumentException("Livro não encontrado");
    }
    bookDAO.delete(id);
  }

  @Transactional
  public Book findBookById(Long id) {
    Book exisBook = bookDAO.findById(id);
    if (exisBook == null) {
      throw new IllegalArgumentException("Livro não encontrado");
    }
    return bookDAO.findById(id);
  }

  @Transactional
  public List<Book> findAllBooks() {
    List<Book> books = bookDAO.findAll();
    if (books.isEmpty()) {
      throw new IllegalArgumentException("Não existem livros cadastrados");
    }
    return books;
  }

}
