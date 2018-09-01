package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.StationDevice;

/**
 * 设备数据操作
 * @author aohanhe
 *
 */
@Repository
public interface DeviceRepository extends JpaRepository<StationDevice, Integer>,QuerydslPredicateExecutor<StationDevice>{

}
