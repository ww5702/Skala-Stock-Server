package com.skala.stock.service;

import com.skala.stock.domain.Stock;
import com.skala.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    
    /** 전체 주식 목록 조회 */
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    /** 주식 등록 (초기 데이터 입력용) */
    public Stock createStock(String name, int price) {
        Stock stock = new Stock();
        stock.setName(name);
        stock.setPrice(price);
        return stockRepository.save(stock);
    }
}
