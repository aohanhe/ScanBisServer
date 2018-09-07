package com.ao.scanElectricityBis.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.StationMongoEntry;

@Repository
public interface StationEntryMongoRepository extends MongoRepository<StationMongoEntry,Integer>  {
	
	@Query("{pos: {\r\n" + 
			"     $near: {\r\n" + 
			"       $geometry: {\r\n" + 
			"          type: \"Point\" ,\r\n" + 
			"          coordinates: [ ?0, ?1 ]\r\n" +  
			"       },\r\n" + 
			"       $maxDistance: 5000       \r\n" + 
			"     }\r\n" + 
			"   }\r\n" + 
			"}")
	public List<StationMongoEntry> findByPos(double lon,double lat);	
	
}
