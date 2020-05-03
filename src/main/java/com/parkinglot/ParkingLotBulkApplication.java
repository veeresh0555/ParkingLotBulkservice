package com.parkinglot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.parkinglot.config.RibbonConfiguration;
@EnableScheduling
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="parkinglotbulk",configuration = RibbonConfiguration.class)
public class ParkingLotBulkApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingLotBulkApplication.class, args);
	}
	
	@Autowired
	JobLauncher jLauncher;
	
	@Autowired
	Job job;
	
	@Scheduled(fixedDelay = 50000000)//If Required
	public void performJob() throws Exception {
		JobParameters params=new JobParametersBuilder().addString("JobId", String.valueOf(System.currentTimeMillis())).toJobParameters();
		jLauncher.run(job, params);
	}


}
