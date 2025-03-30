package com.skala.stock.controller;

import com.skala.stock.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.skala.stock.dto.TradeRequest;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping("/buy")
    public String buyStock(@RequestBody TradeRequest request) {
        tradeService.buyStock(request.getPlayerId(), request.getStockName(), request.getQuantity());
        return "매수 성공";
    }

    @PostMapping("/sell")
    public String sellStock(@RequestBody TradeRequest request) {
        tradeService.sellStock(request.getPlayerId(), request.getStockName(), request.getQuantity());
        return "매도 성공";
    }

}
