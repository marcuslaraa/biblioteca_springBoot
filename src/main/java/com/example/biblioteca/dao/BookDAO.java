package com.example.biblioteca.dao;

import com.example.biblioteca.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookDAO extends BaseDAO<Book> {

  public BookDAO() {
    super(Book.class);
  }
}
