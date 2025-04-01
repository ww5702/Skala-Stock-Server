package com.skala.stock.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.skala.stock.domain.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlayerResponse {

    private String id;
    private int money;
    private List<StockResponse> stocks;

    // static 메서드는 클래스 내부에 있어야 합니다
    public static PlayerResponse from(Player player) {
        List<StockResponse> stockResponses = player.getStocks().stream()
                .map(ps -> new StockResponse(
                        ps.getStock().getName(),
                        ps.getPrice(),
                        ps.getQuantity()))
                .collect(Collectors.toList());  // toList()는 Java 16+만 가능

        return new PlayerResponse(player.getId(), player.getMoney(), stockResponses);
        
    }
}
