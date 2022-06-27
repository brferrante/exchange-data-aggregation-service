package com.tradesoft.exchangedataaggregation.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.repository.MetadataRepository;
import com.tradesoft.exchangedataaggregation.infrastructure.db.ExchangeMetadata;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MetadataController {
    @Autowired
    private MetadataRepository metadataRepository;

    // 2 operations to be defined, get and post metadata, to mongoDB collection
    // Get is by exchange name as key.
    // Post is an upsert by exchange name as key.
//    @Operation(summary = "Gets metadata by exchange name")
//    @GetMapping("exchanges/{exchange-name}/metadata")
//    public List<ExchangeMetadata> getAll(@PathVariable("exchange-name") String exchangeName){
//        return metadataRepository.findAll();
//    }

    @Operation(summary = "Gets metadata by exchange name")
    @GetMapping("exchanges/{exchange-name}/metadata")
    public ExchangeMetadata getMetadata(@PathVariable("exchange-name") String exchangeName){
        return metadataRepository.findById(exchangeName).orElse(ExchangeMetadata.builder().build());
    }

    @Operation(summary = "Uploads metadata by exchange name through a .csv file")
    @PostMapping(value = "exchanges/{exchange-name}/metadata", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveMetadata(@PathVariable("exchange-name") String exchangeName,
                                         @RequestPart("file") MultipartFile multipartFile) throws IOException {

        InputStreamReader reader = new InputStreamReader(multipartFile.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(exchangeName)
                .withFirstRecordAsHeader()
                .parse(reader);
        Map<String,Object> mappedRecord = new HashMap<>();
        for(CSVRecord record : records){
            mappedRecord.putAll(record.toMap());
            metadataRepository.save(
                    ExchangeMetadata.builder().exchangeMetadata(mappedRecord)
                            .exchangeName(exchangeName).build());
        }
    }
}
