package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EodConfigCollectionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	//@JsonProperty("EodConfigList")
	//@Valid
	private List<EodConfigModel> eodConfigModelList = null;

	public EodConfigCollectionModel eodConfigCollectionModel(List<EodConfigModel> eodConfigModelList) {
		this.eodConfigModelList = eodConfigModelList;
		return this;
	}

	public EodConfigCollectionModel addeodConfigCollectionModel(EodConfigModel eodConfigModelList) {
		if (null == this.eodConfigModelList) {
			this.eodConfigModelList = new ArrayList<>();
		}
		this.eodConfigModelList.add(eodConfigModelList);
		return this;
	}

	/**
	 * @return the eodConfigModelList
	 */
	public List<EodConfigModel> getEodConfigModelList() {
		return eodConfigModelList;
	}

	/**
	 * @param eodConfigModelList the eodConfigModelList to set
	 */
	public void setEodConfigModelList(List<EodConfigModel> eodConfigModelList) {
		this.eodConfigModelList = eodConfigModelList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EodConfigCollectionModel [eodConfigModelList=" + eodConfigModelList + "]";
	}

}
