package com.example.covidstats.controller;

import com.example.covidstats.dto.CovidCountryAggregateDTO;
import com.example.covidstats.service.StatsAggregateService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FormController {

    StatsAggregateService statsAggregateService;

    @GetMapping("/stats")
    public CovidCountryAggregateDTO getStats(
            @RequestParam("days") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate,
            @RequestParam("countries") List<String> countries
    ) throws InterruptedException {
        return statsAggregateService.getCovidCountryStats(fromDate, countries);
    }
}
