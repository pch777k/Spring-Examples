package com.pch777;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RequestMapping("/books")
@AllArgsConstructor
@RestController
public class BookController {

	private BookRepository bookRepository;

	@GetMapping
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Book with id " + bookId));
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book _book = bookRepository.save(book);
		return new ResponseEntity<>(_book, HttpStatus.CREATED);
	}

	@PutMapping("/{bookId}")
	public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) throws ResourceNotFoundException {
		Book _book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Book with id " + bookId));
		_book.setTitle(book.getTitle());

		return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long bookId) {
		bookRepository.deleteById(bookId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllBooks() {
		bookRepository.deleteAll();

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
