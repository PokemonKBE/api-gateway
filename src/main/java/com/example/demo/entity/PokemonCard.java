package com.example.demo.entity;

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
public class PokemonCard {

    private int id;
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
