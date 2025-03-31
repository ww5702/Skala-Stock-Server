/*
 * 테스트용 Controller 입니다.
 * 
 */

package com.skala.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skala.stock.domain.Player;
import com.skala.stock.dto.CreatePlayerRequest;
import com.skala.stock.dto.PlayerResponse;
import com.skala.stock.repository.PlayerRepository;
import com.skala.stock.service.PlayerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable String id) {
        return playerService.getPlayer(id);
    }
    // 플레이어 정보 + 보유 주식 응답용 (Vue용)
    @GetMapping("/{id}/details")
    public PlayerResponse getPlayerDetails(@PathVariable String id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return PlayerResponse.from(player);
    }

    @PostMapping
    public Player createPlayer(@RequestBody CreatePlayerRequest request) {
        return playerService.createPlayer(request.getId(), request.getMoney(), request.getPassword());
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Player loginRequest) {
        return playerRepository.findByIdAndPassword(loginRequest.getId(), loginRequest.getPassword())
                .map(player -> ResponseEntity.ok(player.getId() + "님, 환영합니다!"))
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));
    }

    
    


}
