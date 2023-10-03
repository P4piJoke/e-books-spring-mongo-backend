package com.sumdu.edu.ua.ppjk.ebooks.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private String title;
    private String author;
    private Long year;
}
