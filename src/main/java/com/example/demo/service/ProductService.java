package com.example.demo.service;

import com.example.demo.dto.PokemonCardRequest;
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


    protected List<PokemonCardRequest> getPokemonCards() {

        Message returnMessage = productServiceRequest(GET_CARDS);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonCardRequest>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonCardRequest>>() {
                        }.getType()
                );
    }

    protected List<PokemonDeckRequest> getPokemonDecks() {
        Message returnMessage = productServiceRequest(GET_DECKS);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return new ArrayList<PokemonDeckRequest>();
        }

        return new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), new TypeToken<List<PokemonDeckRequest>>() {
                        }.getType()
                );
    }

    protected PokemonDeckResponse createPokemonDeck(String pokemonCards) {
        // TODO: 20.09.2022 get actual list from frontend. dont fuck around with Strings

        PokemonDeckRequest testRequest = createTestPokemonDeck();

        Message requestMessage = new Message((new Gson().toJson(testRequest)).getBytes());
        requestMessage.getMessageProperties().setType(CREATE_DECK.toString());
        var returnMessage = rabbitTemplate.sendAndReceive(directExchange.getName(), productServiceKey, requestMessage);

        if (returnMessage == null) {
            return new PokemonDeckResponse()
                    .setId(0).setName("INVALID")
                    .setPokemonCardList(new ArrayList<>())
                    .setTotalPrice(BigDecimal.ZERO);
        }

        PokemonDeckResponse pokemonDeckResponse = new Gson()
                .fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), PokemonDeckResponse.class);

        return pokemonDeckResponse;
    }

    private Message productServiceRequest(APICall requestType) {
        var requestMessage = new Message("".getBytes());
        requestMessage.getMessageProperties().setType(requestType.toString());
        return rabbitTemplate.sendAndReceive(directExchange.getName(), productServiceKey, requestMessage);
    }

    private PokemonDeckRequest createTestPokemonDeck() {
        PokemonCardRequest testPokemonCard1 = new PokemonCardRequest().setId(0).setName("Test Name").setType("Test Type")
                .setDescription("Test Description").setExpansion("Test Expansion").setHp("100").setIllustrator("Test Illustrator")
                .setNumber("Test Number").setStage("Test Stage").setRarity("Test Rarity").setPrice("1.0");

        PokemonCardRequest testPokemonCard2 = new PokemonCardRequest().setId(0).setName("Test Name").setType("Test Type")
                .setDescription("Test Description").setExpansion("Test Expansion").setHp("100").setIllustrator("Test Illustrator")
                .setNumber("Test Number").setStage("Test Stage").setRarity("Test Rarity").setPrice("2.0");


        List<PokemonCardRequest> testList = new ArrayList<>();
        testList.add(testPokemonCard1);
        testList.add(testPokemonCard2);

        return new PokemonDeckRequest().setId(0).setName("Test Deck").setPokemonCardList(testList);
    }

}
