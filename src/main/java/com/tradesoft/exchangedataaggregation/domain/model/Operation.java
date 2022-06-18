package com.tradesoft.exchangedataaggregation.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Operation {
    Double price;
    Double quantity;

}
