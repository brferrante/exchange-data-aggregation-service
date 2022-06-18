package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.converter;

import com.tradesoft.exchangedataaggregation.domain.model.Operation;
import com.tradesoft.exchangedataaggregation.domain.model.OrderBook;
import com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response.GetOrdersBySymbolResponse;

import java.util.stream.Collectors;

public class OrderBookConverter {
    public static OrderBook fromResponse(GetOrdersBySymbolResponse ordersResponse){
        return OrderBook.builder()
                .symbol(ordersResponse.getSymbol())
                .asks(ordersResponse.getAsks().stream().map(OrderBookConverter::fromBlockchainOp).collect(Collectors.toList()))
                .bids(ordersResponse.getBids().stream().map(OrderBookConverter::fromBlockchainOp).collect(Collectors.toList()))
                .build();
    };

    private static Operation fromBlockchainOp(GetOrdersBySymbolResponse.Op op){
        return Operation.builder()
                .price(op.getPx())
                .quantity(op.getQty())
                .build();
    }
}
