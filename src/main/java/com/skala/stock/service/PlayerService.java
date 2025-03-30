package com.skala.stock.service;

import com.skala.stock.domain.Player;
import com.skala.stock.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /** 플레이어 등록 (이미 있으면 오류) */
    public Player createPlayer(String id, int money) {
        if (playerRepository.existsById(id)) {
            throw new IllegalArgumentException("이미 존재하는 플레이어 ID입니다.");
        }

        Player player = new Player();
        player.setId(id);
        player.setMoney(money);

        return playerRepository.save(player);
    }
}
