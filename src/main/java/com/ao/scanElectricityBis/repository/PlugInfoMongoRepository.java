package com.ao.scanElectricityBis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.PlugInfoMongoEntity;

@Repository
public interface PlugInfoMongoRepository extends MongoRepository<PlugInfoMongoEntity, Integer>{
	
	

}
