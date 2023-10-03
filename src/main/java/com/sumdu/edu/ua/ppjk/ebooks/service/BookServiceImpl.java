package com.sumdu.edu.ua.ppjk.ebooks.service;

import com.sumdu.edu.ua.ppjk.ebooks.dto.request.BookRequestDTO;
import com.sumdu.edu.ua.ppjk.ebooks.dto.response.BookResponseDTO;
import com.sumdu.edu.ua.ppjk.ebooks.entity.BookEntity;
import com.sumdu.edu.ua.ppjk.ebooks.exception.BookNotFoundException;
import com.sumdu.edu.ua.ppjk.ebooks.model.Book;
import com.sumdu.edu.ua.ppjk.ebooks.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final ModelMapper mapper;

    @Override
    public Book save(BookRequestDTO book) {
        BookEntity savedBook = repository.insert(mapper.map(book, BookEntity.class));
        return Book.builder()
                .title(savedBook.getTitle())
                .author(savedBook.getAuthor())
                .year(savedBook.getYear())
                .build();
    }

    @Override
    public List<BookResponseDTO> getAll() {
        List<BookEntity> books = repository.findAll();
        return books.stream().map(this::mapToBookResponse)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public Book update(String bookTitle, BookRequestDTO bookToUpdate) {
        Optional<BookEntity> byTitle = repository.findByTitle(bookTitle);
        if (byTitle.isEmpty()) {
            throw new BookNotFoundException("Book with title -> " + bookTitle + " NOT FOUND");
        }
        BookEntity bookEntity = byTitle.get();
        updateBook(bookEntity, bookToUpdate);

        return Book.builder()
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .year(bookEntity.getYear())
                .build();
    }

    @Override
    public void delete(String bookTitle) {
        repository.deleteByTitle(bookTitle);
    }

    private void updateBook(BookEntity bookEntity, BookRequestDTO bookToUpdate) {
        bookEntity.setTitle(bookToUpdate.title());
        bookEntity.setAuthor(bookToUpdate.author());
        bookEntity.setYear(bookToUpdate.year());

        repository.save(bookEntity);
    }

    private BookResponseDTO mapToBookResponse(BookEntity bookEntity) {
        return mapper.map(bookEntity, BookResponseDTO.class);
    }
}
