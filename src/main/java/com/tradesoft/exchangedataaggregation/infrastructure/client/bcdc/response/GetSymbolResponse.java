package com.tradesoft.exchangedataaggregation.infrastructure.client.bcdc.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@JsonDeserialize
@JsonIgnoreProperties
@NoArgsConstructor
public class GetSymbolResponse {

    Map<String,SymbolsData> response;

}
