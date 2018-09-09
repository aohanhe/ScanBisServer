package com.ao;

import com.ao.scanElectricityBis.entity.QBaseAccount;

/**
 * 管理在系统中自主创建的别名表对应的RootPath,以解决引用一致的问题
 * @author aohanhe
 *
 */
public  class QueryDslRootPaths {
	public static final QBaseAccount creatorAccount=new QBaseAccount("creatorAccount"); 
	public static final QBaseAccount modifAccount=new QBaseAccount("modifAccount"); 
}
