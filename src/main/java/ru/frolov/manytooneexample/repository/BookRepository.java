package ru.frolov.manytooneexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frolov.manytooneexample.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
