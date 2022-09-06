package oracle.fsgbu.eod.monitor.application.services.dto;

import javax.persistence.Column;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class SaveExternalApiDetails {

	@JsonProperty("apiName")
	private String apiName;
	
	@JsonProperty("url")
	private String url;

	@JsonProperty("reqMethod")
	private String reqMethod;

	@JsonProperty("requestHeader")
	private String requestHeader;

	@JsonProperty("requestParam")
	private String requestParam;

	@JsonProperty("requestBody")
	private String requestBody;
	
}
