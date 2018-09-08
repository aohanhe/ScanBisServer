package com.ao.scanElectricityBis.service;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.ao.scanElectricityBis.entity.UserInfo;
import com.ao.scanElectricityBis.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * �û��������
 * 
 * @author aohanhe
 *
 */
@Service
public class UsersService extends BaseService<UserInfo,UserRepository>{
	private Logger logger = LoggerFactory.getLogger(UsersService.class);
	@Autowired
	private UserRepository rep;
	@Autowired
	private EntityManager em;


	@Value("${sys.user.minmoney:10}")
	private int minMoney ;

	/**
	 * querydsl��������
	 */
	private JPAQueryFactory factory;

	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}

	/**
	 * ͨ��OPENIDȡ���û���Ϣ
	 * 
	 * @param openId
	 * @return
	 */
	public UserInfo getItemByOpenId(String openId) {

		var userInfo = QUserInfo.userInfo;

		var re = factory.selectFrom(userInfo).where(userInfo.openid.eq(openId)).fetchFirst();

		return re;
	}
	
	

	/**
	 * ͨ���绰ȡ�û��ߴ����û�
	 * 
	 * @param userName
	 * @param phone
	 * @param openId
	 * @return
	 * @throws ScanElectricityException
	 */
	@Transactional
	public UserInfo getOrCreateItemByPhone(String userName, String phone, String openId) {

		// ���ԭ����������绰���û��������openid
		var userInfo = QUserInfo.userInfo;

		var item = factory.selectFrom(userInfo).where(userInfo.phone.eq(phone)).fetchFirst();
		if (item != null) {
			item.setOpenid(openId);

		} else {
			item = new UserInfo();
			item.setUserName(userName);
			item.setOpenid(openId);
			item.setPhone(phone);
			item.setPwd("");
			item.setLastAcess(new Date());
		}

		em.persist(item);
		return item;
	}

	/**
	 * �ж��û��ʻ�����Ƿ���
	 * 
	 * @param userId
	 * @return
	 */
	public boolean hasEnoughMoney(int userId) {
		var user = rep.findById(userId);
		if (user.isPresent())
			return user.get().getMoney() > this.minMoney;
		return false;
	}

}
