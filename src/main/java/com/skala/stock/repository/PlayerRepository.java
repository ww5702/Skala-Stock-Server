package com.skala.stock.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.skala.stock.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, String> {
    Optional<Player> findByIdAndPassword(String id, String password); // 로그인용 메서드
}
