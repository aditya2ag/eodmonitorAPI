package oracle.fsgbu.eod.monitor.application.services.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class EodHistoryRepoDto {

	private Double maxRunTime;

	private String eocStage;

	private Date branchDate;


	public Double getMaxRunTime() {
		return maxRunTime;
	}

	public void setMaxRunTime(Double maxRunTime) {
		this.maxRunTime = maxRunTime;
	}

	public String getEocStage() {
		return eocStage;
	}

	public void setEocStage(String eocStage) {
		this.eocStage = eocStage;
	}

	public Date getBranchDate() {
		return branchDate;
	}

	public void setBranchDate(Date branchDate) {
		this.branchDate = branchDate;
	}

}
