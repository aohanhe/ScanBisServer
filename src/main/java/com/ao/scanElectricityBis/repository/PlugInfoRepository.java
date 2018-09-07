package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.StationPlugInfo;

@Repository
public interface PlugInfoRepository extends JpaRepository<StationPlugInfo, Integer>,QuerydslPredicateExecutor<StationPlugInfo>{

}
