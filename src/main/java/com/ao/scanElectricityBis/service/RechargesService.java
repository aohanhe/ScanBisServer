package com.ao.scanElectricityBis.service;

import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.AccountRecharge;
import com.ao.scanElectricityBis.repository.AccountRechargeRepository;

/**
 * ��ֵ������
 * @author aohanhe
 *
 */
@Service
public class RechargesService extends BaseService<AccountRecharge, AccountRechargeRepository>{
	
	@Override
	protected void onDeleteItemById(int id) throws ScanElectricityException {
		throw new ScanElectricityException("��֧��ֱ��ɾ����ֵ��¼");	
	}

}
