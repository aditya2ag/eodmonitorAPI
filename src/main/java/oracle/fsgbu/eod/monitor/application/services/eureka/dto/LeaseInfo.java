package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_LEASE_RENEWAL_INTERVAL = 30;
	public static final int DEFAULT_LEASE_DURATION = 90;

	// Client settings
	private int renewalIntervalInSecs = DEFAULT_LEASE_RENEWAL_INTERVAL;
	private int durationInSecs = DEFAULT_LEASE_DURATION;

	// Server populated
	private long registrationTimestamp;
	private long lastRenewalTimestamp;
	private long evictionTimestamp;
	private long serviceUpTimestamp;

	public int getRenewalIntervalInSecs() {
		return renewalIntervalInSecs;
	}

	public void setRenewalIntervalInSecs(int renewalIntervalInSecs) {
		this.renewalIntervalInSecs = renewalIntervalInSecs;
	}

	public int getDurationInSecs() {
		return durationInSecs;
	}

	public void setDurationInSecs(int durationInSecs) {
		this.durationInSecs = durationInSecs;
	}

	public long getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	public void setRegistrationTimestamp(long registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
	}

	public long getLastRenewalTimestamp() {
		return lastRenewalTimestamp;
	}

	public void setLastRenewalTimestamp(long lastRenewalTimestamp) {
		this.lastRenewalTimestamp = lastRenewalTimestamp;
	}

	public long getEvictionTimestamp() {
		return evictionTimestamp;
	}

	public void setEvictionTimestamp(long evictionTimestamp) {
		this.evictionTimestamp = evictionTimestamp;
	}

	public long getServiceUpTimestamp() {
		return serviceUpTimestamp;
	}

	public void setServiceUpTimestamp(long serviceUpTimestamp) {
		this.serviceUpTimestamp = serviceUpTimestamp;
	}

}
