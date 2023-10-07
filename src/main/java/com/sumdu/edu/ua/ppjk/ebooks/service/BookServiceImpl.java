package com.sumdu.edu.ua.ppjk.ebooks.service;

import com.sumdu.edu.ua.ppjk.ebooks.dto.request.BookRequestDTO;
import com.sumdu.edu.ua.ppjk.ebooks.dto.response.BookResponseDTO;
import com.sumdu.edu.ua.ppjk.ebooks.entity.BookEntity;
import com.sumdu.edu.ua.ppjk.ebooks.exception.BookNotFoundException;
import com.sumdu.edu.ua.ppjk.ebooks.model.Book;
import com.sumdu.edu.ua.ppjk.ebooks.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public Book save(BookRequestDTO book) {
        BookEntity savedBook = repository.insert(mapToBookEntity(book));
        return mapToBook(savedBook);
    }

    @Override
    public List<BookResponseDTO> getAll() {
        List<BookEntity> books = repository.findAll();
        return books.stream().map(this::mapToBookResponse)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public Book getByTitle(String title) {
        BookEntity bookEntity = repository.findByTitle(title)
                .orElseThrow(() -> new BookNotFoundException("Book with title -> " + title + " NOT FOUND"));
        return mapToBook(bookEntity);
    }

    @Override
    public Book update(String bookTitle, BookRequestDTO bookToUpdate) {
        BookEntity bookEntity = repository.findByTitle(bookTitle)
                .orElseThrow(() -> new BookNotFoundException("Book with title -> " + bookTitle + " NOT FOUND"));

        updateBook(bookEntity, bookToUpdate);

        return mapToBook(bookEntity);
    }

    @Override
    public void delete(String bookTitle) {
        repository.findByTitle(bookTitle)
                .orElseThrow(() -> new BookNotFoundException("Book with title -> " + bookTitle + " NOT FOUND"));

        repository.deleteByTitle(bookTitle);
    }

    private void updateBook(BookEntity bookEntity, BookRequestDTO bookToUpdate) {
        bookEntity.setTitle(bookToUpdate.title());
        bookEntity.setAuthor(bookToUpdate.author());
        bookEntity.setYear(bookToUpdate.year());

        repository.save(bookEntity);
    }

    private static Book mapToBook(BookEntity savedBook) {
        return Book.builder()
                .title(savedBook.getTitle())
                .author(savedBook.getAuthor())
                .year(savedBook.getYear())
                .build();
    }

    private BookResponseDTO mapToBookResponse(BookEntity bookEntity) {
        return BookResponseDTO.builder()
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .year(bookEntity.getYear())
                .build();
    }

    private BookEntity mapToBookEntity(BookRequestDTO book) {
        return BookEntity.builder()
                .title(book.title())
                .author(book.author())
                .year(book.year())
                .build();
    }
}
