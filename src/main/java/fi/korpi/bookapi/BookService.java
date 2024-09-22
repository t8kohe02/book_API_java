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
        bookRepository.deleteById(id);
    }

    public List<Book> filterBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> filterBooksByYear(Integer year) {
        return bookRepository.findByYear(year);
    }

    public List<Book> filterBooksByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher);
    }
}