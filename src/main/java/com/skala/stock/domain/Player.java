package com.skala.stock.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    private String id;

    private int money;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerStock> stocks = new ArrayList<>();

    public void addPlayerStock(PlayerStock ps) {
        this.stocks.add(ps);
        ps.setPlayer(this);
    }

    public PlayerStock findStockByName(String name) {
        return stocks.stream()
                .filter(ps -> ps.getStock().getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
