package oracle.fsgbu.eod.monitor.application.services.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import oracle.fsgbu.eod.monitor.application.services.dao.TblEodUserDetailsRepository;
import oracle.fsgbu.eod.monitor.application.services.dto.CustomUserDetails;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodUserDetails;
import oracle.fsgbu.eod.monitor.application.services.filter.JwtRequestFilterCustom;
@Service
public class CustomUserDetailsService implements UserDetailsService {

private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	private void debug(String msg) {
		log.debug(this.getClass().getName()+"-->"+msg);
		System.out.println(this.getClass().getName()+"-->"+msg);
	}
	
	@Autowired
	TblEodUserDetailsRepository userDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
		TblEodUserDetails userDetails = this.userDetailsRepository.findUserByEmail(userName);
		if(null == userDetails) {
			debug("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		
		return new CustomUserDetails(userDetails);
	}

}
