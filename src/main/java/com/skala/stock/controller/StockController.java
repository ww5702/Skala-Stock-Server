package com.skala.stock.controller;

import com.skala.stock.domain.Stock;
import com.skala.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.skala.stock.dto.CreateStockRequest;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @PostMapping
    public Stock createStock(@RequestBody CreateStockRequest request) {
        return stockService.createStock(request.getName(), request.getPrice());
    }

}
