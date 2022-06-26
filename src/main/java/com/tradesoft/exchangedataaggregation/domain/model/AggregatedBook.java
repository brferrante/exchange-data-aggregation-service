package com.tradesoft.exchangedataaggregation.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregatedBook {
    String symbol;
    AggregatedOperation averageBid;
    AggregatedOperation averageAsk;
}
