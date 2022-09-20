package com.example.demo.controller;

import com.example.demo.entity.PokemonCard;
import com.example.demo.entity.PokemonCardDeck;
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

    @GetMapping("/getAllCards")
    public List<PokemonCard> getPokemonCard() {
        return apiService.getPokemonCardList();
    }

    @GetMapping("/getAllDecks")
    public List<PokemonCardDeck> getPokemonCardDeck() {
        return apiService.getPokemonCardDeckList();
    }

    @GetMapping("/getCurrency")
    public BigDecimal getCurrency(String currency, String price) {
        return apiService.getCurrency(currency, price);
    }

}
