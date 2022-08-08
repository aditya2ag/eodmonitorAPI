package oracle.fsgbu.eod.monitor.application.services.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import oracle.fsgbu.eod.monitor.application.services.api.EodMonitorAppApi;
import oracle.fsgbu.eod.monitor.application.services.dto.EodCurrentRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodHistoryCollectionModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodSaveErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodStatusModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodTopRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsReqModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsResModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EurekaWeblogicServicesStatusModel;
import oracle.fsgbu.eod.monitor.application.services.dto.UserLoginDto;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.EurekaApplicationResponse;

@RestController
@RequestMapping("/service")
//@CrossOrigin(origins = { "*" }, allowCredentials = "false", allowedHeaders = { "*" }, maxAge = 60 * 30, methods = { RequestMethod.GET, RequestMethod.POST})
 
public class EodConfigServiceController {

	@Autowired
	private EodMonitorAppApi eodMonitorAppApi;

	@GetMapping("/getMsg")
	public String getMsg() {
		System.out.println("Inside get message");
		return this.eodMonitorAppApi.getTestmsg();
	};

	@PostMapping("/userRegistration")
	public /* ResponseEntity<EodUserDetailsResModel> */ResponseEntity<String> saveUserDetails(
			@Valid @RequestBody EodUserDetailsReqModel eodUserDetailsModel) {
		return this.eodMonitorAppApi.saveUserDetails(eodUserDetailsModel);
	};

	@PostMapping("/appLogin")
	public ResponseEntity<?> loginInfo(@RequestBody UserLoginDto userDetails) throws Exception {
		System.out.println(userDetails);
		return this.eodMonitorAppApi.loginInfo(userDetails);
	};

	@GetMapping("/getEODConfigurations")
	public ResponseEntity<HashMap<String, String>> getAllEodAppConfig() {
		return this.eodMonitorAppApi.getAllEodConfiguration();
	};

	/*
	 * @GetMapping("/getAllCurrentRunningBatches") public
	 * ResponseEntity<EodCurrentRunningBatchCollectionModel>
	 * getAllCurrentRunningBatches() { return
	 * this.eodMonitorAppApi.getAllCurrentRunningBatches(); }
	 */

	@GetMapping("/getAllCurrentRunningBatches")
	public ResponseEntity<List<EodCurrentRunningBatchModel>> getAllCurrentRunningBatches() {
		return this.eodMonitorAppApi.getAllCurrentRunningBatches();
	};

	@GetMapping("/getAllTopRunningBatches")
	public ResponseEntity<List<EodTopRunningBatchModel>> getAllTopRunningBatches() {
		return this.eodMonitorAppApi.getAllTopRunningBatches();
	};
	
	@GetMapping("/getAllBatchStatus")
	public ResponseEntity<List<EodCurrentRunningBatchModel>> getAllBatcheStatus() {
		return this.eodMonitorAppApi.getAllBatcheStatus();
	};

	@GetMapping("/getEodStatus/{eodDate}")
	public ResponseEntity<EodStatusModel> getEodStatus(
			@PathVariable(value = "eodDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate eodDate) {
		return this.eodMonitorAppApi.getEodStatus(eodDate);
	};

	@GetMapping("/getEodHistory")
	public ResponseEntity<EodHistoryCollectionModel> getEodHistoryData() {
		return this.eodMonitorAppApi.getEodHistoryData();
	};

	@GetMapping("/getEurekaWLServicesStatus/{productName}")
	public ResponseEntity<List<EurekaWeblogicServicesStatusModel>> getEurekaWLServicesStatus(
			@PathVariable(value = "productName", required = true) String productName) {
		return this.eodMonitorAppApi.getEurekaWLServicesStatus(productName);
	};


	@GetMapping("/getEurekaServicesStatus")
	public ResponseEntity<EurekaApplicationResponse> getEurekaServicesStatus() {
		return this.eodMonitorAppApi.getEurekaStatus();
	};

	@GetMapping("/getEodErrorLog")
	public ResponseEntity<List<EodErrorLogModel>> getEodErrorLogs() {
		return this.eodMonitorAppApi.getEodErrorLogs();
	};

	@PostMapping("/saveEodErrors")
	public ResponseEntity<List<EodErrorLogModel>> saveEodErrors(
			@Valid @RequestBody EodSaveErrorLogModel eodSaveErrorLogModel) {
		return this.eodMonitorAppApi.saveEodErrors(eodSaveErrorLogModel);
	};
	
}
