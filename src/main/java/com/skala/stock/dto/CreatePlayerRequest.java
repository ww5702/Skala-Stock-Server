package com.skala.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlayerRequest {
    private String id;
    private int money;
}