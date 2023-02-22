package com.rohit.batch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {
	
	//private long coffeeId;
	private String brand;
    private String origin;
    private String characteristics;

}
