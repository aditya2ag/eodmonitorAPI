package oracle.fsgbu.eod.monitor.application.services.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import oracle.fsgbu.eod.monitor.application.services.entities.TblEodUserDetails;

public class CustomUserDetails implements UserDetails {

	private TblEodUserDetails tblEodUserDetails;

	public CustomUserDetails(TblEodUserDetails tblEodUserDetails) {
		this.tblEodUserDetails = tblEodUserDetails;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.tblEodUserDetails.getRole());
		ArrayList<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>();
		authList.add(authority);
		return authList;
	}

	@Override
	public String getPassword() {
		return this.tblEodUserDetails.getPassword();
	}

	@Override
	public String getUsername() {
		return this.tblEodUserDetails.getEmail_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
