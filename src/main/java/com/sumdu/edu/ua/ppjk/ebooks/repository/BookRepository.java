package com.sumdu.edu.ua.ppjk.ebooks.repository;

import com.sumdu.edu.ua.ppjk.ebooks.entity.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<BookEntity, String> {

    Optional<BookEntity> findByTitle(String title);

    void deleteByTitle(String title);
}
