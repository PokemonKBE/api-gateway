package com.example.demo.service;

import com.example.demo.dto.CurrencyRequest;
import com.example.demo.dto.PokemonCardRequest;
import com.example.demo.dto.PokemonDeckRequest;
import com.example.demo.dto.PokemonDeckResponse;
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

    public List<PokemonCardRequest> getPokemonCards() {
        return productService.getPokemonCards();
    }

    public List<PokemonDeckRequest> getPokemonDecks() {
        return productService.getPokemonDecks();
    }

    public BigDecimal getPriceInCurrency(CurrencyRequest currencyRequest) {
        return currencyService.getPriceInCurrency(currencyRequest);
    }

    public PokemonDeckResponse createPokemonDeck(PokemonDeckRequest deckRequest) {
        return productService.createPokemonDeck(deckRequest);
    }
}
