package com.tradesoft.exchangedataaggregation.application.converter;

import com.tradesoft.exchangedataaggregation.application.dto.AggregatedSymbolOperationsDto;
import com.tradesoft.exchangedataaggregation.application.dto.OrderBooksResponseDto;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedOperationSearch;

import java.util.List;

public class ConvertAggregatedBook {
    public static OrderBooksResponseDto fromBooks (List<AggregatedBook> books){
      return  OrderBooksResponseDto.builder()
                .aggregatedBooks(books)
                .build();

    }
    public static AggregatedSymbolOperationsDto fromSymbolOperation (AggregatedOperationSearch symbolOperation){
        return  AggregatedSymbolOperationsDto.builder()
                .symbol(symbolOperation.getSymbol())
                .operation(symbolOperation.getOperation())
                .build();

    }
}
