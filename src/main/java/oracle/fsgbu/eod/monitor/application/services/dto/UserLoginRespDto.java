package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;

public class UserLoginRespDto implements Serializable {
	private static final long serialVersionUID = -8091879091924046844L;

	private String status;
	private EodUserDetailsResModel data;
	private String token;

	public UserLoginRespDto() {
	}

	public UserLoginRespDto(String status, EodUserDetailsResModel data, String token) {
		super();
		this.status = status;
		this.data = data;
		this.token = token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EodUserDetailsResModel getData() {
		return data;
	}

	public void setData(EodUserDetailsResModel data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserLoginRespDto [status=" + status + ", data=" + data + ", token=" + token + "]";
	}

}
