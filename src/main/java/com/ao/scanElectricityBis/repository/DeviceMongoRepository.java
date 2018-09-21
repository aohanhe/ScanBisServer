package com.ao.scanElectricityBis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.DeviceMongoEntry;

/**
 * �豸��mongo�����ֿ�
 * @author aohanhe
 *
 */
@Repository
public interface DeviceMongoRepository extends MongoRepository<DeviceMongoEntry, Integer>{

}
