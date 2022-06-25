package com.tradesoft.exchangedataaggregation.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AggregatedOperationSearch {
    String symbol;
    List<Operation> operation;

}
