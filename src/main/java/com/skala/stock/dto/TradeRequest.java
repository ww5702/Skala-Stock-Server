package com.skala.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRequest {
    private String playerId;
    private String stockName;
    private int quantity;
}