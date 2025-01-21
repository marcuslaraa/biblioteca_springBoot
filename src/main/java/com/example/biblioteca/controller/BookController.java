package com.example.biblioteca.controller;

import com.example.biblioteca.model.Book;
import com.example.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController extends BaseController<Book> {

  @Autowired
  private BookService bookService;

  @Override
  protected void createEntity(Book book) {
    bookService.createBook(book);
  }

  @Override
  protected Book findEntityById(Long id) {
    return bookService.findBookById(id);
  }

  @Override
  protected List<Book> findAllEntities() {
    return bookService.findAllBooks();
  }

  @Override
  protected void updateEntity(Long id, Book bookDetails) {
    bookService.updateBook(id, bookDetails);
  }

  @Override
  protected void deleteEntity(Long id) {
    bookService.deleteBook(id);
  }
}