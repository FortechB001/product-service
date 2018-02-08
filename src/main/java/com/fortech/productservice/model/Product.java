package com.fortech.productservice.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@RequiredArgsConstructor
public class Product {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String description;
}
