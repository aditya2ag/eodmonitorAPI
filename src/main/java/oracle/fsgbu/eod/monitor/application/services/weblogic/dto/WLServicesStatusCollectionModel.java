package oracle.fsgbu.eod.monitor.application.services.weblogic.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WLServicesStatusCollectionModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("items")
	private List<WLServicesStatusModel> items;

	public List<WLServicesStatusModel> getItems() {
		return items;
	}

	public void setItems(List<WLServicesStatusModel> items) {
		this.items = items;
	}

	public WLServicesStatusCollectionModel addWLServicesStatusCollectionModel (WLServicesStatusModel wLServicesStatusModel) {
		if(null == this.items) {
			this.items= new ArrayList<WLServicesStatusModel>();
		}
		this.items.add(wLServicesStatusModel);
		return this;
	}
	
}
