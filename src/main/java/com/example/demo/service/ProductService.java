package com.example.demo.service;

import com.example.demo.entity.APICall;
import com.example.demo.entity.PokemonCard;
import com.example.demo.entity.PokemonCardDeck;
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

import static com.example.demo.entity.APICall.GET_CARDS;
import static com.example.demo.entity.APICall.GET_DECKS;

@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Value("${routing-key.product}")
    private String productServiceKey;


    protected List<PokemonCard> getPokemonCardList() {

        Message returnMessage = productServiceRequest(GET_CARDS);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonCard>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonCard>>() {
                        }.getType()
                );
    }

    protected List<PokemonCardDeck> pokemonCardDeckList() {
        Message returnMessage = productServiceRequest(GET_DECKS);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonCardDeck>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonCardDeck>>() {
                        }.getType()
                );
    }

    private Message productServiceRequest(APICall requestType) {
        var requestMessage = new Message("".getBytes());
        requestMessage.getMessageProperties().setType(requestType.toString());
        var returnMessage = rabbitTemplate.sendAndReceive(directExchange.getName(), productServiceKey, requestMessage);

        return returnMessage;
    }
}
