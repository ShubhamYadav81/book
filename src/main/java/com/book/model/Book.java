package com.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter

public class Book {
	
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PUBLISHER")
	private String publisher;
	
	@Column(name = "AUTHOR")
	private String author;

}
