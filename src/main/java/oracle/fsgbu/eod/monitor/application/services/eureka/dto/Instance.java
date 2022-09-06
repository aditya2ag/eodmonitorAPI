package oracle.fsgbu.eod.monitor.application.services.eureka.dto;

import java.io.Serializable;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Instance implements Serializable {

	@JsonProperty("instanceId")
	private String instanceId;

	@JsonProperty("hostName")
	private String hostName;

	@JsonProperty("app")
	private String appName;

	@JsonProperty("ipAddr")
	private String ipAddr;

	@JsonProperty("status")
	private InstanceStatus status;

	@JsonProperty("overriddenstatus")
	private InstanceStatus overriddenStatus;

	@JsonProperty("port")
	private Port port;

	@JsonProperty("securePort")
	private Port securePort;

	@JsonProperty("countryId")
	private int countryId;

	@JsonProperty("dataCenterInfo")
	private DataCenterInfo dataCenterInfo;

	@JsonProperty("leaseInfo")
	private LeaseInfo leaseInfo;

	@JsonProperty("metadata")
	private HashMap<String, String> metadata;

	@JsonProperty("homePageUrl")
	private String homePageUrl;

	@JsonProperty("healthCheckUrl")
	private String healthCheckUrl;

	@JsonProperty("secureHealthCheckUrl")
	private String secureHealthCheckUrl;

	@JsonProperty("vipAddress")
	private String vipAddress;

	@JsonProperty("secureVipAddress")
	private String secureVipAddress;

	@JsonProperty("isCoordinatingDiscoveryServer")
	private Boolean isCoordinatingDiscoveryServer;

	@JsonProperty("lastUpdatedTimestamp")
	private Long lastUpdatedTimestamp;

	@JsonProperty("lastDirtyTimestamp")
	private Long lastDirtyTimestamp;

	@JsonProperty("actionType")
	private ActionType actionType;

	public enum InstanceStatus {
		UP, // Ready to receive traffic
		DOWN, // Do not send traffic- healthcheck callback failed
		STARTING, // Just about starting- initializations to be done - do not // send traffic
		OUT_OF_SERVICE, // Intentionally shutdown for traffic
		UNKNOWN;

		public static InstanceStatus toEnum(String s) {
			if (s != null) {
				try {
					return InstanceStatus.valueOf(s.toUpperCase());
				} catch (IllegalArgumentException e) {
					// ignore and fall through to unknown
					System.out.println(
							"illegal argument supplied to InstanceStatus.valueOf: {}, defaulting to {}:" + UNKNOWN);
				}
			}
			return UNKNOWN;
		}
	}

	public enum ActionType {
		ADDED, // Added in the discovery server
		MODIFIED, // Changed in the discovery server
		DELETED
		// Deleted from the discovery server
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public InstanceStatus getStatus() {
		return status;
	}

	public void setStatus(InstanceStatus status) {
		this.status = status;
	}

	public InstanceStatus getOverriddenStatus() {
		return overriddenStatus;
	}

	public void setOverriddenStatus(InstanceStatus overriddenStatus) {
		this.overriddenStatus = overriddenStatus;
	}

	public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
	}

	public Port getSecurePort() {
		return securePort;
	}

	public void setSecurePort(Port securePort) {
		this.securePort = securePort;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public DataCenterInfo getDataCenterInfo() {
		return dataCenterInfo;
	}

	public void setDataCenterInfo(DataCenterInfo dataCenterInfo) {
		this.dataCenterInfo = dataCenterInfo;
	}

	public LeaseInfo getLeaseInfo() {
		return leaseInfo;
	}

	public void setLeaseInfo(LeaseInfo leaseInfo) {
		this.leaseInfo = leaseInfo;
	}

	public HashMap<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(HashMap<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}

	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}

	public String getSecureHealthCheckUrl() {
		return secureHealthCheckUrl;
	}

	public void setSecureHealthCheckUrl(String secureHealthCheckUrl) {
		this.secureHealthCheckUrl = secureHealthCheckUrl;
	}

	public String getVipAddress() {
		return vipAddress;
	}

	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}

	public String getSecureVipAddress() {
		return secureVipAddress;
	}

	public void setSecureVipAddress(String secureVipAddress) {
		this.secureVipAddress = secureVipAddress;
	}

	public Boolean getIsCoordinatingDiscoveryServer() {
		return isCoordinatingDiscoveryServer;
	}

	public void setIsCoordinatingDiscoveryServer(Boolean isCoordinatingDiscoveryServer) {
		this.isCoordinatingDiscoveryServer = isCoordinatingDiscoveryServer;
	}

	public Long getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Long lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public Long getLastDirtyTimestamp() {
		return lastDirtyTimestamp;
	}

	public void setLastDirtyTimestamp(Long lastDirtyTimestamp) {
		this.lastDirtyTimestamp = lastDirtyTimestamp;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return "Instance [instanceId=" + instanceId + ", hostName=" + hostName + ", appName=" + appName + ", ipAddr="
				+ ipAddr + ", status=" + status + ", overriddenStatus=" + overriddenStatus + ", port=" + port
				+ ", securePort=" + securePort + ", countryId=" + countryId + ", dataCenterInfo=" + dataCenterInfo
				+ ", leaseInfo=" + leaseInfo + ", metadata=" + metadata + ", homePageUrl=" + homePageUrl
				+ ", healthCheckUrl=" + healthCheckUrl + ", secureHealthCheckUrl=" + secureHealthCheckUrl
				+ ", vipAddress=" + vipAddress + ", secureVipAddress=" + secureVipAddress
				+ ", isCoordinatingDiscoveryServer=" + isCoordinatingDiscoveryServer + ", lastUpdatedTimestamp="
				+ lastUpdatedTimestamp + ", lastDirtyTimestamp=" + lastDirtyTimestamp + ", actionType=" + actionType
				+ "]";
	}

}
