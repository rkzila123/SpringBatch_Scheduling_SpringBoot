package com.rohit.batch.schedular;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.rohit.batch.entity.CoffeeEntity;
import com.rohit.batch.pojo.Coffee;
import com.rohit.batch.repository.CoffeeRepo;

import lombok.extern.log4j.Log4j2;

@ComponentScan
@Log4j2
public class CoffeeItemWritter implements ItemWriter<Coffee>{
	
	@Autowired
	private CoffeeRepo coffeeRepo;

	@Override
	public void write(List<? extends Coffee> items) throws Exception {
		
		List<CoffeeEntity> coffeeEntityList= new ArrayList<>();
		
		for (Coffee coffee : items) {
			CoffeeEntity coffeeEntity = new CoffeeEntity();
			BeanUtils.copyProperties(coffeeEntity, coffee);
			coffeeEntityList.add(coffeeEntity);
		}
		if(CollectionUtils.isNotEmpty(coffeeEntityList)) {
			coffeeRepo.saveAll(coffeeEntityList);
		}else {
			log.info("coffeeEntityList is empty ::");
		}
	}

}
