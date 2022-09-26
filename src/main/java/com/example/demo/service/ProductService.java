package com.example.demo.service;

import com.example.demo.dto.PokemonCardResponse;
import com.example.demo.dto.PokemonDeckRequest;
import com.example.demo.dto.PokemonDeckResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.service.APICall.*;

@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Value("${routing-key.product}")
    private String productServiceKey;


    protected List<PokemonCardResponse> getPokemonCards() {

        Message returnMessage = productServiceRequest(GET_CARDS);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonCardResponse>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonCardResponse>>() {
                        }.getType()
                );
    }

    protected List<PokemonDeckResponse> getPokemonDecks() {
        Message returnMessage = productServiceRequest(GET_DECKS);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonDeckResponse>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonDeckResponse>>() {
                        }.getType()
                );
    }

    protected PokemonDeckResponse createPokemonDeck(PokemonDeckRequest deckRequest) {
        Message requestMessage = new Message((new Gson().toJson(deckRequest)).getBytes());
        requestMessage.getMessageProperties().setType(CREATE_DECK.toString());
        var returnMessage = rabbitTemplate.sendAndReceive(directExchange.getName(), productServiceKey, requestMessage);

        if (returnMessage == null) {
            return new PokemonDeckResponse()
                    .setName("INVALID")
                    .setPokemonCardList(new ArrayList<>())
                    .setPrice(BigDecimal.ZERO);
        }

        PokemonDeckResponse pokemonDeckResponse = new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), PokemonDeckResponse.class);

        return pokemonDeckResponse;
    }

    protected String getPokemonFact() {
        Message returnMessage = productServiceRequest(GET_FACT);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return "I don't know anything about cats.";
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), String.class);


    }

    private Message productServiceRequest(APICall requestType) {
        var requestMessage = new Message("".getBytes());
        requestMessage.getMessageProperties().setType(requestType.toString());
        return rabbitTemplate.sendAndReceive(directExchange.getName(), productServiceKey, requestMessage);
    }
}
