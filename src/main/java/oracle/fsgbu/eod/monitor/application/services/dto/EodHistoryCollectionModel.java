package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EodHistoryCollectionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// @JsonProperty("EodHistoryCollectionModel")
	// @Valid
	private List<EodHistoryModel> data;

	private List<String> categories;


	public List<EodHistoryModel> getData() {
		return data;
	}

	public void setData(List<EodHistoryModel> data) {
		this.data = data;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public void addCategories(String categories) {
		if (null == this.categories) {
			this.categories = new ArrayList<>();
		}
		this.categories.add(categories);

	}

}
