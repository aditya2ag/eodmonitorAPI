package oracle.fsgbu.eod.monitor.application.services.api;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import oracle.fsgbu.eod.monitor.application.services.dto.EodCurrentRunningBatchCollectionModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodCurrentRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodHistoryCollectionModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodHistoryModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodSaveErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodStatusModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodTopRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsReqModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsResModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EurekaWeblogicServicesStatusModel;
import oracle.fsgbu.eod.monitor.application.services.dto.UserLoginDto;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Application;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Applications;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.EurekaApplicationResponse;

@Component
public interface EodMonitorAppApi {

	public String getTestmsg();

	// public ResponseEntity<EodCurrentRunningBatchCollectionModel>
	// getAllCurrentRunningBatches();
	public ResponseEntity<HashMap<String, String>> getAllEodConfiguration();

	public ResponseEntity<EodHistoryCollectionModel> getEodHistoryData();

	public ResponseEntity<List<EodCurrentRunningBatchModel>> getAllCurrentRunningBatches();

	public ResponseEntity<List<EodTopRunningBatchModel>> getAllTopRunningBatches();

	public ResponseEntity<List<EodCurrentRunningBatchModel>> getAllBatcheStatus();
	
	public ResponseEntity<EodStatusModel> getEodStatus(LocalDate eodDate);

	public ResponseEntity<List<EurekaWeblogicServicesStatusModel>> getEurekaWLServicesStatus(String productName);

	public ResponseEntity<EurekaApplicationResponse> getEurekaStatus();

	public ResponseEntity<List<EodErrorLogModel>> getEodErrorLogs();

	public ResponseEntity<List<EodErrorLogModel>> saveEodErrors(EodSaveErrorLogModel eodSaveErrorLogModel);

	public /* ResponseEntity<EodUserDetailsResModel> */ ResponseEntity<String> saveUserDetails(
			EodUserDetailsReqModel eodUserDetailsModel);

	public ResponseEntity<?> loginInfo(UserLoginDto userDetails) throws Exception;
}