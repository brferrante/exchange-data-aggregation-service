package com.tradesoft.exchangedataaggregation.infrastructure.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection="metadata")
@Value
@Builder
@AllArgsConstructor
public class ExchangeMetadata {
    @Id
    String exchangeName;
    Map<String,Object> exchangeMetadata;
}
