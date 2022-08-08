package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecurePort implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("$")
	private int port;
	
	@JsonProperty("@enabled")
	private boolean enabled;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "SecurePort [port=" + port + ", enabled=" + enabled + "]";
	}
	
	
	
}
