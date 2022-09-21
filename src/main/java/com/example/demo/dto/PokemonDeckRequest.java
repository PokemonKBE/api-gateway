package com.example.demo.dto;

import com.example.demo.dto.PokemonCardRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PokemonDeckRequest {
    private int id;
    private String name;
    private List<PokemonCardRequest> pokemonCardList;
}
