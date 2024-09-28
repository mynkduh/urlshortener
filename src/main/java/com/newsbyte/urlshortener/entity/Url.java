package com.newsbyte.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String longUrl;

    private int clickCount;
}

