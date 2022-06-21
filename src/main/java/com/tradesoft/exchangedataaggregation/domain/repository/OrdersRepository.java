package com.tradesoft.exchangedataaggregation.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.OrderBook;

import java.util.Set;

public interface OrdersRepository {
    //Handles the information from orders
    Set<String> getSymbols() throws JsonProcessingException;

    OrderBook getOrderBookBySymbol(String symbol);
}
