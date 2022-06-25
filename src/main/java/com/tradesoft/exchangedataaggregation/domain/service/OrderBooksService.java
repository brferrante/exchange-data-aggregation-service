package com.tradesoft.exchangedataaggregation.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedOperationSearch;

import java.util.List;

public interface OrderBooksService {
    //Business logic goes here.

    /*
-    exchanges/{exchange-name}/order-books
-    exchanges/{exchange-name}/order-books/{symbol}/ask
-    exchanges/{exchange-name}/order-books/{symbol}/bid

-    exchanges/{exchange-name}/order-books?orderBy=asc
-    exchanges/{exchange-name}/order-books/{symbol}/ask?orderBy=asc
-    exchanges/{exchange-name}/order-books/{symbol}/bid?orderBy=asc
     */

    List<AggregatedBook> getOrderBook(String exchangeName, Long page, Long size, Boolean isSorted) throws JsonProcessingException;

    AggregatedOperationSearch getOrderBookOperationBySymbol(String symbol, String operation);

    List<String> getAllSymbols() throws JsonProcessingException;
    // Expected functionality: Return the data value for a specific symbol, price averages for trades

    // Expected Functionality: Filter by Symbol alphabetically, return list

    // Expected Functionality: Filter by Symbol and Operation Type, return object;

}
