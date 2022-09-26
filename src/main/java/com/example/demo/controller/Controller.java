package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.APIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final APIService apiService;

    @GetMapping("/get-cards")
    public List<PokemonCardResponse> getPokemonCards() {
        return apiService.getPokemonCards();
    }

    @GetMapping("/get-decks")
    public List<PokemonDeckResponse> getPokemonDecks() {
        return apiService.getPokemonDecks();
    }

    @RequestMapping(value = "/get-currency", method = RequestMethod.GET)
    public BigDecimal getPriceInCurrency(CurrencyRequest currencyRequest) {

        return apiService.getPriceInCurrency(currencyRequest);
    }

    @GetMapping("/get-fact")
    public String getCatFact() {
        return apiService.getPokemonFact();
    }

    @PostMapping("/create-deck")
    public void createPokemonDeck(@RequestBody PokemonDeckRequest deckRequest) {
        apiService.createPokemonDeck(deckRequest);
    }
}
