package com.example.covidstats.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDTO implements Serializable {
    private String capital;
    private Integer population;
    private String region;
    private String subregion;
    private Double area;
}
