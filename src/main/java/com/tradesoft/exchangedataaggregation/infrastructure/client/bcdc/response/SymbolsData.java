package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonDeserialize
@JsonIgnoreProperties
@NoArgsConstructor
public class SymbolsData {
    @JsonProperty("base_currency")
    private String baseCurrency;
    @JsonProperty("base_currency_scale")
    private Integer baseCurrencyScale;
    @JsonProperty("counter_currency")
    private String counterCurrency;
    @JsonProperty("counter_currency_scale")
    private Integer counterCurrencyScale;
    @JsonProperty("min_price_increment")
    private Integer minPriceIncrement;
    @JsonProperty("min_price_increment_scale")
    private Integer minPriceIncrementScale;
    @JsonProperty("min_order_size")
    private Long minOrderSize;
    @JsonProperty("min_order_size_scale")
    private Integer minOrderSizeScale;
    @JsonProperty("max_order_size")
    private Integer maxOrderSize;
    @JsonProperty("max_order_size_scale")
    private Integer maxOrderSizeScale;
    @JsonProperty("lot_size")
    private Integer lotSize;
    @JsonProperty("lot_size_scale")
    private Integer lotSizeScale;
    @JsonProperty("status")
    private String status;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("auction_price")
    private Double auctionPrize;
    @JsonProperty("auction_size")
    private Double auctionSize;
    @JsonProperty("auction_time")
    private String auctionTime;
    @JsonProperty("imbalance")
    private Double imbalance;
}
