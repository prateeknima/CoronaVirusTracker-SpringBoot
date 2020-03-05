package com.prateek.TrackCoronaVirus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrackCoronaVirusApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackCoronaVirusApplication.class, args);
	}

}
