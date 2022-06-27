package com.tradesoft.exchangedataaggregation.application.controller;

import com.tradesoft.exchangedataaggregation.application.converter.ConvertAggregatedBook;
import com.tradesoft.exchangedataaggregation.application.dto.AggregatedSymbolOperationsDto;
import com.tradesoft.exchangedataaggregation.application.dto.OrderBooksResponseDto;
import com.tradesoft.exchangedataaggregation.domain.model.OperationType;
import com.tradesoft.exchangedataaggregation.domain.service.OrderBooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderBookController {
    private final OrderBooksService orderBooksService;
    private final Long defaultPage =1L;
    private final Long defaultSize =20L;
    //TODO add Page and Size parameters with springconfig
    /**
     * Fetches the averages for all available specified exchange symbols OR the specified symbol.
     * <p>
     * Pagination and results per page can be specified via their respective fields
     * Alphabetical listing available by enabling the order-alphabetically flag.
     * Can narrow requested operation by operation type if desired, default ALL will fetch bids and asks.
     *
     * @param exchangeName specifies the desired exchange (to be implemented at a later time, for now it defaults to Blockchain.com
     * @param page specifies pagination for result set
     * @param size specifies amounts of replies per page
     * @param symbol specifies if the operation is for a particular currency
     * @param operationType specifies if a particular operation type is requested
     * @param order specifies if the list is sorted alphabetically
     */
    @Operation(summary = "Provides the quantity and price average of the order book (asks and bids) for each symbol")
    @GetMapping("exchanges/{exchange-name}/order-books")
    public ResponseEntity<OrderBooksResponseDto> listAll(@PathVariable("exchange-name") String exchangeName,
                                                         @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                                                         @RequestParam(value = "size", required = false, defaultValue = "20") Long size,
                                                         @RequestParam(value="order-alphabetically", defaultValue = "false") Boolean order,
                                                         @RequestParam(value="symbol", required = false) Optional<String> symbol,
                                                         @RequestParam(value = "operation-type", defaultValue = "ALL") OperationType operationType) {
        try {
            var actualPage = Optional.ofNullable(page)
                    .orElse(defaultPage);
            var actualSize = Optional.ofNullable(size)
                    .orElse(defaultSize);

            return ResponseEntity.ok(ConvertAggregatedBook.fromBooks(orderBooksService.getOrderBook(exchangeName, actualPage, actualSize, order, symbol, operationType)));

        } catch (Exception e){
            return null;
        }
    }

    @Operation(summary = "Provides the alphabetical list for all available symbols")
    @GetMapping("exchanges/{exchange-name}/symbols-list")
    public ResponseEntity<List<String>> getAllSymbols(@PathVariable("exchange-name") String exchangeName) {
        try {
            return ResponseEntity.ok(orderBooksService.getAllSymbols());
        } catch (Exception e){
            return null;
        }
    }

}
