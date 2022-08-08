package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EurekaApplicationResponse implements Serializable {

	@JsonProperty("applications")
	private Applications applications;

	public Applications getApplications() {
		return applications;
	}

	public void setApplications(Applications applications) {
		this.applications = applications;
	}

	@Override
	public String toString() {
		return "EurekaApplicationResponse [applications=" + applications + "]";
	}

}
