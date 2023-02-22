package com.rohit.batch.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coffee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeEntity {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long coffeeId;
    
	private String brand;
    private String origin;
    private String characteristics;

}
