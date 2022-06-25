package com.tradesoft.exchangedataaggregation.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.*;
import com.tradesoft.exchangedataaggregation.domain.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBooksServiceImpl implements OrderBooksService {
    //TODO: Refactor ordersRepository to a Map<String, OrderRepository>.
    //  Key will be exchange name, will need to add a previous step to get the exchange name,
    //  refactor getOrderBook() to getOrderBook(String exchangeName)

    private final OrdersRepository ordersRepository;
    @Override
    //TODO add pagination parameter
    public List<AggregatedBook> getOrderBook(String exchangeName, Long page, Long size, Boolean isSorted) throws JsonProcessingException {
        //TODO: Add logic for exchange-name to work with different exchange names.
        // fetch the first N symbol list from exchange (N = 10, configurable). Use pagination parameters inside getAllSymbols
         return getAggregatedBooks(page, size, isSorted);

//        return this.getAllSymbols().stream()
//                // fetch orders by symbol list, add to aggregated order books intermediate list
//                .map(ordersRepository::getOrderBookBySymbol)
//                // generate averages and add orderBook to return map List<OrderBook>
//                .map(this::aggregateOrderBook)
//                .collect(Collectors.toList());

    }

    private ArrayList<AggregatedBook> getAggregatedBooks(Long page, Long size, Boolean isSorted) throws JsonProcessingException {
        int from = (int) ((page -1)* size);
        int to = (int) (page * size);
        var symbols = this.getAllSymbols();
        if(isSorted){
            //TODO add comparator to order by asc or desc;
            Collections.sort(symbols);
        }
        var returnList = new ArrayList<AggregatedBook>();
        for (int i = from; i < to; i++) {
            //fetch orders by paginated symbol list, add to aggregated order books intermediate list
            returnList.add(this.aggregateOrderBook(ordersRepository.getOrderBookBySymbol(symbols.get(i))));
        }
        return returnList;
    }

    @Override
    //TODO: Cache this method to an exchange-symbol map, parameter exchange name will domain this
    public List<String> getAllSymbols() throws JsonProcessingException {
        //TODO: Add logic for exchange-name to work with different exchange names.
        // fetch the first N symbol list from exchange (N = 10, configurable)
        return new ArrayList<>(ordersRepository.getSymbols());

    }

    @Override
    public AggregatedOperationSearch getOrderBookOperationBySymbol(String symbol, String operation) {
        return aggregateOperationSearch(
                getDesiredOperation(ordersRepository.getOrderBookBySymbol(symbol),operation), symbol);
    }

    private AggregatedOperationSearch aggregateOperationSearch(List<Operation> operations, String symbol){
        return AggregatedOperationSearch.builder()
                .symbol(symbol)
                .operation(operations)
                .build();
    }
    private AggregatedBook aggregateOrderBook(OrderBook orderBook) {
        // Run averages and return aggregated orderBook
        return AggregatedBook.builder()
                .symbol(orderBook.getSymbol())
                .averageAsk(getAverageOperation(orderBook.getAsks()))
                .averageBid(getAverageOperation(orderBook.getBids()))
                .build();
    }

    private AggregatedOperation getAverageOperation(List<Operation> operations) {
        // Generate averages for operations.
        // Developer note: I wanted to round down using the Math library but realized some coins have very small
        // prices in the second or third decimal place, so I decided to treat the information raw as the exchange provides.
        // This leads to long decimal positions, but it's an accurate averaging.
        return AggregatedOperation.builder()
                .priceAverage(operations.stream()
                .map(Operation::getPrice)
                        .map(Math::round)
                .collect(Collectors.averagingDouble(Long::doubleValue)))
                .quantityAverage(operations.stream()
                        .map(Operation::getQuantity)
                        .reduce(0.0, Double::sum
                        ))
                .build();
    }

    //TODO fix this method
    private List<Operation> getDesiredOperation(OrderBook orderBookBySymbol, String operation){
        if (operation.equals("bid")){
            return orderBookBySymbol.getBids();
        } else if (operation.equals("ask")){
            return orderBookBySymbol.getAsks();
        } else return null;

    }
}
