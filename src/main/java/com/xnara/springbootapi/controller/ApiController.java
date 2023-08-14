package com.xnara.springbootapi.controller;

import com.xnara.springbootapi.model.CombinedDataResponse;
import com.xnara.springbootapi.model.CustomerRequest;
import com.xnara.springbootapi.service.DataService;

import io.swagger.annotations.ApiOperation;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final DataService dataService;
    private static final Logger logger = Logger.getLogger(ApiController.class);


    public ApiController(DataService dataService) {
        this.dataService = dataService;
    }

    @ApiOperation("Combine data for a customer")
    @PostMapping("/combine-data")
    public ResponseEntity<CombinedDataResponse> combineData(@RequestBody CustomerRequest request) {
    	
    	logger.info("Received request for customer ID: "+ request.getCustomer_id());
        try {
            CombinedDataResponse response = dataService.combineData(request.getCustomer_id());

            logger.info("Sending response for customer ID: "+ request.getCustomer_id());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	logger.error("Error occurred while processing request: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
