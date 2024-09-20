package fi.korpi.bookapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/filter")
    public List<Book> filterBooks(@RequestParam(required = false) String author,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) String publisher) {
        if (author != null) {
            return bookService.filterBooksByAuthor(author);
        } else if (year != null) {
            return bookService.filterBooksByPublicationYear(year);
        } else if (publisher != null) {
            return bookService.filterBooksByPublisher(publisher);
        }
        return bookService.getAllBooks();
    }
}