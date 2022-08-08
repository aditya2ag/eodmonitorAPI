package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EodUserDetailsReqModel implements Serializable {

	private String name;

	private String email;

	private String password;

	private String role;
	
	private String userRole;

	private String location;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "EodUserDetailsReqModel [name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", userRole=" + userRole + ", location=" + location + "]";
	}


}
