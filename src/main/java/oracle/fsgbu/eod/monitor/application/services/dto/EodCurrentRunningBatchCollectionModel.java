package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EodCurrentRunningBatchCollectionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	//@JsonProperty("EodConfigList")
	//@Valid
	private List<EodCurrentRunningBatchModel> eodCurrentRunningBatchModelList = null;

	public EodCurrentRunningBatchCollectionModel eodCurrentRunningBatchCollectionModel(List<EodCurrentRunningBatchModel> eodCurrentRunningBatchModelList) {
		this.eodCurrentRunningBatchModelList = eodCurrentRunningBatchModelList;
		return this;
	}

	public EodCurrentRunningBatchCollectionModel addeodCurrentRunningBatchCollectionModel(EodCurrentRunningBatchModel eodCurrentRunningBatchModelList) {
		if (null == this.eodCurrentRunningBatchModelList) {
			this.eodCurrentRunningBatchModelList = new ArrayList<>();
		}
		this.eodCurrentRunningBatchModelList.add(eodCurrentRunningBatchModelList);
		return this;
	}

	public List<EodCurrentRunningBatchModel> getEodCurrentRunningBatchModelList() {
		return eodCurrentRunningBatchModelList;
	}

	public void setEodCurrentRunningBatchModelList(List<EodCurrentRunningBatchModel> eodCurrentRunningBatchModelList) {
		this.eodCurrentRunningBatchModelList = eodCurrentRunningBatchModelList;
	}

	@Override
	public String toString() {
		return "EodCurrentRunningBatchCollectionModel [eodCurrentRunningBatchModelList="
				+ eodCurrentRunningBatchModelList + "]";
	}

	
}
