package com.example.covidstats.service;

import com.example.covidstats.dto.CovidCountryAggregateDTO;
import com.example.covidstats.dto.external.CountryDTO;
import com.example.covidstats.dto.external.CovidCountryHistoricalDTO;
import com.example.covidstats.service.external.NovelCovidAPI;
import com.example.covidstats.service.external.RestCountriesAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
public class StatsAggregateService {

    NovelCovidAPI novelCovidAPI;
    RestCountriesAPI restCountriesAPI;

    public CovidCountryAggregateDTO getCovidCountryStats(LocalDate fromDate, List<String> countries) throws InterruptedException {
        long lastDays = ChronoUnit.DAYS.between(fromDate, LocalDate.now()) + 1;

        List<Integer> newCases = Collections.synchronizedList(new ArrayList<>());
        List<Integer> newDeaths = Collections.synchronizedList(new ArrayList<>());
        List<Integer> newRecoveries = Collections.synchronizedList(new ArrayList<>());
        Map<String, Integer> maxDailyCasesPeaks = new ConcurrentHashMap<>();
        Map<String, Integer> newCasesPerCountry = new ConcurrentHashMap<>();
        Map<String, Object> maxCasesCountryInfo = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(countries.size());
        List<Callable<Object>> countryTasks = new ArrayList<>(countries.size());
        for(String country : countries) {
            countryTasks.add(Executors.callable(() -> {
                CovidCountryHistoricalDTO covidCountryHistoricalDTO = novelCovidAPI
                        .getCountryHistoricalData(country, lastDays);
                List<Integer> totalCountryCases = new ArrayList<>(
                        covidCountryHistoricalDTO.getTimeline().get("cases").values());
                List<Integer> totalCountryDeaths = new ArrayList<>(
                        covidCountryHistoricalDTO.getTimeline().get("deaths").values());
                List<Integer> totalCountryRecoveries = new ArrayList<>(
                        covidCountryHistoricalDTO.getTimeline().get("recovered").values());
                for(int i=(int)lastDays-1; i>0; i--){
                    totalCountryCases.set(i, totalCountryCases.get(i) - totalCountryCases.get(i - 1));
                    totalCountryDeaths.set(i, totalCountryDeaths.get(i) - totalCountryDeaths.get(i - 1));
                    totalCountryRecoveries.set(i, totalCountryRecoveries.get(i) - totalCountryRecoveries.get(i - 1));
                }
                totalCountryCases.remove(0);
                totalCountryDeaths.remove(0);
                totalCountryRecoveries.remove(0);
                newCases.addAll(totalCountryCases);
                newDeaths.addAll(totalCountryDeaths);
                newRecoveries.addAll(totalCountryRecoveries);
                maxDailyCasesPeaks.put(
                        country,
                        totalCountryCases
                                .stream()
                                .max(Comparator.naturalOrder()).orElse(0)
                );
                newCasesPerCountry.put(
                        country,
                        totalCountryCases
                                .stream()
                                .mapToInt(i -> i)
                                .sum()
                );
            }));
        }
        executorService.invokeAll(countryTasks);

        long totalNewCases = newCases.stream()
                .mapToLong(l -> l)
                .sum();
        long totalNewDeaths = newDeaths.stream()
                .mapToLong(l -> l)
                .sum();
        long totalNewRecoveries = newRecoveries.stream()
                .mapToLong(l -> l)
                .sum();

        double averageDailyNewCases = newCases.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0D);
        double averageDailyNewDeaths = newDeaths.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0D);
        double averageDailyNewRecoveries = newRecoveries.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0D);

        CountryDTO countryDTO = restCountriesAPI.getCountryData(
                Collections.max(newCasesPerCountry.entrySet(), Map.Entry.comparingByValue()).getKey());
        maxCasesCountryInfo.put("capital", countryDTO.getCapital());
        maxCasesCountryInfo.put("population", countryDTO.getPopulation());
        maxCasesCountryInfo.put("region", countryDTO.getRegion());
        maxCasesCountryInfo.put("subregion", countryDTO.getSubregion());
        maxCasesCountryInfo.put("area", countryDTO.getArea());

        return new CovidCountryAggregateDTO(
                totalNewCases,
                totalNewDeaths,
                totalNewRecoveries,
                averageDailyNewCases,
                averageDailyNewDeaths,
                averageDailyNewRecoveries,
                maxDailyCasesPeaks,
                newCasesPerCountry,
                maxCasesCountryInfo
        );
    }
}
