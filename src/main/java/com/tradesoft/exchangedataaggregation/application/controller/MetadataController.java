package com.tradesoft.exchangedataaggregation.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tradesoft.exchangedataaggregation.domain.repository.MetadataRepository;
import com.tradesoft.exchangedataaggregation.infrastructure.db.ExchangeMetadata;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<ExchangeMetadata> getMetadata(@PathVariable("exchange-name") String exchangeName){
        return ResponseEntity.ok(metadataRepository.findById(exchangeName)
                .orElseThrow(()-> new NotFoundException("Requested exchange records not found")));
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

        Tika tika = new Tika();
        if(!tika.detect(multipartFile.getBytes()).equalsIgnoreCase("TEXT/PLAIN")){
            throw new InvalidPropertiesFormatException("Not a .csv file!");
        }

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
        reader.close();

    }

    @ControllerAdvice
    public class Handler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handle(Exception ex,
                                             HttpServletRequest request, HttpServletResponse response) {
            if (ex instanceof NotFoundException || ex instanceof InvalidPropertiesFormatException) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
