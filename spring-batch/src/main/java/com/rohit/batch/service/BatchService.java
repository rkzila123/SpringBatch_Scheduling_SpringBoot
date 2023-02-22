package com.rohit.batch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.batch.entity.CoffeeEntity;
import com.rohit.batch.repository.CoffeeRepo;

@Service
public class BatchService {
	
	@Autowired
	private CoffeeRepo coffeeRepo;
	
	
	public List<CoffeeEntity> getAllDetails() {
		return coffeeRepo.findAll();
	}

}
