package com.sumdu.edu.ua.ppjk.ebooks.controller;

import com.sumdu.edu.ua.ppjk.ebooks.dto.request.BookRequestDTO;
import com.sumdu.edu.ua.ppjk.ebooks.dto.response.BookControllerErrorResponse;
import com.sumdu.edu.ua.ppjk.ebooks.dto.response.BookResponseDTO;
import com.sumdu.edu.ua.ppjk.ebooks.exception.BookNotFoundException;
import com.sumdu.edu.ua.ppjk.ebooks.exception.IllegalBookRequestException;
import com.sumdu.edu.ua.ppjk.ebooks.model.Book;
import com.sumdu.edu.ua.ppjk.ebooks.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@RestController
@RequestMapping("/api/v1/e-books")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookService service;

    @PostMapping("/add")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalBookRequestException(exposeMessageFromBindingResult(bindingResult));
        }

        var newBook = service.save(bookRequestDTO);
        return ResponseEntity.ok(newBook);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BookResponseDTO>> getBooksList() {
        var books = service.getAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{title}")
    public ResponseEntity<Book> getBook(@PathVariable String title) {
        var book = service.getByTitle(title);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{book}/update")
    public ResponseEntity<Book> updateBook(@PathVariable String book,
                                           @Valid @RequestBody BookRequestDTO bookRequestDTO,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalBookRequestException(exposeMessageFromBindingResult(bindingResult));
        }

        var bookToUpdate = service.update(book, bookRequestDTO);
        return ResponseEntity.ok(bookToUpdate);
    }

    @DeleteMapping("/{book}/delete")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable String book) {
        service.delete(book);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ExceptionHandler({IllegalBookRequestException.class, BookNotFoundException.class})
    public ResponseEntity<BookControllerErrorResponse> handleBookRequestValidationException(Exception exception) {
        BookControllerErrorResponse response = new BookControllerErrorResponse(
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String exposeMessageFromBindingResult(BindingResult bindingResult) {
        StringJoiner errorMessage = new StringJoiner(";");

        List<FieldError> errors = bindingResult.getFieldErrors();
        errors.forEach(error ->
                errorMessage.add(error.getField() + " - " + error.getDefaultMessage())
        );

        return errorMessage.toString();
    }
}
