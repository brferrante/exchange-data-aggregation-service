package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Value;


import java.util.Map;
@Data
@Value
@JsonDeserialize
@JsonIgnoreProperties
public class GetSymbolResponse {

    Map<String,SymbolsData> response;

    public GetSymbolResponse(Map<String, SymbolsData> response) {
        this.response = response;
    }
}
