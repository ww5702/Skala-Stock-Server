package com.skala.stock.scheduler;

import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.skala.stock.domain.Stock;
import com.skala.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPriceScheduler {

    private final StockRepository stockRepository;
    private final Random random = new Random();

    // 5초마다 실행 (5000ms)
    @Scheduled(fixedRate = 5000)
    public void updateStockPrices() {
        System.out.println("🟡 스케줄러 실행됨");

        List<Stock> stocks = stockRepository.findAll();
        System.out.println("📦 전체 주식 수: " + stocks.size());

        for (Stock stock : stocks) {
            int originalPrice = stock.getPrice();
            double changeRate = (random.nextDouble() * 0.2) - 0.1;
            int newPrice = (int) Math.round(originalPrice * (1 + changeRate));

            stock.setPrice(newPrice);
            System.out.println("🟢 " + stock.getName() + ": " + originalPrice + " → " + newPrice);
        }

        stockRepository.saveAll(stocks);
    }
}
