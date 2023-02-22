package com.rohit.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.batch.entity.CoffeeEntity;

@Repository
public interface CoffeeRepo extends JpaRepository<CoffeeEntity,Long>{

}
