package com.example.covidstats.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CovidCountryHistoricalDTO implements Serializable {
    private String country;
    private List<String> provinces;
    private Map<String, Map<String, Integer>> timeline;
}
