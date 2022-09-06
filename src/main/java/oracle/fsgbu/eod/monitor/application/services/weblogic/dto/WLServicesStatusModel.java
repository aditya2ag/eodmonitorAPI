package oracle.fsgbu.eod.monitor.application.services.weblogic.dto;

import java.io.Serializable;

public class WLServicesStatusModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String type;
	private String state;
	private String health;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	@Override
	public String toString() {
		return "WLServicesStatusModel [name=" + name + ", type=" + type + ", state=" + state + ", health=" + health
				+ "]";
	}

}
