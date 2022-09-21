package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PokemonCardResponse {
    private long id;
    private String name;
    private String description;
    private String hp;
    private String type;
    private String stage;
    private String expansion;
    private String rarity;
    private String number;
    private String illustrator;
    private String price;
}
