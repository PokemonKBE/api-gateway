package com.example.demo.service;

import com.example.demo.entity.PokemonCard;
import com.example.demo.entity.PokemonCardDeck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class APIService {
    private final ProductService productService;
    private final CurrencyService currencyService;

    public List<PokemonCard> getPokemonCardList() {
        return productService.getPokemonCardList();
    }

    public List<PokemonCardDeck> getPokemonCardDeckList() {
        return productService.pokemonCardDeckList();
    }

    public BigDecimal getCurrency(String currency, String price) {
        return currencyService.getCurrency(currency, price);
    }
}
