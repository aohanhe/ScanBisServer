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
 * �û���Ϣ��ȡ�ӿ�
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
	 * ȡ�õ�ǰ�û�
	 * 
	 * @return
	 */
	public static IScanServerPrincipal getCurrentUser() {
		if (userAware != null)
			return userAware.currentUser();

		Object pr = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {
			// �����ǰ�û���û�е�������Ӧ��
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
				logger.error("ȡ�õ�ǰ�û�����:" + e.getMessage(), e);

			}
		}

		return null;
	}

	@Autowired(required = false)
	public void setUserAware(CurrentUserAware userAware) {
		ScanServerPrincipalManger.userAware = userAware;
	}

}
