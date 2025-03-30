package com.skala.stock.config;

import com.skala.stock.domain.Stock;
import com.skala.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StockRepository stockRepository;

    @Override
    public void run(String... args) {
        if (stockRepository.count() == 0) {
            stockRepository.save(new Stock("삼성전자", 70000));
            stockRepository.save(new Stock("LG에너지솔루션", 510000));
            stockRepository.save(new Stock("NAVER", 180000));
            stockRepository.save(new Stock("카카오", 59000));
            stockRepository.save(new Stock("현대차", 210000));
            System.out.println("✅ 초기 주식 데이터가 삽입되었습니다.");
        }
    }
}
