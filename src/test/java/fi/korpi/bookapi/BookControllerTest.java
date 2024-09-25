package fi.korpi.bookapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.Optional;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = List.of(
                new Book("First Book", "First Author", 2001, "First Publisher", "First description"),
                new Book("Second Book", "Second Author", 2002, "Second Publisher", "Second description"));

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("First Book"))
                .andExpect(jsonPath("$[1].title").value("Second Book"));
    }

    @Test
    public void testGetBookByIdSuccess() throws Exception {
        Book book = new Book("First Book", "First Author", 2001, "First Publisher", "First description");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("First Book"))
                .andExpect(jsonPath("$.author").value("First Author"));
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));
    }

    @Test
    public void testAddBookSuccess() throws Exception {
        Book newBook = new Book();
        newBook.setTitle("Testers Handbook");
        newBook.setAuthor("Testerius Authorius");
        newBook.setYear(2001);
        newBook.setPublisher("Publisher House");

        when(bookService.saveBook(any(Book.class))).thenReturn(newBook);

        mockMvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newBook)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddBookWithoutTitle() throws Exception {
        Book newBook = new Book();
        newBook.setAuthor("Testerius Authorius");
        newBook.setYear(2001);
        newBook.setPublisher("Publisher House");

        mockMvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title can't be empty"));
    }

    @Test
    public void testDeleteBookByIdSuccess() throws Exception {
        Book book = new Book("Test Book", "Author", 2021, "Publisher", "Descriptoin");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBookByIdNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found"));
    }
}