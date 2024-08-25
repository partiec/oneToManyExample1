package ru.frolov.manytooneexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.frolov.manytooneexample.entity.Library;
import ru.frolov.manytooneexample.repository.BookRepository;
import ru.frolov.manytooneexample.repository.LibraryRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/libraries")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Library> create(@RequestBody Library library) {
        Library savedLibrary = this.libraryRepository.save(library);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedLibrary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> update(@PathVariable Long id, @RequestBody Library library) {
        Optional<Library> optionalLibrary = this.libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        library.setId(optionalLibrary.get().getId());
        libraryRepository.save(library);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Library> delete(@PathVariable Long id) {
        Optional<Library> optionalLibrary = this.libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        this.libraryRepository.delete(optionalLibrary.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getById(@PathVariable Long id) {
        Optional<Library> optionalLibrary = this.libraryRepository.findById(id);

        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalLibrary.get());
    }

    @GetMapping
    public ResponseEntity<Page<Library>> getAll(Pageable pageable) {
        return ResponseEntity.ok(this.libraryRepository.findAll(pageable));
    }
}
