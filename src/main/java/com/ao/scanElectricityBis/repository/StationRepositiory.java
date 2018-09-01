package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.StationStationInfo;

/**
 * 站场数据库操作对象
 * @author aohanhe
 *
 */
@Repository
public interface StationRepositiory extends JpaRepository<StationStationInfo, Integer>,QuerydslPredicateExecutor<StationStationInfo>{

}
