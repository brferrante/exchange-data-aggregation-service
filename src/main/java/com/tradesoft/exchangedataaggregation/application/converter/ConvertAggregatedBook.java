package com.tradesoft.exchangedataaggregation.application.converter;

import com.tradesoft.exchangedataaggregation.application.dto.OrderBooksResponseDto;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;

import java.util.List;

public class ConvertAggregatedBook {
    public static OrderBooksResponseDto fromBooks (List<AggregatedBook> books){
      return  OrderBooksResponseDto.builder()
                .aggregatedBooks(books)
                .build();

    }
}
