package com.book;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.book.controller.BookController;
import com.book.dto.BookDto;
import com.book.exception.BookNotFoundException;
import com.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = { BookController.class })
@ExtendWith(MockitoExtension.class)
class BookControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	BookService bookServices;

	private BookDto bookDto;

	@BeforeEach
	void setupAccountDto() {
		bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setAuthor("shubham");
		bookDto.setName("java");
		bookDto.setPublisher("shubham yadav");

	}

	@Test
	void shouldReturnCreatedStatusWhenBookInsterted() throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String masterAccountDtoInJsonForm = mapper.writeValueAsString(bookDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/book")
				.contentType(MediaType.APPLICATION_JSON).content(masterAccountDtoInJsonForm);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}

	@Test
	void shouldReturnOkStatusWhenBookDtoIsPresent() throws Exception {

		when(bookServices.getRecord(1)).thenReturn(new BookDto());
		mockMvc.perform(get("/book/1")).andExpect(status().isOk());

	}
	@Test
	void shouldReturnOkStatusWhenBooksDtoListIsPresent() throws Exception {

		when(bookServices.getAllRecords()).thenReturn(new ArrayList<BookDto>());
		mockMvc.perform(get("/book")).andExpect(status().isOk());

	}

	@Test
	void shouldReturnOKStatusWhenUserDeleted() throws Exception {
		when(bookServices.delete(1)).thenReturn(true);
		mockMvc.perform(delete("/book/1")).andExpect(status().isNoContent());
	}
	

	@Test
	void shouldReturnOKStatusWhenUserUpdated() throws Exception {

		when(bookServices.updateDetails(1, bookDto)).thenReturn(bookDto);

		ObjectMapper mapper = new ObjectMapper();
		String userDtoInJsonForm = mapper.writeValueAsString(bookDto);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/book/1")
				.contentType(MediaType.APPLICATION_JSON).content(userDtoInJsonForm);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
				
	}
	
}
