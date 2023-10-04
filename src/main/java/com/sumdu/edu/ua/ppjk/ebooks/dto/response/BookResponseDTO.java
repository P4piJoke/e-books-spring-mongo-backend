package com.sumdu.edu.ua.ppjk.ebooks.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {

    private String title;
    private String author;
    private Long year;
}
