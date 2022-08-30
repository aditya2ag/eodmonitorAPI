package oracle.fsgbu.eod.monitor.application.services.dto;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ApiDetailsResponse {

	private String id;
	
	private String apiName;
	
	private String url;

	
}
