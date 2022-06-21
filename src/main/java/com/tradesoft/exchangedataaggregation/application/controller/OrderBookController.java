package com.tradesoft.exchangedataaggregation.application.controller;

import com.tradesoft.exchangedataaggregation.application.converter.ConvertAggregatedBook;
import com.tradesoft.exchangedataaggregation.application.dto.OrderBooksResponseDto;
import com.tradesoft.exchangedataaggregation.domain.service.OrderBooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderBookController {
    private final OrderBooksService orderBooksService;
    /*
    -    exchanges/{exchange-name}/order-books
-    exchanges/{exchange-name}/order-books/{symbol}/ask
-    exchanges/{exchange-name}/order-books/{symbol}/bid

-    exchanges/{exchange-name}/order-books?orderBy=asc
-    exchanges/{exchange-name}/order-books/{symbol}/ask?orderBy=asc
-    exchanges/{exchange-name}/order-books/{symbol}/bid?orderBy=asc
@PathVariable(exchange-name) String exName, @RequestParam(orderBy) String orderBy
@RequestParam(required = false)
     */
    @Operation(summary = "Provides the quantity and price average of the order book (asks and bids) for each symbol")
    @GetMapping("exchanges/{exchange-name}/order-books")
    public ResponseEntity<OrderBooksResponseDto> listAll(@PathVariable("exchange-name") String exchangeName) {
        try {
            return ResponseEntity.ok(ConvertAggregatedBook.fromBooks(orderBooksService.getOrderBook()));
        } catch (Exception e){
            return null;
        }
        }
    //TODO: Add logic for exchange-name to work with different exchange names.

}
