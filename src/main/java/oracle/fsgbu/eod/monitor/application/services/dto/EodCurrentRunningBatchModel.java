package oracle.fsgbu.eod.monitor.application.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
public class EodCurrentRunningBatchModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Integer id = null;

	@JsonProperty("branchcode")
	private String branchcode = null;

	@JsonProperty("eocstage")
	private String eocstage = null;

	@JsonProperty("eocbatch")
	private String eocbatch = null;

	@JsonProperty("eocbatchstatus")
	private String eocbatchstatus = null;

	@JsonProperty("starttime")
	private String starttime = null;

	@JsonProperty("endtime")
	private String endtime = null;

	@JsonProperty("duration")
	private String duration = null;

	
	@JsonProperty("dailyavg")
	private String dailyavg = null;

	@JsonProperty("monthlyavg")
	private String monthlyavg = null;


	@JsonProperty("holidayavg")
	private String holidayavg = null;

	
		
	public String getHolidayavg() {
		return holidayavg;
	}

	public void setHolidayavg(String holidayavg) {
		this.holidayavg = holidayavg;
	}

	public String getDailyavg() {
		return dailyavg;
	}

	public void setDailyavg(String dailyavg) {
		this.dailyavg = dailyavg;
	}

	public String getMonthlyavg() {
		return monthlyavg;
	}

	public void setMonthlyavg(String monthlyavg) {
		this.monthlyavg = monthlyavg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getEocstage() {
		return eocstage;
	}

	public void setEocstage(String eocstage) {
		this.eocstage = eocstage;
	}

	public String getEocbatch() {
		return eocbatch;
	}

	public void setEocbatch(String eocbatch) {
		this.eocbatch = eocbatch;
	}

	public String getEocbatchstatus() {
		return eocbatchstatus;
	}

	public void setEocbatchstatus(String eocbatchstatus) {
		this.eocbatchstatus = eocbatchstatus;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "EodCurrentRunningBatchModel [id=" + id + ", branchcode=" + branchcode + ", eocstage=" + eocstage
				+ ", eocbatch=" + eocbatch + ", eocbatchstatus=" + eocbatchstatus + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", duration=" + duration + "]";
	}

}
