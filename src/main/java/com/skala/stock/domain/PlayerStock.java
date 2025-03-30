package com.skala.stock.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStock {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "stock_name")
    private Stock stock;

    private int quantity;
}
