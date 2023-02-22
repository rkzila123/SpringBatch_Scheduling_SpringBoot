package com.rohit.batch.schedular;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rohit.batch.entity.CoffeeEntity;
import com.rohit.batch.service.BatchService;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	@Autowired
	private BatchService batchService;
	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		Long jobId=jobExecution.getJobParameters().getLong("jobId");
		LOGGER.info("beforeStep jobId ::{}" ,jobId);
		jobExecution.getExecutionContext().put("jobId", jobId);
		
	}
	
	@Override
    public void afterJob(JobExecution jobExecution) {
		List<CoffeeEntity> coffeeListFromDB= new ArrayList<>();
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("!!! JOB FINISHED! Time to verify the results");
            Long jobId = (Long) jobExecution.getExecutionContext().get("jobId");
            LOGGER.info("jobId :: {}" ,jobId);
             coffeeListFromDB= batchService.getAllDetails();
            LOGGER.info("Number of records in DB {}" , coffeeListFromDB.size());
        }
    }

}
