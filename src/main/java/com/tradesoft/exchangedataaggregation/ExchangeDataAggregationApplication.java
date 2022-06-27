package com.tradesoft.exchangedataaggregation;

import com.tradesoft.exchangedataaggregation.domain.repository.MetadataRepository;
import com.tradesoft.exchangedataaggregation.infrastructure.db.ExchangeMetadata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MetadataRepository.class)
public class ExchangeDataAggregationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeDataAggregationApplication.class, args);
	}

}
