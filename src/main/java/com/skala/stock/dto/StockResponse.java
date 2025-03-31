package com.skala.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockResponse {
    private Long id;
    private String name;
    private int price;
    private int quantity; // PlayerStock이 가지고 있는 수량

    // PlayerResponse.from()에서 사용할 생성자 추가
    public StockResponse(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
