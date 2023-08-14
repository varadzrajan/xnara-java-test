package com.xnara.springbootapi.service;
import com.xnara.springbootapi.exception.DataNotFoundException;
import com.xnara.springbootapi.exception.ExternalApiException;
import com.xnara.springbootapi.model.CombinedDataResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DataService {

    private final RestTemplate restTemplate;
    private static final Logger logger = Logger.getLogger(DataService.class);

    @Value("${pack1.url}")
    private String pack1Url;

    @Value("${pack2.url}")
    private String pack2Url;

    @Autowired
    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> fetchDataFromApi(String apiUrl) {
        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(apiUrl, String[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public CombinedDataResponse combineData(int customerId) {

    	  try {
              List<String> pack1Data = fetchDataFromApi(pack1Url);
              List<String> pack2Data = fetchDataFromApi(pack2Url);

              logger.info("Customer ID requested: " + customerId);
              if (pack1Data.isEmpty() || pack2Data.isEmpty()) {
                  throw new DataNotFoundException("Data not found for customer ID: " + customerId);
              }

              return new CombinedDataResponse(
                      "1",
                      customerId,
                      pack1Data,
                      pack2Data
              );
          } catch (Exception e) {
              logger.error("Error occurred while processing request: " + e.getMessage(), e);
              throw new ExternalApiException("Error calling external API", e);
          }
      }
}
