package ru.frolov.manytooneexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frolov.manytooneexample.entity.Book;
import ru.frolov.manytooneexample.entity.Library;
import ru.frolov.manytooneexample.repository.BookRepository;
import ru.frolov.manytooneexample.repository.LibraryRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Optional<Library> optionalLibrary = this.libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(optionalLibrary.get());

        Book savedBook = this.bookRepository.save(book);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        Optional<Library> optionalLibrary = this.libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Book> optionalBook = this.bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(optionalLibrary.get());
        book.setId(optionalBook.get().getId());
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book>delete(@PathVariable Long id){
        Optional<Book>optionalBook  = this.bookRepository.findById(id);
        if (!optionalBook.isPresent()){
            return ResponseEntity.unprocessableEntity().build();
        }

        this.bookRepository.delete(optionalBook.get());

        return ResponseEntity.noContent().build();
    }


}
