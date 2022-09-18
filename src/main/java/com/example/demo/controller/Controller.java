package com.example.demo.controller;

import com.example.demo.pokemon.PokemonCard;
import com.example.demo.service.APIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private final APIService apiService;

    @GetMapping("/getAllCards")
    public List<PokemonCard> getPokemonCards() {
//        APIService apiService = new APIService();
        log.info("get all Pokemon Cards endpoint called !!!!!!!!!!!!!!!!");
        return apiService.getPokemonCardList();
    }

}
