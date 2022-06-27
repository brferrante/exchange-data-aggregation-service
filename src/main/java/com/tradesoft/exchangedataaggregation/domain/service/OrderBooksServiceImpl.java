package com.tradesoft.exchangedataaggregation.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.model.*;
import com.tradesoft.exchangedataaggregation.domain.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBooksServiceImpl implements OrderBooksService {


    private final OrdersRepository ordersRepository;
    @Override
    public List<AggregatedBook> getOrderBook(
            AvailableExchanges exchangeName, Long page, Long size, Boolean isSorted, Optional<String> symbol, OperationType operationType) throws JsonProcessingException {
            return getAggregatedBooks(exchangeName, page, size, isSorted, symbol, operationType);




//        return this.getAllSymbols().stream()
//                // fetch orders by symbol list, add to aggregated order books intermediate list
//                .map(ordersRepository::getOrderBookBySymbol)
//                // generate averages and add orderBook to return map List<OrderBook>
//                .map(this::aggregateOrderBook)
//                .collect(Collectors.toList());

    }

    private ArrayList<AggregatedBook> getAggregatedBooks( AvailableExchanges exchangeName,
            Long page, Long size, Boolean isSorted, Optional<String> symbol, OperationType operationType) throws JsonProcessingException, NotFoundException {
        int from = (int) ((page -1)* size);
        int to = (int) (page * size);
        var symbols = new ArrayList<String>();
        if(symbol.isEmpty()) {
            symbols.addAll(this.getAllSymbols(exchangeName));
        } else {
            ArrayList<AggregatedBook> returnList = new ArrayList<>();
            returnList.add(this.aggregateOrderBook(ordersRepository.getOrderBookBySymbol(exchangeName, symbol.get().toUpperCase(Locale.ROOT)),operationType));
            return returnList;
        }
        if(isSorted){
            //if required here we can add a comparator to order by asc or desc;
            Collections.sort(symbols);
        }
        var returnList = new ArrayList<AggregatedBook>();
        for (int i = from; i < to; i++) {
            //fetch orders by paginated symbol list, add to aggregated order books intermediate list
            returnList.add(this.aggregateOrderBook(ordersRepository.getOrderBookBySymbol(exchangeName, symbols.get(i)), operationType));
        }
        return returnList;
    }

    @Override
    // Exchange enum will tell the repository which exchange it should hit up.
    public List<String> getAllSymbols(AvailableExchanges exchange) throws JsonProcessingException, NotFoundException {
        //fetch all symbols from exchange
        return new ArrayList<>(ordersRepository.getSymbols(exchange));

    }

    private AggregatedOperationSearch aggregateOperationSearch(List<Operation> operations, String symbol){
        return AggregatedOperationSearch.builder()
                .symbol(symbol)
                .operation(operations)
                .build();
    }
    private AggregatedBook aggregateOrderBook(OrderBook orderBook, OperationType operationType) {
        // Run averages and return aggregated orderBook
        // Add only requested operation type
        switch (operationType){
            case BID:
                return AggregatedBook.builder()
                        .symbol(orderBook.getSymbol())
                        .averageBid(getAverageOperation(orderBook.getBids()))
                        .build();
            case ASK:
                return AggregatedBook.builder()
                        .symbol(orderBook.getSymbol())
                        .averageAsk(getAverageOperation(orderBook.getAsks()))
                        .build();
            case ALL:
            default:
                return AggregatedBook.builder()
                        .symbol(orderBook.getSymbol())
                        .averageAsk(getAverageOperation(orderBook.getAsks()))
                        .averageBid(getAverageOperation(orderBook.getBids()))
                        .build();
        }

    }

    private AggregatedOperation getAverageOperation(List<Operation> operations) {
        // Generate average price and quantity sum for operations.
        // Assumption: Rounding up to 5 spaces the same as seen on Blockchain format for value display.
        DecimalFormat decimalFormat = new DecimalFormat("#.######");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return AggregatedOperation.builder()
                .priceAverage(Double.valueOf(decimalFormat.format(operations.stream()
                .map(Operation::getPrice)
                .collect(Collectors.averagingDouble(Double::doubleValue))
                        )))
        // Don't think we should round the decimal spaces for quantity since that would be unreliable information provided,
        // but if needed for any sort of display reason, the same or a different DecimalFormat can be applied.
                .totalQuantity(operations.stream()
                        .map(Operation::getQuantity)
                        .reduce(0.0, Double::sum
                        ))
                .build();
    }
}
