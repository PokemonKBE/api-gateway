package com.example.demo.service;

import com.example.demo.pokemon.PokemonCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class APIService {
    private final ProductService productService;

    public List<PokemonCard> getPokemonCardList() {
        return productService.getPokemonCardList();
    }
}
