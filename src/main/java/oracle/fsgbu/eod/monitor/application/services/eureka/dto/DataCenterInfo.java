package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataCenterInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("@class")
	private String classname;

	@JsonProperty("name")
	private String name;

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DataCenterInfo [classname=" + classname + ", name=" + name + "]";
	}

}
