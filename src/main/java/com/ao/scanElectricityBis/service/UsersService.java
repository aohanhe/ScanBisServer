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

import com.ao.scanElectricityBis.auth.ScanServerPrincipalManger;
import com.ao.scanElectricityBis.base.ScanElectricityException;
import com.ao.scanElectricityBis.base.ScanSeverExpressionMaps;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.ao.scanElectricityBis.entity.UserInfo;
import com.ao.scanElectricityBis.repository.UserRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import ao.jpaQueryHelper.SelectExpressionCollection;

/**
 * 用户管理服务
 * 
 * @author aohanhe
 *
 */
@Service
public class UsersService extends BaseService<UserInfo, UserRepository> {
	private Logger logger = LoggerFactory.getLogger(UsersService.class);
	@Autowired
	private UserRepository rep;
	@Autowired
	private EntityManager em;

	@Value("${sys.user.minmoney:10}")
	private int minMoney;

	
	public UsersService() {
		super(QUserInfo.userInfo);
	}
	

	/**
	 * 通过OPENID取得用户信息
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
	 * 通过电话取得或者创建用户
	 * 
	 * @param userName
	 * @param phone
	 * @param openId
	 * @return
	 * @throws ScanElectricityException
	 */
	@Transactional
	public UserInfo getOrCreateItemByPhone(String phone, String openId) {

		// 如果原来存在这个电话的用户，则更新openid
		var userInfo = QUserInfo.userInfo;

		var item = factory.selectFrom(userInfo).where(userInfo.phone.eq(phone)).fetchFirst();
		if (item != null) {
			item.setOpenid(openId);

		} else {
			item = new UserInfo();
			item.setName(phone);
			item.setOpenid(openId);
			item.setPhone(phone);
			item.setPwd("");
			item.setLastAccess(new Date());
		}

		em.persist(item);
		return item;
	}

	/**
	 * 判断用户帐户余额是否足
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

	private ScanSeverExpressionMaps<UserInfo> selects;

	@Override
	protected JPAQuery<Tuple> createFullDslQuery() throws ScanElectricityException {
		var userInfo = QUserInfo.userInfo;
		var createAccount =new QBaseAccount("createAccount");
		var modiAccount=new QBaseAccount("modiAccount");

		try {

			if (selects == null) {
				
				selects = new ScanSeverExpressionMaps<UserInfo>(userInfo, UserInfo.class);
				
			}
			
			var str=selects.getExpressionArray();

			
			return 	selects.addExtendsLeftJoin(factory.select(selects.getExpressionArray())
					.from(userInfo)	);	
		} catch (Exception ex) {
			logger.error("创建查询指令出错:" + ex.getMessage(), ex);
			throw new ScanElectricityException("创建查询指令出错:" + ex.getMessage(), ex);
		}

	}

	@Override
	protected UserInfo fecthTupleIntoEntity(Tuple tuple) throws RuntimeException {

		try {
			return selects.fectionDataInItem(tuple);
		} catch (IllegalAccessException e) {
			logger.error("从结果中提取数据失败:" + e.getMessage(), e);
			throw new RuntimeException("从结果中提取数据失败:" + e.getMessage(), e);
		}
	}

}
