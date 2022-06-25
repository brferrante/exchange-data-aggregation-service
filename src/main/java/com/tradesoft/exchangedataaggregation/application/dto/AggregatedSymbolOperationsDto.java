package com.tradesoft.exchangedataaggregation.application.dto;

import com.tradesoft.exchangedataaggregation.domain.model.Operation;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AggregatedSymbolOperationsDto {
    String symbol;
    List<Operation> operation;
}
