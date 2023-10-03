package com.sumdu.edu.ua.ppjk.ebooks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

@Validated
public record BookRequestDTO(

        @NotBlank(message = "Title can not be empty")
        String title,
        @NotBlank(message = "Author can not be empty")
        String author,

        @NotNull
        @NumberFormat
        Long year
) {
}
