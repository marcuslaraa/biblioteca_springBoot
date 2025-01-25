package com.example.biblioteca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.biblioteca.ErrorType;
import com.example.biblioteca.model.ErrorResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class BaseController<T> {

  protected abstract void createEntity(T entity);

  protected abstract T findEntityById(Long id);

  protected abstract List<T> findAllEntities();

  protected abstract void updateEntity(Long id, T entityDetails);

  protected abstract void deleteEntity(Long id);

  @PostMapping
  public CompletableFuture<ResponseEntity<?>> create(@RequestBody T entity) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        System.out.println(entity);
        createEntity(entity);
        System.out.println(entity);
        return ResponseEntity.ok(entity);
      } catch (IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(ErrorType.BAD_REQUEST.getStatusCode()).body(errorResponse);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.getStatusCode()).body(errorResponse);
      }
    });
  }

  @GetMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> getById(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        T entity = findEntityById(id);
        return ResponseEntity.ok(entity);
      } catch (IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(ErrorType.NOT_FOUND.getStatusCode()).body(errorResponse);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.getStatusCode()).body(errorResponse);
      }
    });
  }

  @GetMapping
  public CompletableFuture<ResponseEntity<?>> getAll() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        List<T> entities = findAllEntities();
        return ResponseEntity.ok(entities);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.getStatusCode()).body(errorResponse);
      }
    });
  }

  @PutMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> update(@PathVariable Long id, @RequestBody T entityDetails) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        updateEntity(id, entityDetails);
        return ResponseEntity.ok(entityDetails);
      } catch (IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(ErrorType.BAD_REQUEST.getStatusCode()).body(errorResponse);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.getStatusCode()).body(errorResponse);
      }
    });
  }

  @DeleteMapping("/{id}")
  public CompletableFuture<ResponseEntity<?>> delete(@PathVariable Long id) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        deleteEntity(id);
        return ResponseEntity.ok().build();
      } catch (IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(ErrorType.NOT_FOUND.getStatusCode()).body(errorResponse);
      } catch (Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorType.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(ErrorType.INTERNAL_SERVER_ERROR.getStatusCode()).body(errorResponse);
      }
    });
  }
}