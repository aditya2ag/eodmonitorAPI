package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class EodHistoryModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("data")
	private List<Double> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}

	public void addData(Double data) {
		if (null == this.data) {
			this.data = new ArrayList<>();
		}
		this.data.add(data);
	}

	@Override
	public String toString() {
		return "EodHistoryModel [name=" + name + ", data=" + data + "]";
	}

}
