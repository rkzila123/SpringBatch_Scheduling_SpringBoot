package com.rohit.batch.schedular;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class BatchScheduler {
	
	@Autowired
	@Qualifier("DemoJob")
	private Job demoJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	 @Scheduled(cron  = "${demo.scheduler.timer}")
	 public void doBatchTask() throws InterruptedException {
	        log.info("doBatchTask");
	        JobExecution job=null;
	        try {
				JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).addLong("jobId", Long.parseLong("12345"))
						.toJobParameters();
				 job = jobLauncher.run(demoJob, jobParameters);
				 
				 log.info("Demo job executed with status:: {}",job.getExitStatus().getExitCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
	 
	    }

}
