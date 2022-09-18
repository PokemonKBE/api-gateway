package com.example.demo.service;

import com.example.demo.pokemon.PokemonCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.pokemon.Calls.GET_CARDS;

@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private final DirectExchange directExchange;

    @Value("${routing-key.product}")
    private final String productServiceKey;


    public List<PokemonCard> getPokemonCardList() {

        var requestMessage = new Message("".getBytes());
        requestMessage.getMessageProperties().setType(GET_CARDS.toString());
        var returnMessage = rabbitTemplate.sendAndReceive(directExchange.getName(), productServiceKey, requestMessage);

        if(returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonCard>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonCard>>() {
                        }.getType()
                );
    }
}
