package com.example.demo.service;

import com.example.demo.dto.Currency;
import com.example.demo.dto.CurrencyRequest;
import com.example.demo.dto.CurrencyResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class CurrencyService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;
    @Value("${routing-key.currency}")
    private String currencyServiceKey;

    protected BigDecimal getPriceInCurrency(CurrencyRequest currencyRequest) {
        Message requestMessage = new Message((new Gson().toJson(currencyRequest)).getBytes());
        var returnMessage = rabbitTemplate.sendAndReceive(directExchange.getName(), currencyServiceKey, requestMessage);

        if (returnMessage == null) {
            log.info("Return message is null. Sending empty list.");
            return BigDecimal.ZERO;
        }
        CurrencyResponse currencyResponse = new Gson().fromJson(new String(returnMessage.getBody(), StandardCharsets.UTF_8), CurrencyResponse.class);

        return currencyResponse.getPrice();
    }


    private boolean isNumeral(String price) {
        try {
            BigDecimal bigDecimal = new BigDecimal(price);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
