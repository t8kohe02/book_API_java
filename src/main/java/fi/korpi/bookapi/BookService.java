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
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> filterBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> filterBooksByPublicationYear(int year) {
        return bookRepository.findByPublicationYear(year);
    }

    public List<Book> filterBooksByPublisher(String publisher) {
        return bookRepository.findByPublisher(publisher);
    }
}