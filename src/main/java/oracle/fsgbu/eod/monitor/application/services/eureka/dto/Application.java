package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Application implements Serializable {

	@JsonProperty("name")
	private String name;

	/*
	 * @JsonProperty("instance") private InstanceCollection instanceCollection;
	 */

	@JsonProperty("instance")
	private List<Instance> instanceCollection;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Instance> getInstanceCollection() {
		return instanceCollection;
	}

	public void setInstanceCollection(List<Instance> instanceCollection) {
		this.instanceCollection = instanceCollection;
	}

	public void addInstanceCollection(Instance instance) {
		if (null == this.instanceCollection) {
			this.instanceCollection = new ArrayList<Instance>();
		}

		this.instanceCollection.add(instance);
	}

}
