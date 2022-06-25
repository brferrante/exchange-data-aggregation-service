package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc;

import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response.GetOrdersBySymbolResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class BlockchainClient {

    WebClient client = WebClient.create("https://api.blockchain.com/");
    //  fetch the different symbols from the Blockchain.com exchange endpoint
    public Map<String, Object> getSymbols() {
        return  client.get().uri("/v3/exchange/symbols")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}).block();

    }

    //  get the currency orders data based on symbol.
    public GetOrdersBySymbolResponse getOrdersBySymbol(String symbol){
        return client.get()
                .uri("/v3/exchange/l3/"+symbol)//use a parameterization method here instead of concatenating strings
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(GetOrdersBySymbolResponse.class).block(); //add pagination for the calls, limit the amount of requests
    }
}
