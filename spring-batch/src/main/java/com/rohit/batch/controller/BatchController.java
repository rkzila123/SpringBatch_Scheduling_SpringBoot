package com.rohit.batch.controller;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.batch.pojo.CommonInput;
import com.rohit.batch.pojo.CommonResponseModel;

@RestController
public class BatchController {
	
	@Autowired
	@Qualifier("DemoJob")
	private Job demoJob;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@CrossOrigin
	@PostMapping(value = "/invokeDemoJob", consumes = { "application/JSON" })
	public ResponseEntity<CommonResponseModel> processClosingExpedite(@RequestBody CommonInput commonInputs){
		CommonResponseModel commonResponseModel = new CommonResponseModel();
		JobExecution job = null;
		ResponseEntity<CommonResponseModel> rmJobResponse = null;
		
		try {
			JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).addLong("jobId", Long.parseLong(commonInputs.getValue()))
					.toJobParameters();
			job = jobLauncher.run(demoJob, jobParameters);
			commonResponseModel.setStatus("Success");
			commonResponseModel.setMessage("Demo job executed with status::"+job.getExitStatus().getExitCode()); 
			rmJobResponse = new ResponseEntity<>(commonResponseModel, HttpStatus.OK); 
		}catch(Exception e) {
			commonResponseModel.setStatus("failure");
			commonResponseModel.setMessage("Job failed with error:"+e.getMessage()); 
			e.printStackTrace();
			rmJobResponse = new ResponseEntity<>(commonResponseModel, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		 
		return rmJobResponse;
	}

}
