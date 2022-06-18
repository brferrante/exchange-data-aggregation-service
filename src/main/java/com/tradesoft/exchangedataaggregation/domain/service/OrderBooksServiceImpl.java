package com.tradesoft.exchangedataaggregation.domain.service;

import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedOperation;
import com.tradesoft.exchangedataaggregation.domain.model.Operation;
import com.tradesoft.exchangedataaggregation.domain.model.OrderBook;
import com.tradesoft.exchangedataaggregation.domain.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBooksServiceImpl implements OrderBooksService {
    //TODO: Refactor ordersRepository to a Map<String, OrderRepository>.
    //  Key will be exchange name, will need to add a previous step to get the exchange name,
    //  refactor getOrderBook() to getOrderBook(String exchangeName)

    private final OrdersRepository ordersRepository;
    @Override
    public List<AggregatedBook> getOrderBook() {
        // fetch the first N symbol list from exchange (N = 10, configurable)
        return ordersRepository.getSymbols().stream()
                // fetch orders by symbol list, add to aggregated order books intermediate list
                .map(ordersRepository::getOrderBookBySymbol)
                // generate averages and add orderBook to return map List<OrderBook>
                .map(this::aggregateOrderBook)
                .collect(Collectors.toList());

    }

    private AggregatedBook aggregateOrderBook(OrderBook orderBook) {
        return AggregatedBook.builder()
                .symbol(orderBook.getSymbol())
                .averageAsk(getAverageOperation(orderBook.getAsks()))
                .averageBid(getAverageOperation(orderBook.getBids()))
                .build();
    }

    private AggregatedOperation getAverageOperation(List<Operation> operations) {
        return AggregatedOperation.builder()
                .priceAverage(operations.stream()
                .map(Operation::getPrice)
                .collect(Collectors.averagingDouble(Double::doubleValue)))
                .quantityAverage(operations.stream()
                        .map(Operation::getQuantity)
                        .reduce(0.0, Double::sum
                        ))
                .build();
    }
}
