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
 * 帐号服务类
 * @author aohanhe
 *
 */
@Service
public class AccountService extends BaseService<BaseAccount, AccountRepository>{
	@Autowired
	private EntityManager em;
	
	/**
	 * querydsl构建工具
	 */
	private JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}

	/**
	 * 检查用户帐号密码是否正确
	 * @param userName
	 * @param pwd
	 * @return 如果不正确返回空
	 * @throws ScanElectricityException
	 */
	public BaseAccount checkUserPwd(String userName,String pwd) throws ScanElectricityException {
		
		if(Strings.isBlank(userName)) throw new ScanElectricityException("参数 userName不能为空");
		if(Strings.isBlank(pwd)) throw new ScanElectricityException("参数 pwd不能为空");
		
		var account=QBaseAccount.baseAccount;
		return factory.selectFrom(account)
			.where(account.userName.eq(userName)
					.and(account.pwd.eq(pwd))
					.and(account.status.eq(true))
					).fetchOne();
	}
}
