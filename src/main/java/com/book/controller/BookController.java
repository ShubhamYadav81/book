package com.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.book.dto.BookDto;
import com.book.exception.BookAlreadyExistsException;
import com.book.exception.BookNotFoundException;
import com.book.service.BookService;

import java.util.*;

@RestController
@RequestMapping("/book")

public class BookController {

	@Autowired
	BookService bookServices;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void addBook(@RequestBody BookDto bookDto) throws BookAlreadyExistsException {
		bookServices.add(bookDto);
	}

	@GetMapping
	public ResponseEntity<List<BookDto>> getAllRecord() {
		return new ResponseEntity<>(bookServices.getAllRecords(), HttpStatus.OK);
	}

	@GetMapping("{bookId}")
	public ResponseEntity<BookDto> getBook(@PathVariable int bookId) throws BookNotFoundException {

		return new ResponseEntity<>(bookServices.getRecord(bookId), HttpStatus.OK);
	}

	@DeleteMapping("{bookId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable int bookId) throws BookNotFoundException {
		bookServices.delete(bookId);
	}

	@PutMapping("{bookId}")
	public ResponseEntity<BookDto> updateBookDetail(@PathVariable int bookId, @RequestBody BookDto bookDto)
			throws BookNotFoundException {
		return new ResponseEntity<>(bookServices.updateDetails(bookId, bookDto), HttpStatus.OK);
	}

}
