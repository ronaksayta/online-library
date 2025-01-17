package com.ronakcode.onlinelibrary.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    private String isbn;
    private String title;
    private String subTitle;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String authors;
    private String imageLink;
}
