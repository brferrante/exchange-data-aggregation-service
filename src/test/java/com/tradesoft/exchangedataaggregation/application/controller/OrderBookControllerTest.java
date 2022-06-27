package com.tradesoft.exchangedataaggregation.application.controller;

import com.tradesoft.exchangedataaggregation.application.converter.ConvertAggregatedBook;
import com.tradesoft.exchangedataaggregation.application.dto.OrderBooksResponseDto;
import com.tradesoft.exchangedataaggregation.domain.model.OperationType;
import com.tradesoft.exchangedataaggregation.domain.service.OrderBooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookControllerTest {

    private final Long defaultPage =1L;
    private final Long defaultSize =20L;
    @Autowired
    OrderBooksService orderBooksService;




}