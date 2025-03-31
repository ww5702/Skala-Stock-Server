package com.skala.stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skala.stock.domain.Player;
import com.skala.stock.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    /** 전체 플레이어 조회 */
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /** 플레이어 ID로 조회 */
    public Player getPlayer(String id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 플레이어입니다."));
    }

    // 사용자 등록 (비밀번호 포함)
    public Player createPlayer(String id, int money, String password) {
        Player player = new Player();
        player.setId(id);
        player.setMoney(money);
        player.setPassword(password); // ← 비밀번호 저장
        return playerRepository.save(player);
    }
}
