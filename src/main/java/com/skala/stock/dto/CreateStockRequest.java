package com.skala.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStockRequest {
    private String name;
    private int price;
}