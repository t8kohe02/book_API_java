package fi.korpi.bookapi;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "title", "author", "year" })
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title can't be empty")
    private String title;

    @NotEmpty(message = "Author can't be empty")
    private String author;

    @NotNull(message = "Year can't be empty")
    private Integer year;

    @Size(max = 255, message = "Publisher can be up to 255 characters long")
    private String publisher;

    @Size(max = 255, message = "Description can be up to 255 characters long")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}