package application.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import application.entity.Book;
import application.repository.BookRepository;

@Service
public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void save(Book book) {
		if (book == null)
			return;
		bookRepository.save(book);
	}

	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	public List<Book> findAvailableBooks() {
		return bookRepository.findAll().stream().filter(book -> !book.isRented()).collect(Collectors.toList());
	}

	@PostConstruct
	public void addBooks() {
		IntStream.range(0, 20).forEachOrdered(number -> {
			String title = String.format("Book %d", number);
			String author = String.format("Author %d", number);
			Book book = new Book(title, author);
			bookRepository.save(book);
		});
	}
}