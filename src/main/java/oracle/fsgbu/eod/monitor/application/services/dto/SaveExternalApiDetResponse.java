package oracle.fsgbu.eod.monitor.application.services.dto;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class SaveExternalApiDetResponse {

	@JsonProperty("status")
	private String status;
	
}
