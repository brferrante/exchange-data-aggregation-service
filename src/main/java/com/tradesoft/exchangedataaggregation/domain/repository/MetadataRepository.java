package com.tradesoft.exchangedataaggregation.domain.repository;

import com.tradesoft.exchangedataaggregation.infrastructure.db.ExchangeMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MetadataRepository extends MongoRepository<ExchangeMetadata, String> {
    ExchangeMetadata findByExchangeName(String exchangeName);
    void exchangeMetadata(String exchangeName, Map<String, Object> exchangeMetadata);
}
