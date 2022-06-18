package com.tradesoft.exchangedataaggregation.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AggregatedBook {
    String symbol;
    AggregatedOperation averageBid;
    AggregatedOperation averageAsk;
}
