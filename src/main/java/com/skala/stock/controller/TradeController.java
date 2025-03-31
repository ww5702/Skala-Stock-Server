package com.skala.stock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skala.stock.dto.PlayerResponse;
import com.skala.stock.dto.TradeRequest;
import com.skala.stock.service.TradeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/buy")
    public PlayerResponse buyStock(@RequestBody TradeRequest request) {
        return tradeService.buyStock(
                request.getPlayerId(),
                request.getStockName(),
                request.getQuantity()
        );
    }

    @PostMapping("/sell")
    public PlayerResponse sellStock(@RequestBody TradeRequest request) {
        return tradeService.sellStock(
                request.getPlayerId(),
                request.getStockName(),
                request.getQuantity()
        );
    }
}
