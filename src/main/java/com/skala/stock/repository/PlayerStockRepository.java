package com.skala.stock.repository;

import com.skala.stock.domain.PlayerStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStockRepository extends JpaRepository<PlayerStock, Long> {
}
