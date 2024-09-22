package fi.korpi.bookapi;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<?> getAllBooks(@RequestParam(required = false) String author, @RequestParam(required = false) Integer year, @RequestParam(required = false) String publisher) {
        if (author != null && author.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author must not be an empty string");
        }
        if (publisher != null && publisher.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Publisher must not be an empty string");
        }

        List<Book> books;
        if (author != null) {
            books = bookService.filterBooksByAuthor(author);
        } else if (year != null) {
            books = bookService.filterBooksByYear(year);
        } else if (publisher != null){
            books = bookService.filterBooksByPublisher(publisher);
        } else {
            books = bookService.getAllBooks();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable String id) {
        try {
            Long bookId = Long.parseLong(id);
            Optional<Book> book = bookService.getBookById(bookId);

            if (book.isPresent()) {
                return ResponseEntity.ok(book.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID");
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            Book newBook = bookService.saveBook(book);
            Map<String, Object> response = new HashMap<>();
            response.put("id", newBook.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable String id) {
        try {
            Long bookId = Long.parseLong(id);

            Optional<Book> book = bookService.getBookById(bookId);
            if (book.isPresent()){
                bookService.deleteBook(bookId);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID");
        }
    }

    @GetMapping("/filter")
    public List<Book> filterBooks(@RequestParam(required = false) String author,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) String publisher) {
        if (author != null) {
            return bookService.filterBooksByAuthor(author);
        } else if (year != null) {
            return bookService.filterBooksByYear(year);
        } else if (publisher != null) {
            return bookService.filterBooksByPublisher(publisher);
        }
        return bookService.getAllBooks();
    }
}