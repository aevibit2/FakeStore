package com.example.firstSpringApi.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Product {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private Category category;
}
