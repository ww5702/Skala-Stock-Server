package com.skala.stock.service;

import com.skala.stock.domain.*;
import com.skala.stock.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final PlayerRepository playerRepository;
    private final StockRepository stockRepository;
    private final PlayerStockRepository playerStockRepository;

    @Transactional
    public void buyStock(String playerId, String stockName, int quantity) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        Stock stock = stockRepository.findById(stockName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주식입니다."));

        int totalPrice = stock.getPrice() * quantity;
        if (player.getMoney() < totalPrice) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        // 잔액 차감
        player.setMoney(player.getMoney() - totalPrice);

        // 기존 보유 주식 있는지 확인
        PlayerStock playerStock = player.getStocks().stream()
                .filter(ps -> ps.getStock().getName().equals(stockName))
                .findFirst()
                .orElse(null);

        if (playerStock != null) {
            playerStock.setQuantity(playerStock.getQuantity() + quantity);
        } else {
            playerStock = new PlayerStock();
            playerStock.setPlayer(player);
            playerStock.setStock(stock);
            playerStock.setQuantity(quantity);
            player.getStocks().add(playerStock); // 연관관계 주입
        }

        playerRepository.save(player); // cascade 옵션 덕분에 stock도 함께 저장
    }

    @Transactional
    public void sellStock(String playerId, String stockName, int quantity) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        Stock stock = stockRepository.findById(stockName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주식입니다."));

        PlayerStock playerStock = player.getStocks().stream()
                .filter(ps -> ps.getStock().getName().equals(stockName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("보유하지 않은 주식입니다."));

        if (playerStock.getQuantity() < quantity) {
            throw new IllegalArgumentException("보유 수량보다 많은 수량을 판매할 수 없습니다.");
        }

        // 수익 지급
        int totalPrice = stock.getPrice() * quantity;
        player.setMoney(player.getMoney() + totalPrice);

        // 수량 차감 또는 삭제
        if (playerStock.getQuantity() == quantity) {
            player.getStocks().remove(playerStock);
            playerStockRepository.delete(playerStock);
        } else {
            playerStock.setQuantity(playerStock.getQuantity() - quantity);
        }

        playerRepository.save(player);
    }
}
