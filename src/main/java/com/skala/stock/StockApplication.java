package com.skala.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.skala.stock.repository.StockRepository;
import com.skala.stock.scheduler.StockPriceScheduler;

@SpringBootApplication
@EnableScheduling
public class StockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}

	@Bean
    public StockPriceScheduler stockPriceScheduler(StockRepository stockRepository) {
        return new StockPriceScheduler(stockRepository);
    }
}
