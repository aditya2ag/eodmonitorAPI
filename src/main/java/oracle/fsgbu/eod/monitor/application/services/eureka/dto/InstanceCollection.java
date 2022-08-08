package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstanceCollection implements Serializable {

	@JsonProperty("instance")
	private List<Instance> instanceCollection;

	public List<Instance> getInstanceCollection() {
		return instanceCollection;
	}

	public void setInstanceCollection(List<Instance> instanceCollection) {
		this.instanceCollection = instanceCollection;
	}

	public void addInstColletion(Instance instanceData) {
		if (null == this.instanceCollection) {
			this.instanceCollection = new ArrayList<Instance>();
		}

		this.instanceCollection.add(instanceData);

	}

	@Override
	public String toString() {
		return "InstanceCollection [instanceCollection=" + instanceCollection + "]";
	}

}
