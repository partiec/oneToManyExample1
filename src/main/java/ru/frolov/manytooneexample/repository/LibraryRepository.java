package ru.frolov.manytooneexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.frolov.manytooneexample.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}
