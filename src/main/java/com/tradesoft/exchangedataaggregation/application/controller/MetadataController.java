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
import org.webjars.NotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class MetadataController {
    @Autowired
    private MetadataRepository metadataRepository;

    /**
     * Allows the read of metadata records in DB through exchange name
     * <p>
     *
     * @param exchangeName specifies the desired exchange which metadata to consult.
     */
    @CrossOrigin
    @Operation(summary = "Gets metadata by exchange name")
    @GetMapping("exchanges/{exchange-name}/metadata")
    public ExchangeMetadata getMetadata(@PathVariable("exchange-name") String exchangeName){
        return metadataRepository.findById(exchangeName)
                .orElseThrow(()-> new NotFoundException("Requested exchange records not found"));
    }

    /**
     * Allows the loading of metadata records in DB through exchange name and a .csv file
     * <p>
     *
     * @param exchangeName specifies the desired exchange which metadata to upload.
     * @param multipartFile indicates the uploaded .csv file
     */
    @CrossOrigin
    @Operation(summary = "Uploads metadata by exchange name through a .csv file")
    @PostMapping(value = "exchanges/{exchange-name}/metadata", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveMetadata(@PathVariable("exchange-name") String exchangeName,
                                         @RequestPart("file") MultipartFile multipartFile) throws IOException {

        InputStreamReader reader = new InputStreamReader(multipartFile.getInputStream());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(exchangeName)
                .withFirstRecordAsHeader()
                .parse(reader);
        List<Map<String,Object>>mappedRecord = new ArrayList<>();
        for(CSVRecord record : records){
            Map<String,Object> recordMap= new HashMap<>();
            recordMap.putAll(record.toMap());
            mappedRecord.add(recordMap);
        }
        metadataRepository.save(
                ExchangeMetadata.builder().exchangeMetadata(mappedRecord)
                        .exchangeName(exchangeName).build());
    }
}
