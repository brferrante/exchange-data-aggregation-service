package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.AvailableExchanges;
import com.tradesoft.exchangedataaggregation.domain.model.OrderBook;
import com.tradesoft.exchangedataaggregation.domain.repository.OrdersRepository;
import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.converter.OrderBookConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.util.Set;

@Repository
@AllArgsConstructor
public class OrdersRepositoryImpl implements OrdersRepository {
    private final BlockchainClient blockchainClient;
    @Override
    public Set<String> getSymbols(AvailableExchanges exchange) throws NotFoundException {
        switch (exchange) {
            case BLOCKCHAIN_DOT_COM:
                return blockchainClient.getSymbols().keySet();
            case BINANCE:
            default:
                throw new NotFoundException("Exchange not yet implemented!");
        }
    }

    @Override
    public OrderBook getOrderBookBySymbol(AvailableExchanges exchange, String symbol) throws NotFoundException {
        switch (exchange) {
            case BLOCKCHAIN_DOT_COM:
                return OrderBookConverter.fromResponse(blockchainClient.getOrdersBySymbol(symbol));
            case BINANCE:
            default:
                throw new NotFoundException("Exchange not yet implemented!");
        }
    }
}
