package com.example.biblioteca.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "loans")
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String user;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date loanDate;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date returnDate;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "loan_item", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
  private Set<Book> books = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Date getLoanDate() {
    return loanDate;
  }

  public void setLoanDate(Date loanDate) {
    this.loanDate = loanDate;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public Set<Book> getBooks() {
    return books;
  }

  public void setBooks(Set<Book> books) {
    this.books = books;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Loan{");
    sb.append("id=").append(id);
    sb.append(", user='").append(user).append('\'');
    sb.append(", loanDate=").append(loanDate);
    sb.append(", returnDate=").append(returnDate);
    sb.append(", books=").append(books);
    sb.append('}');
    return sb.toString();
  }
}