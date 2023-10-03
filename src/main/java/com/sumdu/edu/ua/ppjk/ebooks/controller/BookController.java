package com.sumdu.edu.ua.ppjk.ebooks.controller;

import com.sumdu.edu.ua.ppjk.ebooks.dto.request.BookRequestDTO;
import com.sumdu.edu.ua.ppjk.ebooks.dto.response.BookResponseDTO;
import com.sumdu.edu.ua.ppjk.ebooks.model.Book;
import com.sumdu.edu.ua.ppjk.ebooks.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/e-books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {

        var newBook = service.save(bookRequestDTO);
        return ResponseEntity.ok(newBook);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponseDTO>> getBooksList() {
        var books = service.getAll();
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{book}/update")
    public ResponseEntity<Book> updateBook(@PathVariable String book,
                                           @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        var bookToUpdate = service.update(book, bookRequestDTO);
        return ResponseEntity.ok(bookToUpdate);
    }

    @DeleteMapping("/{book}/delete")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable String book) {
        service.delete(book);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
