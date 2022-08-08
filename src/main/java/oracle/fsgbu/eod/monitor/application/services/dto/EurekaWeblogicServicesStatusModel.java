package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class EurekaWeblogicServicesStatusModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("Service")
	private String service;

	@JsonProperty("Totalinstcount")
	private Integer totalinstcount;

	@JsonProperty("EurekaStat")
	private String eurekaStat;

	@JsonProperty("EurInstcnt")
	private Integer eurInstcnt;

	@JsonProperty("FailedInst")
	private String failedInst;

	@JsonProperty("WLDeploymentstat")
	private String wldeploymentstat;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Integer getTotalinstcount() {
		return totalinstcount;
	}

	public void setTotalinstcount(Integer totalinstcount) {
		this.totalinstcount = totalinstcount;
	}

	public String getEurekaStat() {
		return eurekaStat;
	}

	public void setEurekaStat(String eurekaStat) {
		this.eurekaStat = eurekaStat;
	}

	public Integer getEurInstcnt() {
		return eurInstcnt;
	}

	public void setEurInstcnt(Integer eurInstcnt) {
		this.eurInstcnt = eurInstcnt;
	}

	public String getFailedInst() {
		return failedInst;
	}

	public void setFailedInst(String failedInst) {
		this.failedInst = failedInst;
	}

	public String getWldeploymentstat() {
		return wldeploymentstat;
	}

	public void setWldeploymentstat(String wldeploymentstat) {
		this.wldeploymentstat = wldeploymentstat;
	}

	@Override
	public String toString() {
		return "EodHistoryModel2 [service=" + service + ", totalinstcount=" + totalinstcount + ", eurekaStat="
				+ eurekaStat + ", eurInstcnt=" + eurInstcnt + ", failedInst=" + failedInst + ", wldeploymentstat="
				+ wldeploymentstat + "]";
	}

}
