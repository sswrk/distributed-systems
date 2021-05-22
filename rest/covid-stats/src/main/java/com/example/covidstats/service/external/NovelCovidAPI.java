package com.example.covidstats.service.external;

import com.example.covidstats.dto.external.CovidCountryHistoricalDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NovelCovidAPI {

    RestTemplate restTemplate = new RestTemplate();

    String baseUrl = "https://corona.lmao.ninja/v2/";

    public CovidCountryHistoricalDTO getCountryHistoricalData(String country, long lastDays){
        StringBuilder urlBuilder = new StringBuilder(baseUrl)
                .append("historical/")
                .append(country)
                .append("?")
                .append("lastdays=")
                .append(lastDays);

        return restTemplate.getForObject(urlBuilder.toString(), CovidCountryHistoricalDTO.class);

    }

}
