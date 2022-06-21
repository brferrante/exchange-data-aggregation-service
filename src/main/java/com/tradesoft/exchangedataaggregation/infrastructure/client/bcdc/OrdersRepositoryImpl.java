package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.OrderBook;
import com.tradesoft.exchangedataaggregation.domain.repository.OrdersRepository;
import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.converter.OrderBookConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@AllArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepository {
    private final BlockchainClient blockchainClient;
    @Override
    public Set<String> getSymbols() throws JsonProcessingException {
        return blockchainClient.getSymbols().getResponse().keySet();
    }

    @Override
    public OrderBook getOrderBookBySymbol(String symbol) {
        return OrderBookConverter.fromResponse(blockchainClient.getOrdersBySymbol(symbol));
    }
}
