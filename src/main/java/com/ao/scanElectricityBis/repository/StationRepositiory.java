package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.StationStationInfo;

/**
 * վ�����ݿ��������
 * @author aohanhe
 *
 */
@Repository
public interface StationRepositiory extends JpaRepository<StationStationInfo, Integer>,QuerydslPredicateExecutor<StationStationInfo>{

}
