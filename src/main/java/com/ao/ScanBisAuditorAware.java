package com.ao;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * ҵ����ƴ�����,�������ݱ��浽��ʱ��Ӽ�Ҫ��Ϣ
 * @author aohanhe
 *
 */
@Component
public class ScanBisAuditorAware implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		
		UserDetails principal=(UserDetails) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
		
		
		
		return Optional.of(0);
	}

}
