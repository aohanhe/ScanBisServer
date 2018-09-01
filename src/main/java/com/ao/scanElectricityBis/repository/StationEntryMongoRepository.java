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
	
	/**
	 * 如果用eclipse这里会有一个错误提示，但是是可以编译过去的，是因为如果使用了spring的插件会去检查，我们的语句中的变量是不是在实体中存在
	 * 这里使用的是mongdb 原始的字段名  _id,没有在实体对象中存在，所以校验会出错，但实际运行是没有问题的
	 * @param id
	 */
	@DeleteQuery("{_id:?0}")
	public void deleteUserId(int id);
}
