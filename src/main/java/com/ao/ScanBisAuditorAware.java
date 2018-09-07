package com.ao;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 业务审计处理类,用于数据保存到库时添加及要信息
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
