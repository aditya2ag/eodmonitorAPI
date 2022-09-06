package oracle.fsgbu.eod.monitor.application.services.weblogic.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebLogicResponseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("body")
	private WLServicesStatusCollectionModel body;

	@JsonProperty("messages")
	private List<String> messages;

	public WLServicesStatusCollectionModel getBody() {
		return body;
	}

	public void setBody(WLServicesStatusCollectionModel body) {
		this.body = body;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public void addMessage(String message) {
		if (null == this.messages) {
			this.messages = new ArrayList<String>();
		}
		this.messages.add(message);

	}

	@Override
	public String toString() {
		return "WebLogicResponseModel [body=" + body + ", messages=" + messages + "]";
	}

}
