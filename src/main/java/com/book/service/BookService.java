package com.book.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.dao.BookRepository;
import com.book.dto.BookDto;
import com.book.exception.BookAlreadyExistsException;
import com.book.exception.BookNotFoundException;
import com.book.model.Book;

import java.util.*;

@Service

public class BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	ModelMapper mapper;

	String errorMessage = "book not exists";

	public Book add(BookDto bookDto) throws BookAlreadyExistsException {
		Book book = mapper.map(bookDto, Book.class);
		if (!bookRepository.existsById(bookDto.getId())) {
			bookRepository.save(book);
		} else {
			throw new BookAlreadyExistsException("book alredy present");
		}
		return book;
	}

	public List<BookDto> getAllRecords() {
		List<Book> bookList = (List<Book>) bookRepository.findAll();
		List<BookDto> bookDtoList = new ArrayList<>();
		for (Book book : bookList) {
			BookDto bookDto = mapper.map(book, BookDto.class);
			bookDtoList.add(bookDto);
		}
		return bookDtoList;
	}

	public BookDto getRecord(int bookId) throws BookNotFoundException {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(errorMessage));
		return mapper.map(book, BookDto.class);
	}

	public boolean delete(int bookid) throws BookNotFoundException {
		if (bookRepository.existsById(bookid)) {
			bookRepository.deleteById(bookid);
			return true;
		}
		throw new BookNotFoundException(errorMessage);

	}

	public BookDto updateDetails(int bookid, BookDto bookDto) throws BookNotFoundException {
		if (bookRepository.existsById(bookid)) {
			bookDto.setId(bookid);
			Book book = mapper.map(bookDto, Book.class);
			return mapper.map(bookRepository.save(book), BookDto.class);
		}
		throw new BookNotFoundException(errorMessage);
	}
}
