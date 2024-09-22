package fi.korpi.bookapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);
    List<Book> findByYear(int year);
    List<Book> findByPublisher(String publisher);
    boolean existsByTitleAndAuthorAndYear(String title, String author, Integer year);
}