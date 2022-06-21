package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response.GetOrdersBySymbolResponse;
import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response.GetSymbolResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class BlockchainClient {

    WebClient client = WebClient.create("https://api.blockchain.com/");
    public GetSymbolResponse getSymbols() throws JsonProcessingException {


        ObjectMapper om = new ObjectMapper();
        //THIS WORKS
        Map<String, Object> response = client.get()
                .uri("/v3/exchange/symbols")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}).block();

        return om.convertValue(response, GetSymbolResponse.class);
    }

    public GetOrdersBySymbolResponse getOrdersBySymbol(String symbol){
        return client.get()
                .uri("/v3/exchange/l3/"+symbol)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(GetOrdersBySymbolResponse.class).block();
    }
}
