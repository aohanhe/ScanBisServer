package com.ao;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.ao.scanElectricityBis.entity.QAccountExpense;
import com.ao.scanElectricityBis.entity.QAccountRecharge;
import com.ao.scanElectricityBis.entity.QBaseAccount;
import com.ao.scanElectricityBis.entity.QBaseOperator;
import com.ao.scanElectricityBis.entity.QStationDevice;
import com.ao.scanElectricityBis.entity.QStationPlugInfo;
import com.ao.scanElectricityBis.entity.QStationStationInfo;
import com.ao.scanElectricityBis.entity.QUserInfo;
import com.querydsl.core.types.Expression;

import ao.jpaQueryHelper.IRootPathsManger;


/**
 * 管理在系统中自主创建的别名表对应的RootPath,以解决引用一致的问题
 * @author aohanhe
 *
 */
@Service
public  class QueryDslRootPaths implements IRootPathsManger{
	public static final int Root_Expense=1;
	public static final int Root_Recharge=2;
	public static final int Root_Account=3;
	public static final int Root_Operator=4;
	public static final int Root_Device=5;
	public static final int Root_PlugInfo=6;
	public static final int Root_Station=7;
	public static final int Root_UserInfo=8;
	
	public static final int Root_Creator=100;
	public static final int Root_Modif=101;
	
	/**
	 * 建立表与常量的关联
	 */
	private static final HashMap<Integer, Expression<?>> paths=new HashMap<>(){{
		put(Root_Expense, QAccountExpense.accountExpense);
		put(Root_Recharge,QAccountRecharge.accountRecharge);
		put(Root_Account,QBaseAccount.baseAccount);
		put(Root_Operator,QBaseOperator.baseOperator);
		put(Root_Device,QStationDevice.stationDevice);
		put(Root_PlugInfo,QStationPlugInfo.stationPlugInfo);
		put(Root_Station,QStationStationInfo.stationStationInfo);		
		put(Root_UserInfo,QUserInfo.userInfo);
		
		put(Root_Creator,creatorAccount);
		put(Root_Modif,modifAccount);
		
	}};
	
	public static final QBaseAccount creatorAccount=new QBaseAccount("creatorAccount"); 
	public static final QBaseAccount modifAccount=new QBaseAccount("modifAccount"); 
	
	/**
	 * 通过Id 取得 实体的表达式
	 * @param id
	 * @return
	 */
	public  Expression<?> getRootPathById(int id){
		return paths.get(id);
	}
	
}
