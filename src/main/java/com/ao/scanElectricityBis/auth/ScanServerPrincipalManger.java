package com.ao.scanElectricityBis.auth;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用户消息获取接口
 * 
 * @author aohanhe
 *
 */
@Service
public class ScanServerPrincipalManger {
	private static Logger logger = LoggerFactory.getLogger(ScanServerPrincipalManger.class);

	private static CurrentUserAware userAware;

	private static ObjectMapper obMapper = new ObjectMapper();

	@PostConstruct
	public void init() {
		obMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 取得当前用户
	 * 
	 * @return
	 */
	public static IScanServerPrincipal getCurrentUser() {
		if (userAware != null)
			return userAware.currentUser();

		Object pr = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			// 如果当前用户还没有登入过则对应的
			if (!(pr instanceof String)) {
				
					return (IScanServerPrincipal) pr;
				
			}

			return null;

		} catch (ClassCastException ex) {
			try {
				String value = obMapper.writeValueAsString(pr);

				var item = obMapper.readValue(value, ScanServerPrincipal.class);
				return item;
			} catch (Exception e) {
				logger.error("取得当前用户出错:" + e.getMessage(), e);

			}
		}

		return null;
	}

	@Autowired(required = false)
	public void setUserAware(CurrentUserAware userAware) {
		ScanServerPrincipalManger.userAware = userAware;
	}

}
