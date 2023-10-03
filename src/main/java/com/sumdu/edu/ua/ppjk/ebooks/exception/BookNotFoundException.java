package com.sumdu.edu.ua.ppjk.ebooks.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String s) {
        super(s);
    }
}
