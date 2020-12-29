package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}