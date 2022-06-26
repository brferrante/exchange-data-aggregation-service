package com.tradesoft.exchangedataaggregation.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tradesoft.exchangedataaggregation.domain.model.AggregatedBook;
import lombok.Builder;
import lombok.Value;

import java.util.List;
@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderBooksResponseDto {
    List<AggregatedBook> aggregatedBooks;
}
