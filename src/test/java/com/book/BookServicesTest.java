package com.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.book.dao.BookRepository;
import com.book.dto.BookDto;
import com.book.exception.BookAlreadyExistsException;
import com.book.exception.BookNotFoundException;
import com.book.model.Book;
import com.book.service.BookService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class BookServicesTest {
	@InjectMocks
	BookService bookservices;

	@MockBean
	BookRepository bookRepository;

	@MockBean
	ModelMapper mapper;

	private BookDto bookDto;
	private Book book;

	@BeforeEach
	void setupBookDtoAndBook() {
		bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setAuthor("shubham");
		bookDto.setName("java");
		bookDto.setPublisher("shubham yadav");

		book = new Book();
		book.setId(1);
		book.setAuthor("shubham");
		book.setName("java");
		book.setPublisher("shubham yadav");
	}

	@Test
	void shouldReturnBookWhenBookAdd() throws BookAlreadyExistsException {

		when(mapper.map(any(BookDto.class), any())).thenReturn(new Book());
		bookservices.add(bookDto);
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void shouldThrowBookAlredyExistExceptionWhenBookAlredyExists() throws BookAlreadyExistsException {
		when(mapper.map(any(BookDto.class), any())).thenReturn(new Book());
		when(bookRepository.existsById(1)).thenReturn(true);
		Assertions.assertThrows(BookAlreadyExistsException.class, () -> {
			bookservices.add(bookDto);
		});

	}

	@Test
	void shouldReturnListOfBook() {
		List<Book> bookList = new ArrayList<>();

		bookList.add(book);

		Book book1 = new Book();
		book1.setId(2);
		book1.setAuthor("mayank");
		book1.setName("java adavnces");
		book1.setPublisher("mayank pandit");

		when(bookRepository.findAll()).thenReturn(bookList);
		when(mapper.map(any(Book.class), any())).thenReturn(bookDto);
		assertEquals("java", bookservices.getAllRecords().get(0).getName());
	}

	@Test
	void shouldReturnBookWhenBookIsPresent() throws BookNotFoundException {

		Optional<Book> optional = Optional.of(book);

		when(bookRepository.findById(1)).thenReturn(optional);
		when(mapper.map(any(Book.class), any())).thenReturn(bookDto);

		assertEquals("shubham", bookservices.getRecord(1).getAuthor());

	}

	@Test
	void shouldThrowBookNotFoundExceptionWhenBookIsNotPresent() throws BookNotFoundException {

		when(bookRepository.findById(3)).thenReturn(Optional.empty());
		Assertions.assertThrows(BookNotFoundException.class, () -> {
			bookservices.getRecord(3);
		});
	}

	@Test
	void shouldReturnTrueWhenBookIsDeleted() throws BookNotFoundException {

		when(bookRepository.existsById(1)).thenReturn(true);
		assertTrue(bookservices.delete(1));
		verify(bookRepository, times(1)).deleteById(1);
	}

	@Test
	void shouldThrowUserNotFOundExceptionWhenUserIsNotDeleted() throws BookNotFoundException {

		when(bookRepository.findById(1)).thenReturn(Optional.empty());
		Assertions.assertThrows(BookNotFoundException.class, () -> {
			bookservices.delete(1);
		});
	}

	@Test
	void shouldReturnTrueWhenBookRecordUpdated() throws BookNotFoundException {

		when(bookRepository.existsById(1)).thenReturn(true);
		
		when(mapper.map(any(Book.class), any())).thenReturn(bookDto);
		when(mapper.map(any(BookDto.class), any())).thenReturn(book);
		when(bookRepository.save(any(Book.class))).thenReturn(book);
		assertEquals("shubham",bookservices.updateDetails(1, bookDto).getAuthor());
		verify(bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	void shouldThrowUserNotFOundExceptionWhenUserIsNotUpdated() throws BookNotFoundException {

		when(bookRepository.existsById(1)).thenReturn(false);
		Assertions.assertThrows(BookNotFoundException.class, () -> {
			bookservices.updateDetails(1, bookDto);
		});
	}

}
