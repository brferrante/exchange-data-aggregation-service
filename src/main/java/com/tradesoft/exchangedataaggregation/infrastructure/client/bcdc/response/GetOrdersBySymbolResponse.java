package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response;

import lombok.Data;

import java.util.List;

@Data
public class GetOrdersBySymbolResponse {
    String symbol;
    List<Op> bids;
    List<Op> asks;

    @Data
    public static class Op{
        Double px;
        Double qty;
    }
}
