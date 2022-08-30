package oracle.fsgbu.eod.monitor.application.services.service;

import oracle.fsgbu.eod.monitor.application.services.api.EodMonitorAppApi;
import oracle.fsgbu.eod.monitor.application.services.dao.ITblEocRunChartRespository;
import oracle.fsgbu.eod.monitor.application.services.dao.ITblWLEurekaConfigRespository;
import oracle.fsgbu.eod.monitor.application.services.dao.TblEodConfigRepository;
import oracle.fsgbu.eod.monitor.application.services.dao.TblEodErrorTrackerRepository;
import oracle.fsgbu.eod.monitor.application.services.dao.TblEodUserDetailsRepository;
import oracle.fsgbu.eod.monitor.application.services.dto.EodCurrentRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodHistoryCollectionModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodHistoryModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodSaveErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodStatusModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodTopRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsReqModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EurekaWeblogicServicesStatusModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodStatusRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.ITblWLEurekaConfigModel;
import oracle.fsgbu.eod.monitor.application.services.dto.ITblWLEurekaInstancesModel;
import oracle.fsgbu.eod.monitor.application.services.dto.UserLoginDto;
import oracle.fsgbu.eod.monitor.application.services.dto.UserLoginRespDto;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEocRunChart;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodErrorTracker;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodUserDetails;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Application;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Applications;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.DataCenterInfo;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.EurekaApplicationResponse;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Instance;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Instance.InstanceStatus;
import oracle.fsgbu.eod.monitor.application.services.filter.JwtRequestFilterCustom;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.InstanceCollection;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Port;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.SecurePort;
import oracle.fsgbu.eod.monitor.application.services.utils.EodConfigUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.EodCurrentRunningBatchUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.EodErrorTrackerUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.EodUserDetailsUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.security.JwtUtils;
import oracle.fsgbu.eod.monitor.application.services.weblogic.dto.WLServicesStatusModel;
import oracle.fsgbu.eod.monitor.application.services.weblogic.dto.WebLogicResponseModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.ResponseEntity.ok;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EodMonitorWeblogicEurekaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EodMonitorWeblogicEurekaService.class);

	private void debug(String msg) {
		LOGGER.debug(this.getClass().getName() + "-->" + msg);
		System.out.println(this.getClass().getName() + "-->" + msg);
	}

	@Autowired
	private ITblWLEurekaConfigRespository tblWLEurekaConfigRespository;

	@Autowired
	private TblEodConfigRepository tblEodConfigRepository;

	private HashMap<String, String> wlStatusofAppsHashmap = new HashMap<String, String>();
	private HashMap<String, String> wleurekaMappings = new HashMap<String, String>();
	private HashMap<String, Integer> wleurekaInstanceCount = new HashMap<String, Integer>();
	private List<ITblWLEurekaConfigModel> tblWlEurekaModelCollection = null;
	private List<ITblWLEurekaInstancesModel> tblWLEurekaInstancesModel = null;
	private HashMap<String, String> eodConfigTblHashmap = new HashMap<String, String>();
	private boolean isWeblogicExecutionDOne = false;

	@PostConstruct
	public void fetchWLEurekaMappings_v2() {
		// Caching the Weblogic-Eureka Apps maintenance table
		this.tblWlEurekaModelCollection = this.tblWLEurekaConfigRespository.fetchWLEurekaMappings();
		this.tblWlEurekaModelCollection.forEach(action -> this.wleurekaMappings.put(action.getWLAppName().toUpperCase(),
				action.getEurekaAppName().toUpperCase()));
		debug("this.tblWlEurekaModelCollection:" + this.tblWlEurekaModelCollection.size());

		// Caching the Eureka Instances from maintenance table
		this.tblWLEurekaInstancesModel = this.tblWLEurekaConfigRespository.fetchTotalEurekaInstances();
		this.tblWLEurekaInstancesModel.forEach(action -> this.wleurekaInstanceCount
				.put(action.getAppNameinEureka().toUpperCase(), action.getTotalInstCount()));

		// Caching the EOD config table values
		this.eodConfigTblHashmap = EodConfigUtils.convertEntityListToHashmap(this.tblEodConfigRepository.findAll());

	}

	@Async
	public ResponseEntity<CompletableFuture<List<EurekaWeblogicServicesStatusModel>>> getEurekaWLServicesStatus(
			String productName) {
		ResponseEntity<CompletableFuture<List<EurekaWeblogicServicesStatusModel>>> responseDetails = null;
		debug("Here inside getEurekaWLServicesStatus");
		CompletableFuture<List<EurekaWeblogicServicesStatusModel>> eurekaWLCollectionModelList = invokeWebLogicServices(
				productName.toUpperCase());
		eurekaWLCollectionModelList = invokeEurekaServices();

		responseDetails = ok(eurekaWLCollectionModelList);
		return responseDetails;
	};

	@Async
	private CompletableFuture<List<EurekaWeblogicServicesStatusModel>> invokeWebLogicServices(String productName) {
		List<EurekaWeblogicServicesStatusModel> eurekaWLCollectionModelList = new ArrayList<>();
		this.isWeblogicExecutionDOne = false;
		/*
		 * UriComponentsBuilder builder = UriComponentsBuilder
		 * .fromHttpUrl(this.eodConfigTblHashmap.get("SERVICE_WEBLOGIC_API").trim());
		 */
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(this.eodConfigTblHashmap.get(productName + "_WEBLOGIC_API").trim());

		debug("URL getting called {} " + builder.toUriString());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		// headers.set("Authorization", "Basic
		// "+this.eodConfigTblHashmap.get("ENCRYPTED_WEBLOGIC_USER:PASSWORD"));

		headers.setBasicAuth(this.eodConfigTblHashmap.get("ENCRYPTED_WEBLOGIC_USER:PASSWORD"));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity(headers);
		debug("Entity is : {}" + entity.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<WebLogicResponseModel> webLogicResponseModel = null;
		webLogicResponseModel = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				WebLogicResponseModel.class, new Object[0]);
		debug("Total Services Deployed:" + webLogicResponseModel.getBody().getBody().getItems().size());

		this.wlStatusofAppsHashmap.clear();
		String eurekaAppname = null;
		for (WLServicesStatusModel wlServicesStatusModel : webLogicResponseModel.getBody().getBody().getItems()) {

			debug("+++++++ " + wlServicesStatusModel.getName().toUpperCase() + " +++++++++");
			eurekaAppname = null;
			eurekaAppname = this.wleurekaMappings.get(wlServicesStatusModel.getName().toUpperCase());
			if (null == eurekaAppname) {
				eurekaAppname = wlServicesStatusModel.getName().toUpperCase();
			}
			debug("EurekaAppName for Weblogic:" + eurekaAppname);
			this.wlStatusofAppsHashmap.put(eurekaAppname,
					(wlServicesStatusModel.getState().contentEquals("STATE_ACTIVE") ? "UP" : "DOWN"));

		}
		debug("Weblogic API fetch done");
		this.isWeblogicExecutionDOne = true;
		return CompletableFuture.completedFuture(eurekaWLCollectionModelList);
	}

	@Async
	private CompletableFuture<List<EurekaWeblogicServicesStatusModel>> invokeEurekaServices() {
		List<EurekaWeblogicServicesStatusModel> eurekaWLCollectionModelList = new ArrayList<>();

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(this.eodConfigTblHashmap.get("SERVICE_EUREKA_API").trim());
		LOGGER.info("URL getting called {} ", builder.toUriString());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity(headers);
		LOGGER.info("Entity is : {}", entity.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EurekaApplicationResponse> eurekaApplicationResponse = null;
		eurekaApplicationResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				EurekaApplicationResponse.class, new Object[0]);
		debug("Total Services Deployed:"
				+ eurekaApplicationResponse.getBody().getApplications().getApplication().size());

		eurekaApplicationResponse.getBody().getApplications().getApplication()
				.forEach(action -> eurekaWLCollectionModelList.add(buildEurekaWeblogicServicesStatusModel(action)));

		return CompletableFuture.completedFuture(eurekaWLCollectionModelList);
	}

	private EurekaWeblogicServicesStatusModel buildEurekaWeblogicServicesStatusModel(Application application) {
		EurekaWeblogicServicesStatusModel statusModel = new EurekaWeblogicServicesStatusModel();
		int eurInstcnt = 0;
		String failedEurekaInsts = "";
		// String webLogicStatus =
		// this.wLStatusofAppsHashmap.getOrDefault(application.getName().toUpperCase(),
		// "DOWN");
		while (!this.isWeblogicExecutionDOne) {
			try {
				debug("Gonna take a nap for 1 minute");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				debug("Thread sleep exception:" + e.getMessage());
				e.printStackTrace();
			}
		}

		if (this.isWeblogicExecutionDOne) {
			String webLogicStatus = this.wlStatusofAppsHashmap.get(application.getName().toUpperCase());
			if (webLogicStatus == null) {
				debug("Value not found for key:" + application.getName().toUpperCase());
				if (application.getName().toUpperCase().contains("-SERVICES")) {
					String modifiedServiceName = application.getName().toUpperCase().replace("-SERVICES", "-SERVICE");
					debug("modifiedServiceName:" + modifiedServiceName);
					webLogicStatus = this.wlStatusofAppsHashmap.get(modifiedServiceName);
				}

			}

			// debug("HashMap size of WebLogic
			// Apps:"+this.wLStatusofAppsHashmap.size());
			statusModel.setWldeploymentstat(webLogicStatus);
			statusModel.setService(application.getName());
			statusModel.setEurekaStat("DOWN");

			for (Instance instance : application.getInstanceCollection()) {
				// debug(instance.getAppName()+"<->"+instance.getInstanceId());
				if (statusModel.getEurekaStat().equals("DOWN")) {
					statusModel.setEurekaStat(instance.getStatus().toString());
				}
				if (instance.getStatus() != InstanceStatus.UP) {
					failedEurekaInsts = failedEurekaInsts.concat(instance.getInstanceId().concat(","));
				}
				eurInstcnt++;
			}
			statusModel.setEurInstcnt(eurInstcnt);
			statusModel.setFailedInst(failedEurekaInsts);
			statusModel.setTotalinstcount(this.wleurekaInstanceCount.get(application.getName().toUpperCase())); // fetch
			// maintenace
			// table
		}

		return statusModel;
	}

}
