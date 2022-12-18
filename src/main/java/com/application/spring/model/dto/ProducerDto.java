package com.application.spring.model.dto;

import com.application.spring.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDto {

    private UUID id;
    private String name;
    private List<Product> products;
}
