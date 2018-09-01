package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.StationDevice;

/**
 * �豸���ݲ���
 * @author aohanhe
 *
 */
@Repository
public interface DeviceRepository extends JpaRepository<StationDevice, Integer>,QuerydslPredicateExecutor<StationDevice>{

}
