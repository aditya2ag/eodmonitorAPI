package oracle.fsgbu.eod.monitor.application.services.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ApiHealthCheckResponseModel {

	private String apiName;
	
	private String status;
	
	private String responseSize;
	
	private String responseTime;
	
}
