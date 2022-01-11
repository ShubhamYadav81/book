package com.book.dao;

import org.springframework.data.repository.CrudRepository;

import com.book.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

}
