package com.tradesoft.exchangedataaggregation.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;
import com.tradesoft.exchangedataaggregation.domain.model.AvailableExchanges;
import com.tradesoft.exchangedataaggregation.domain.model.OperationType;

import java.util.List;
import java.util.Optional;

public interface OrderBooksService {
    //Business logic goes here.

    List<AggregatedBook> getOrderBook(
            AvailableExchanges exchangeName, Long page, Long size, Boolean isSorted, Optional<String> symbol, OperationType operationType) throws JsonProcessingException;


    List<String> getAllSymbols(AvailableExchanges exchangeName) throws JsonProcessingException;

}
