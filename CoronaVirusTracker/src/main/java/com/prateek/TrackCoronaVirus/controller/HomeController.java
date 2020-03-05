package com.prateek.TrackCoronaVirus.controller;


import com.prateek.TrackCoronaVirus.models.LocationStats;
import com.prateek.TrackCoronaVirus.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;
    @GetMapping("/")
    public String displayData(Model model){
        List<LocationStats> statsList = coronaVirusDataService.getAllStats();
        int totalCount = statsList.stream().mapToInt(stat -> stat.getTotalCases()).sum();
        int totalNewCases = statsList.stream().mapToInt(stat -> stat.getDifference()).sum();

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("totalNewCases",totalNewCases);
        model.addAttribute("statsList",statsList);
        return "displayData";
    }
}
