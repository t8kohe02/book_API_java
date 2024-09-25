package fi.korpi.bookapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveBookSuccess() {
        Book book = new Book();
        book.setTitle("New Book");
        book.setAuthor("New Author");
        book.setYear(2022);
        book.setPublisher("New Publisher");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertNotNull(savedBook);
        assertEquals("New Book", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testFindBookByIdSuccess() {
        Book book = new Book();
        book.setTitle("Existing Book");
        book.setAuthor("Existing Author");
        book.setYear(2021);
        book.setPublisher("Existing Publisher");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertTrue(foundBook.isPresent());
        assertEquals("Existing Book", foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindBookByIdNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertFalse(foundBook.isPresent());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteBookSuccess() {
        Book book = new Book();
        book.setTitle("Book to Delete");
        book.setAuthor("Author");
        book.setYear(2022);
        book.setPublisher("Publisher");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.deleteBook(1L);
        });

        verify(bookRepository, times(0)).deleteById(anyLong());
        verify(bookRepository, times(1)).findById(1L);
    }

}
