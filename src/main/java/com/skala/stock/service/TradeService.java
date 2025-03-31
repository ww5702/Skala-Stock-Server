package com.skala.stock.service;

import org.springframework.stereotype.Service;

import com.skala.stock.domain.Player;
import com.skala.stock.domain.PlayerStock;
import com.skala.stock.domain.Stock;
import com.skala.stock.dto.PlayerResponse;
import com.skala.stock.repository.PlayerRepository;
import com.skala.stock.repository.PlayerStockRepository;
import com.skala.stock.repository.StockRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TradeService {

    private final PlayerRepository playerRepository;
    private final StockRepository stockRepository;
    private final PlayerStockRepository playerStockRepository;

    @Transactional
    public PlayerResponse buyStock(String playerId, String stockName, int quantity) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));

        Stock stock = stockRepository.findById(stockName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주식입니다."));

        int totalPrice = stock.getPrice() * quantity;
        if (player.getMoney() < totalPrice) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }

        player.setMoney(player.getMoney() - totalPrice);

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
            player.addPlayerStock(playerStock);
        }

        return PlayerResponse.from(player);
    }

    @Transactional
    public PlayerResponse sellStock(String playerId, String stockName, int quantity) {
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

        int totalPrice = stock.getPrice() * quantity;
        player.setMoney(player.getMoney() + totalPrice);

        if (playerStock.getQuantity() == quantity) {
            player.getStocks().remove(playerStock);
            playerStockRepository.delete(playerStock);
        } else {
            playerStock.setQuantity(playerStock.getQuantity() - quantity);
        }

        return PlayerResponse.from(player);
    }

}
