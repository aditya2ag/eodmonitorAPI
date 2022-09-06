package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EodUserDetailsResModel implements Serializable {

	private String name;

	private String email;

	private String role;

	private String userRole;

	private String location;

	// private String errorDesc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "EodUserDetailsResModel [name=" + name + ", email=" + email + ", role=" + role + ", userRole=" + userRole
				+ ", location=" + location + "]";
	}

}
