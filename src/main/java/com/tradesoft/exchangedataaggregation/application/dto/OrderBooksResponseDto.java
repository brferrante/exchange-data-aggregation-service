package com.tradesoft.exchangedataaggregation.application.dto;

import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;
import lombok.Builder;
import lombok.Value;

import java.util.List;
@Value
@Builder
public class OrderBooksResponseDto {
    List<AggregatedBook> aggregatedBooks;
}
