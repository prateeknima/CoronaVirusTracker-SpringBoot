package com.prateek.TrackCoronaVirus.services;


import com.prateek.TrackCoronaVirus.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    public static String data_url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    private List<LocationStats> allStats = new ArrayList<>();
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchCoronaData() throws IOException, InterruptedException {
        List<LocationStats> newData = new ArrayList<>();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(data_url))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

        StringReader body = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(body);
        for (CSVRecord record : records) {
            LocationStats stats = new LocationStats();
            stats.setCountry(record.get("Country/Region"));
            stats.setState(record.get("Province/State"));
            int difference = Integer.parseInt(record.get(record.size()-1)) - Integer.parseInt(record.get(record.size()-2));
            stats.setDifference(difference);
            stats.setTotalCases(Integer.parseInt(record.get(record.size()-1)));
            newData.add(stats);
        }
        allStats = newData;
    }
}
