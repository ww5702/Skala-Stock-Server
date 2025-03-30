/*
 * 테스트용 Controller 입니다.
 * 
 */

package com.skala.stock.controller;

import com.skala.stock.domain.Player;
import com.skala.stock.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.skala.stock.dto.CreatePlayerRequest;


import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable String id) {
        return playerService.getPlayer(id);
    }

    @PostMapping
    public Player createPlayer(@RequestBody CreatePlayerRequest request) {
        return playerService.createPlayer(request.getId(), request.getMoney());
    }

}
