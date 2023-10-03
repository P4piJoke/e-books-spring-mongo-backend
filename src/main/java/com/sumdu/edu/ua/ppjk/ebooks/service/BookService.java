package com.sumdu.edu.ua.ppjk.ebooks.service;

import com.sumdu.edu.ua.ppjk.ebooks.dto.request.BookRequestDTO;
import com.sumdu.edu.ua.ppjk.ebooks.dto.response.BookResponseDTO;
import com.sumdu.edu.ua.ppjk.ebooks.model.Book;

import java.util.List;

public interface BookService {

    Book save(BookRequestDTO book);

    List<BookResponseDTO> getAll();

    Book update(String bookTitle, BookRequestDTO bookToUpdate);

    void delete(String bookTitle);

}
