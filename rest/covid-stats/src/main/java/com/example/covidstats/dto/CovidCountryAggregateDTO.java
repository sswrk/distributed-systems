package com.example.covidstats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CovidCountryAggregateDTO implements Serializable {
    private long totalNewCases;
    private long totalNewDeaths;
    private long totalNewRecovered;
    private double averageDailyCases;
    private double averageDailyDeaths;
    private double averageDailyRecovered;
    private Map<String, Integer> maxDailyCasesPeaks;
    private Map<String, Integer> newCasesPerCountry;
    private Map<String, Object> maxCasesCountryInfo;
}
