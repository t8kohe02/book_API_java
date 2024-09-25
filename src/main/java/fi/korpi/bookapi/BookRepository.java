package fi.korpi.bookapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    List<Book> findByYear(int year);

    List<Book> findByPublisher(String publisher);

    List<Book> findByAuthorAndYear(String author, Integer year);

    List<Book> findByAuthorAndPublisher(String author, String publisher);

    List<Book> findByYearAndPublisher(Integer year, String publisher);

    List<Book> findByAuthorAndYearAndPublisher(String author, Integer year, String publisher);

    boolean existsByTitleAndAuthorAndYear(String title, String author, Integer year);
}