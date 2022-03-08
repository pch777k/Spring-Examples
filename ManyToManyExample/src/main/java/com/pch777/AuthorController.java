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
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class AuthorController {

	private AuthorRepository authorRepository;
	private BookRepository bookRepository;

	@GetMapping("/authors")
	public List<Author> getAllAuthors() {
		return authorRepository.findAll();
	}

	@GetMapping("/authors/{authorId}")
	public ResponseEntity<Author> getAuthorById(@PathVariable Long authorId) throws ResourceNotFoundException {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + authorId));
		return new ResponseEntity<>(author, HttpStatus.OK);
	}

	@PostMapping("/authors")
	public ResponseEntity<Author> createAuthor(@RequestBody Author authorRequest) {
		Author author = authorRepository.save(authorRequest);
		return new ResponseEntity<>(author, HttpStatus.CREATED);
	}

	@PostMapping("/books/{bookId}/authors")
	public ResponseEntity<Author> addAuthor(@PathVariable Long bookId,
			@RequestBody Author authorRequest) throws ResourceNotFoundException {

		Author author = bookRepository.findById(bookId).map(book -> {
			Long authorId = authorRequest.getId();

			// author is existed
			if (authorRequest.getId() != null) {
				Author _author = new Author();
				try {
					_author = authorRepository.findById(authorId)
							.orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + authorId));

				} catch (ResourceNotFoundException e) {
					e.printStackTrace();
				}
				book.addAuthor(_author);
				bookRepository.save(book);
				return _author;
			}

			// add and create new Author
			book.addAuthor(authorRequest);
			return authorRepository.save(authorRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));
		return new ResponseEntity<>(author, HttpStatus.CREATED);
	}

	@PutMapping("/authors/{authorId}")
	public ResponseEntity<Author> updateAuthor(@PathVariable long authorId,
			@RequestBody Author authorRequest) throws ResourceNotFoundException {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("AuthorId " + authorId + "not found"));
		author.setName(authorRequest.getName());
		return new ResponseEntity<>(authorRepository.save(author), HttpStatus.OK);
	}

	@DeleteMapping("/authors/{authorId}")
	public ResponseEntity<HttpStatus> deleteAuthorById(@PathVariable Long authorId) throws ResourceNotFoundException {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + authorId));
		author.removeBooks();
		authorRepository.deleteById(authorId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/authors")
	public ResponseEntity<HttpStatus> deleteAllAuthors() {
		authorRepository.findAll()
				.forEach(author -> author.removeBooks());
		authorRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
