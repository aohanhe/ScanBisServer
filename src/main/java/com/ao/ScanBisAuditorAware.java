package com.ao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ao.scanElectricityBis.auth.ScanServerPrincipalManger;

/**
 * ҵ����ƴ�����,�������ݱ��浽��ʱ��Ӽ�Ҫ��Ϣ
 * @author aohanhe
 *
 */
@Component
public class ScanBisAuditorAware implements AuditorAware<Integer>{
	@Autowired
	private ScanServerPrincipalManger principalManger;

	@Override
	public Optional<Integer> getCurrentAuditor() {	
		var user=principalManger.getCurrentUser();
		
		if(user==null)
			return Optional.of(0);
		
		int id = user.getUser();
		
		return Optional.of(id);
	}

}
