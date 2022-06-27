package com.tradesoft.exchangedataaggregation.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.AvailableExchanges;
import com.tradesoft.exchangedataaggregation.domain.model.OrderBook;
import org.webjars.NotFoundException;

import java.util.Set;

public interface OrdersRepository {
    //Handles the information from orders
    Set<String> getSymbols(AvailableExchanges exchange) throws JsonProcessingException, NotFoundException;

    OrderBook getOrderBookBySymbol(AvailableExchanges exchange, String symbol) throws NotFoundException;
}
