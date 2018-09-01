package com.ao.scanElectricityBis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.ao.scanElectricityBis.entity.UserInfo;


@Repository
public interface UserRepository extends  JpaRepository<UserInfo, Integer>,QuerydslPredicateExecutor<UserInfo>{

}
