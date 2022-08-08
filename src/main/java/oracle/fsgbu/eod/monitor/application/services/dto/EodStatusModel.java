package oracle.fsgbu.eod.monitor.application.services.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class EodStatusModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("Completed")
	private int completed;

	@JsonProperty("Aborted")
	private int aborted;

	@JsonProperty("Running")
	private int running;

	@JsonProperty("Pending")
	private int pending;

	@JsonProperty("Total")
	private int total;

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public int getAborted() {
		return aborted;
	}

	public void setAborted(int aborted) {
		this.aborted = aborted;
	}

	public int getRunning() {
		return running;
	}

	public void setRunning(int running) {
		this.running = running;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "EodStatusModel [completed=" + completed + ", aborted=" + aborted + ", running=" + running + ", total="
				+ total + "]";
	}

	public void ResetEodStatusModel() {
		this.completed = 0;
		this.aborted = 0;
		this.running = 0;
		this.total = 0;
	}

}
