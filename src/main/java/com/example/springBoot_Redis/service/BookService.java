package com.example.springBoot_Redis.service;

import java.util.List;

import com.example.springBoot_Redis.dto.CreateBookDto;
import com.example.springBoot_Redis.entity.Book;
import com.example.springBoot_Redis.repository.BookRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "bookService")
public class BookService {

    @Setter(onMethod_ = @Autowired)
    private BookRepository bookRepository;

    public Book create(CreateBookDto bookDto) {
        Book book = new Book()
                .setTitle(bookDto.getTitle())
                .setAuthor(bookDto.getAuthor())
                .setCategory(bookDto.getCategory())
                .setPrice(Double.valueOf(bookDto.getPrice()));
        book = bookRepository.save(book);
        return book;
    }

    @Cacheable(value = "findById", key = "#id")
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @CacheEvict(value = "findById", key = "#id")
    public void clearId(Long id) {
    }

    @Cacheable(value = "findAll")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @CacheEvict(value = "findAll", allEntries = true)
    public void clearCache() {
    }

}
