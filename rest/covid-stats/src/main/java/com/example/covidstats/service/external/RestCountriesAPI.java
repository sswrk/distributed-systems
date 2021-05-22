package com.example.covidstats.service.external;

import com.example.covidstats.dto.external.CountryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestCountriesAPI {

    RestTemplate restTemplate = new RestTemplate();

    String baseUrl = "https://restcountries.eu/rest/v2/";

    public CountryDTO getCountryData(String country){
        StringBuilder urlBuilder = new StringBuilder(baseUrl)
                .append("name/")
                .append(country)
                .append("?fulltext=true")
                .append("?fields=capital;population;region;subregion;area");

        CountryDTO[] countryDTOArray = restTemplate.getForEntity(urlBuilder.toString(), CountryDTO[].class).getBody();
        return countryDTOArray==null ? null : countryDTOArray[0];
    }
}
