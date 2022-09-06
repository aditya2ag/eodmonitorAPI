package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Applications implements Serializable {

	@JsonProperty("versions__delta")
	private String versions__delta;
	
	@JsonProperty("apps__hashcode")
	private String apps__hashcode;

	@JsonProperty("application")
	private List<Application> application;

	public String getVersions__delta() {
		return versions__delta;
	}

	public void setVersions__delta(String versions__delta) {
		this.versions__delta = versions__delta;
	}

	public String getApps__hashcode() {
		return apps__hashcode;
	}

	public void setApps__hashcode(String apps__hashcode) {
		this.apps__hashcode = apps__hashcode;
	}

	public List<Application> getApplication() {
		return application;
	}

	public void setApplication(List<Application> application) {
		this.application = application;
	}

	public void addApplication(Application application) {
		if (null == this.application) {
			this.application = new ArrayList<Application>();
		}
		this.application.add(application);
	}

}
