package com.inqoo.fsd.solutions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
class LikeToReadService {

//    private static final Logger log = LoggerFactory.getLogger(LikeToReadService.class);

    private final LikeToReadRepository repository;

    LikeToReadService(LikeToReadRepository repository) {
        this.repository = repository;
    }

    void addAuthor(Author author) {
        repository.addAuthor(author);
    }

    void addBook(String firstName, String lastName, Book book) {
        Optional<Author> author = repository.findAuthor(firstName, lastName);
        if (author.isPresent()) {
            repository.addBook(author.get(), book);
        } else {
            System.out.printf(String.format("Could not add book to existing author %s %s", firstName, lastName));
        }
    }

    void addReview(String title, Review review) {
        Optional<Book> book = repository.findBook(title);
        if (book.isPresent()) {
            System.out.printf(String.format("adding review for %s: %s", title, review));
            repository.addReview(title, review);
        } else {
            System.out.printf(String.format("Could not add review to nor existing book %s" , title));
        }
    }

    List<Review> getReviews(String title) {
        Optional<Book> book = repository.findBook(title);
        if (book.isPresent()) {
            return book.get().getReviews();
        } else {
            return Collections.emptyList();
        }
    }

    List<Author> getAuthors() {
        return repository.getAuthors();
    }

    List<Book> getBooks() {
        return repository.getBooks();
    }
}
