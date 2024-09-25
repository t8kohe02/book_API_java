package fi.korpi.bookapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        if (bookRepository.existsByTitleAndAuthorAndYear(book.getTitle(), book.getAuthor(), book.getYear())) {
            throw new IllegalArgumentException("Duplicate entry.");
        }
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    public List<Book> filterBooks(String author, Integer year, String publisher) {
        if (author != null && year != null && publisher != null) {
            return bookRepository.findByAuthorAndYearAndPublisher(author, year, publisher);
        } else if (author != null && year != null) {
            return bookRepository.findByAuthorAndYear(author, year);
        } else if (author != null && publisher != null) {
            return bookRepository.findByAuthorAndPublisher(author, publisher);
        } else if (year != null && publisher != null) {
            return bookRepository.findByYearAndPublisher(year, publisher);
        } else if (author != null) {
            return bookRepository.findByAuthor(author);
        } else if (year != null) {
            return bookRepository.findByYear(year);
        } else if (publisher != null) {
            return bookRepository.findByPublisher(publisher);
        } else {
            return getAllBooks();
        }
    }
}