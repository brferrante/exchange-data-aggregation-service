package com.tradesoft.exchangedataaggregation.domain.model;

import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class AggregatedOperation {
    Double priceAverage;
    Double totalQuantity;
}
