package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc;

import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response.GetOrdersBySymbolResponse;
import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response.GetSymbolResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BlockchainClient {

    public GetSymbolResponse getSymbols(){
        return WebClient.create("https://api.blockchain.com/v3/exchange/symbols")
                .get().retrieve().bodyToMono(GetSymbolResponse.class).block();
    }

    public GetOrdersBySymbolResponse getOrdersBySymbol(String symbol){
        return WebClient.create("https://api.blockchain.com/v3/exchange/l3/"+symbol)
                .get().retrieve().bodyToMono(GetOrdersBySymbolResponse.class).block();
    }
}
