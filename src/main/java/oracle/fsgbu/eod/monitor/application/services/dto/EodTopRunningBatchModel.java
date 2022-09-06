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
public class EodTopRunningBatchModel implements Serializable {
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

	@JsonProperty("sessionId")
	private String sessionId = null;

	@JsonProperty("serialNo")
	private String serialNo = null;

	
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

	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchcode == null) ? 0 : branchcode.hashCode());
		result = prime * result + ((dailyavg == null) ? 0 : dailyavg.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + ((endtime == null) ? 0 : endtime.hashCode());
		result = prime * result + ((eocbatch == null) ? 0 : eocbatch.hashCode());
		result = prime * result + ((eocbatchstatus == null) ? 0 : eocbatchstatus.hashCode());
		result = prime * result + ((eocstage == null) ? 0 : eocstage.hashCode());
		result = prime * result + ((holidayavg == null) ? 0 : holidayavg.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((monthlyavg == null) ? 0 : monthlyavg.hashCode());
		result = prime * result + ((starttime == null) ? 0 : starttime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EodTopRunningBatchModel other = (EodTopRunningBatchModel) obj;
		if (branchcode == null) {
			if (other.branchcode != null)
				return false;
		} else if (!branchcode.equals(other.branchcode))
			return false;
		if (dailyavg == null) {
			if (other.dailyavg != null)
				return false;
		} else if (!dailyavg.equals(other.dailyavg))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (endtime == null) {
			if (other.endtime != null)
				return false;
		} else if (!endtime.equals(other.endtime))
			return false;
		if (eocbatch == null) {
			if (other.eocbatch != null)
				return false;
		} else if (!eocbatch.equals(other.eocbatch))
			return false;
		if (eocbatchstatus == null) {
			if (other.eocbatchstatus != null)
				return false;
		} else if (!eocbatchstatus.equals(other.eocbatchstatus))
			return false;
		if (eocstage == null) {
			if (other.eocstage != null)
				return false;
		} else if (!eocstage.equals(other.eocstage))
			return false;
		if (holidayavg == null) {
			if (other.holidayavg != null)
				return false;
		} else if (!holidayavg.equals(other.holidayavg))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (monthlyavg == null) {
			if (other.monthlyavg != null)
				return false;
		} else if (!monthlyavg.equals(other.monthlyavg))
			return false;
		if (starttime == null) {
			if (other.starttime != null)
				return false;
		} else if (!starttime.equals(other.starttime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EodCurrentRunningBatchModel [id=" + id + ", branchcode=" + branchcode + ", eocstage=" + eocstage
				+ ", eocbatch=" + eocbatch + ", eocbatchstatus=" + eocbatchstatus + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", duration=" + duration + "]";
	}

}
