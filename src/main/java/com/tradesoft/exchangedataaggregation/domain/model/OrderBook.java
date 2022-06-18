package com.tradesoft.exchangedataaggregation.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderBook {
    String symbol;
    List<Operation> asks;
    List<Operation> bids;

}
