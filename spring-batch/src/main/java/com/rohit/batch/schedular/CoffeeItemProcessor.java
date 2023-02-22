package com.rohit.batch.schedular;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.ComponentScan;

import com.rohit.batch.pojo.Coffee;

@ComponentScan
public class CoffeeItemProcessor implements ItemProcessor<Coffee, Coffee>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CoffeeItemProcessor.class);

	@Override
	public Coffee process(Coffee coffee) throws Exception {
		
		String brand = coffee.getBrand().toUpperCase();
        String origin = coffee.getOrigin().toUpperCase();
        String chracteristics = coffee.getCharacteristics().toUpperCase();
        //long coffeeId = coffee.getCoffeeId();

        Coffee transformedCoffee = new Coffee(brand, origin, chracteristics);
        LOGGER.info("Converting ( {} ) into ( {} )", coffee, transformedCoffee);

        return transformedCoffee;
	}

}
