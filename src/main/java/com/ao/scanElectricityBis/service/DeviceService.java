package com.ao.scanElectricityBis.service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.entity.StationDevice;
import com.ao.scanElectricityBis.repository.DeviceRepository;

import ao.jpaQueryHelper.BaseJpaQueryBean;
import ao.jpaQueryHelper.JpaQueryHelper;
import ao.jpaQueryHelper.JpaQueryHelperException;

/**
 * �豸����
 * @author aohanhe
 *
 */
@Service
public class DeviceService extends BaseService<StationDevice, DeviceRepository>{
	@Autowired
	private EntityManager em;
	
	@Override
	public TypedQuery<StationDevice> findAllByQueryBean(BaseJpaQueryBean query, Class<StationDevice> reClassType)
			throws JpaQueryHelperException {
		
		return JpaQueryHelper.createQueryFromBean(em, query, null, list->{
			
		}, reClassType);
	}

}
