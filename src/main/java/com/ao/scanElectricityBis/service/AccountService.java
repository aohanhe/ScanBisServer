package com.ao.scanElectricityBis.service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.BaseAccount;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.repository.AccountRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * �ʺŷ�����
 * @author aohanhe
 *
 */
@Service
public class AccountService extends BaseService<BaseAccount, AccountRepository>{
	@Autowired
	private EntityManager em;
	
	/**
	 * querydsl��������
	 */
	private JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}

	/**
	 * ����û��ʺ������Ƿ���ȷ
	 * @param userName
	 * @param pwd
	 * @return �������ȷ���ؿ�
	 * @throws ScanElectricityException
	 */
	public BaseAccount checkUserPwd(String userName,String pwd) throws ScanElectricityException {
		
		if(Strings.isBlank(userName)) throw new ScanElectricityException("���� userName����Ϊ��");
		if(Strings.isBlank(pwd)) throw new ScanElectricityException("���� pwd����Ϊ��");
		
		var account=QBaseAccount.baseAccount;
		return factory.selectFrom(account)
			.where(account.userName.eq(userName)
					.and(account.pwd.eq(pwd))
					.and(account.status.eq(true))
					).fetchOne();
	}
}
