package com.sumdu.edu.ua.ppjk.ebooks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book")
public class BookEntity {

    @Id
    private String id;

    private String title;

    private String author;

    private Long year;

    @Override
    public String toString() {
        return String.format(
                "BookEntity[id=%s, title='%s', author='%s', year='%s']",
                id, title, author, year);
    }
}
