package oracle.fsgbu.eod.monitor.application.services.service;

import static org.springframework.http.ResponseEntity.ok;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import oracle.fsgbu.eod.monitor.application.services.api.EodMonitorAppApi;
import oracle.fsgbu.eod.monitor.application.services.dao.ITblApiHealthcheckDetRepository;
import oracle.fsgbu.eod.monitor.application.services.dao.ITblEocRunChartRespository;
import oracle.fsgbu.eod.monitor.application.services.dao.ITblWLEurekaConfigRespository;
import oracle.fsgbu.eod.monitor.application.services.dao.TblEodConfigRepository;
import oracle.fsgbu.eod.monitor.application.services.dao.TblEodErrorTrackerRepository;
import oracle.fsgbu.eod.monitor.application.services.dao.TblEodUserDetailsRepository;
import oracle.fsgbu.eod.monitor.application.services.dto.ApiDetailsResponse;
import oracle.fsgbu.eod.monitor.application.services.dto.ApiHealthCheckResponseModel;
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
import oracle.fsgbu.eod.monitor.application.services.dto.SaveExternalApiDetResponse;
import oracle.fsgbu.eod.monitor.application.services.dto.SaveExternalApiDetails;
import oracle.fsgbu.eod.monitor.application.services.dto.UserLoginDto;
import oracle.fsgbu.eod.monitor.application.services.dto.UserLoginRespDto;
import oracle.fsgbu.eod.monitor.application.services.entities.TblApihealthcheckDetails;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodUserDetails;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Application;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Applications;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.DataCenterInfo;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.EurekaApplicationResponse;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Instance;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Instance.InstanceStatus;
import oracle.fsgbu.eod.monitor.application.services.eureka.dto.Port;
import oracle.fsgbu.eod.monitor.application.services.utils.CallWebserviceRest;
import oracle.fsgbu.eod.monitor.application.services.utils.EodConfigUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.EodCurrentRunningBatchUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.EodErrorTrackerUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.EodUserDetailsUtils;
import oracle.fsgbu.eod.monitor.application.services.utils.ValidateJson;
import oracle.fsgbu.eod.monitor.application.services.utils.security.JwtUtils;
import oracle.fsgbu.eod.monitor.application.services.weblogic.dto.WLServicesStatusModel;
import oracle.fsgbu.eod.monitor.application.services.weblogic.dto.WebLogicResponseModel;

@Service
public class EodMonitorService implements EodMonitorAppApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(EodMonitorService.class);

	private void debug(String msg) {
		LOGGER.debug(this.getClass().getName() + "-->" + msg);
		System.out.println(this.getClass().getName() + "-->" + msg);
	}

	@Autowired
	private ITblEocRunChartRespository tblEocRunChartRespository;

	@Autowired
	private ITblWLEurekaConfigRespository tblWLEurekaConfigRespository;

	@Autowired
	private TblEodConfigRepository tblEodConfigRepository;

	@Autowired
	private TblEodErrorTrackerRepository tblEodErrorTrackerRepository;

	@Autowired
	private TblEodUserDetailsRepository tblEodUserDetailsRepository;

	@Autowired
	private EodStatusModel eodStatusModel;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	ITblApiHealthcheckDetRepository tblApiHealthcheckDetRepository;

	@Override
	public String getTestmsg() {
		debug("EodConfigService");
		return "EodConfigService";
	}

	private HashMap<String, String> wlStatusofAppsHashmap = new HashMap<String, String>();
	private HashMap<String, String> wleurekaMappings = new HashMap<String, String>();
	private HashMap<String, Integer> wleurekaInstanceCount = new HashMap<String, Integer>();
	private List<ITblWLEurekaConfigModel> tblWlEurekaModelCollection = null;
	private List<ITblWLEurekaInstancesModel> tblWLEurekaInstancesModel = null;
	private HashMap<String, String> eodConfigTblHashmap = new HashMap<String, String>();

	/*
	 * @Override public ResponseEntity<EodCurrentRunningBatchCollectionModel>
	 * getAllCurrentRunningBatches() {
	 * ResponseEntity<EodCurrentRunningBatchCollectionModel> responseDetails; final
	 * List<EodCurrentRunningBatchModel> eodCurrentRunningBatchModelList = null;
	 * responseDetails = ok(new EodCurrentRunningBatchCollectionModel()
	 * .eodCurrentRunningBatchCollectionModel(eodCurrentRunningBatchModelList));
	 * return responseDetails; }
	 */
	@PostConstruct
	public void fetchWLEurekaMappings() {
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

	@Override
	public ResponseEntity<HashMap<String, String>> getAllEodConfiguration() {
		HashMap<String, String> eodConfigHashmap = EodConfigUtils
				.convertEntityListToHashmap(this.tblEodConfigRepository.findAll());
		ResponseEntity<HashMap<String, String>> responseDetails = ok(eodConfigHashmap);
		return responseDetails;
	}

	@Override
	public ResponseEntity<EodStatusModel> getEodStatus(LocalDate eodDate) {
		ResponseEntity<EodStatusModel> responseDetails;

		this.eodStatusModel.ResetEodStatusModel();

		List<IEodStatusRepoModel> ieodStatusRepoModel = this.tblEocRunChartRespository.fetchBatchStatus(eodDate);
		for (IEodStatusRepoModel iEodStatusRepoModel : ieodStatusRepoModel) {
			switch (iEodStatusRepoModel.getEocBatchStatus()) {
			case "C":
				this.eodStatusModel.setCompleted(iEodStatusRepoModel.getBatchCount());
				break;
			case "W":
				this.eodStatusModel.setRunning(iEodStatusRepoModel.getBatchCount());
				break;
			case "A":
				this.eodStatusModel.setRunning(iEodStatusRepoModel.getBatchCount());
				break;
			case "N":
				this.eodStatusModel.setPending(iEodStatusRepoModel.getBatchCount());
				// nonRunBatchCount = iEodStatusRepoModel.getBatchCount();
				break;
			default:
				break;
			}
		}

		this.eodStatusModel.setTotal(this.eodStatusModel.getAborted() + this.eodStatusModel.getCompleted()
				+ this.eodStatusModel.getRunning() + this.eodStatusModel.getPending() /* nonRunBatchCount */);

		responseDetails = ok(eodStatusModel);
		return responseDetails;
	}

	@Override
	public ResponseEntity<List<EodCurrentRunningBatchModel>> getAllCurrentAbortedBatches() {
		ResponseEntity<List<EodCurrentRunningBatchModel>> responseDetails;
		final List<EodCurrentRunningBatchModel> responseList = EodCurrentRunningBatchUtils
				.convertAbortedBatchIEntityListToVOList(this.tblEocRunChartRespository.fetchCurrentAbortedBatches());
		responseDetails = ok(responseList);
		return responseDetails;
	}

	@Override
	public ResponseEntity<List<EodTopRunningBatchModel>> getAllTopRunningBatches() {
		String pattern = "dd-MMM-yyyy";
		DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
		Timestamp homeBranchDate = this.tblEocRunChartRespository.fetchCoreSystemDate();
		LocalDate tempDate = homeBranchDate.toLocalDateTime().toLocalDate();
		String dateClause = null;
		List<LocalDate> monthenddateClauses = new ArrayList<LocalDate>();
		int months = 6; // Last 6 month ends data
		while (months > 0) {

			tempDate = tempDate.with(TemporalAdjusters.firstDayOfMonth()).minusDays(1);
			debug("tempDate:" + tempDate);
			monthenddateClauses.add(tempDate);
			/*
			 * String formattedDate = tempDate.format(df); if (months == 6) { dateClause =
			 * "'" + formattedDate + "'"; } else { dateClause = dateClause.concat("'" +
			 * formattedDate + "'"); } if (months > 1) { dateClause =
			 * dateClause.concat(","); }
			 */
			months--;
		}
		debug("dateClause:" + monthenddateClauses.toString());
		final List<EodTopRunningBatchModel> responseList = EodCurrentRunningBatchUtils
				.convertITopRunningBatchEntityListToVOList(
						this.tblEocRunChartRespository.fetchTopRunningBatches(monthenddateClauses));
		return ResponseEntity.ok(responseList);
	}

	@Override
	public ResponseEntity<List<EodCurrentRunningBatchModel>> getAllBatcheStatus() {
		ResponseEntity<List<EodCurrentRunningBatchModel>> responseDetails;
		final List<EodCurrentRunningBatchModel> responseList = EodCurrentRunningBatchUtils
				.convertIEntityListToVOList(this.tblEocRunChartRespository.fetchAllBatcheStatus());
		responseDetails = ok(responseList);
		return responseDetails;
	}

	@Override
	public ResponseEntity<EodHistoryCollectionModel> getEodHistoryData() {
		ResponseEntity<EodHistoryCollectionModel> responseDetails = null;
		List<EodHistoryModel> eodHistoryModelList = new ArrayList<EodHistoryModel>();
		List<String> categoriesDate = new ArrayList<String>();
		// getEodHistoryDatafromEntity();
		String[] historyBranches = this.tblEocRunChartRespository.historyBranches().split(",");
		List<String> histBrnList = Arrays.asList(historyBranches);
		HashMap<String, Object> voListRepoHashMap = new HashMap<String, Object>();
		voListRepoHashMap = EodCurrentRunningBatchUtils
				.convertIHistoryEntityListToVOList(this.tblEocRunChartRespository.fetchEodHistoryData(7,histBrnList));

		eodHistoryModelList = (List<EodHistoryModel>) voListRepoHashMap.get("EodHistoryModel");
		categoriesDate = (List<String>) voListRepoHashMap.get("categoryDate");

		EodHistoryCollectionModel eodHistoryCollectionModel = new EodHistoryCollectionModel();

		eodHistoryCollectionModel.setData(eodHistoryModelList);
		eodHistoryCollectionModel.setCategories(categoriesDate);

		responseDetails = ok(eodHistoryCollectionModel);

		return responseDetails;
	};

	@Override
	public ResponseEntity<List<EurekaWeblogicServicesStatusModel>> getEurekaWLServicesStatus(String productName) {
		ResponseEntity<List<EurekaWeblogicServicesStatusModel>> responseDetails = null;
		debug("Here inside getEurekaWLServicesStatus");
		List<EurekaWeblogicServicesStatusModel> eurekaWLCollectionModelList = invokeWebLogicServices(
				productName.toUpperCase());
		eurekaWLCollectionModelList = invokeEurekaServices(productName.toUpperCase());

		responseDetails = ok(eurekaWLCollectionModelList);
		return responseDetails;
	};

	private List<EurekaWeblogicServicesStatusModel> invokeWebLogicServices(String productName) {
		List<EurekaWeblogicServicesStatusModel> eurekaWLCollectionModelList = new ArrayList<>();

		/*
		 * UriComponentsBuilder builder = UriComponentsBuilder
		 * .fromHttpUrl(this.eodConfigTblHashmap.get("SERVICE_WEBLOGIC_API").trim());
		 */
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(this.eodConfigTblHashmap.get(productName + "_WEBLOGIC_API").trim());

		LOGGER.info("URL getting called {} ", builder.toUriString());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		// headers.set("Authorization", "Basic
		// "+this.eodConfigTblHashmap.get("ENCRYPTED_WEBLOGIC_USER:PASSWORD"));

		headers.setBasicAuth(this.eodConfigTblHashmap.get("ENCRYPTED_WEBLOGIC_USER:PASSWORD"));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity(headers);
		LOGGER.info("Entity is : {}", entity.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<WebLogicResponseModel> webLogicResponseModel = null;
		webLogicResponseModel = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				WebLogicResponseModel.class, new Object[0]);
		debug("Total Services Deployed:" + webLogicResponseModel.getBody().getBody().getItems().size());

		/*
		 * webLogicResponseModel.getBody().getBody().getItems() .forEach(action ->
		 * eurekaWLCollectionModelList.add(buildEurekaWeblogicServicesStatusModel(action
		 * )));
		 */

		/*
		 * this.wLStatusofAppsHashmap.clear(); Pattern pattern =
		 * Pattern.compile("[0-9.]"); Matcher match = null; String finalAppName = null;
		 * for (WLServicesStatusModel wlServicesStatusModel :
		 * webLogicResponseModel.getBody().getBody().getItems()) {
		 * 
		 * debug("+++++++ " + wlServicesStatusModel.getName().toUpperCase() +
		 * " +++++++++"); String[] appName =
		 * wlServicesStatusModel.getName().toUpperCase().split("-", 0); finalAppName =
		 * null; for (String a : appName) { debug(a); try { match.reset(); } catch
		 * (Exception e) { // TODO: handle exception } match = pattern.matcher(a); if
		 * (!match.find()) { finalAppName = (finalAppName != null ?
		 * finalAppName.concat("-" + a) : a);
		 * 
		 * } } debug("finalAppName:" + finalAppName);
		 * this.wLStatusofAppsHashmap.put((finalAppName !=null ? finalAppName :
		 * wlServicesStatusModel.getName().toUpperCase()),
		 * (wlServicesStatusModel.getState().contentEquals("STATE_ACTIVE") ? "UP" :
		 * "DOWN"));
		 * 
		 * }
		 */

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
			this.wlStatusofAppsHashmap.put(eurekaAppname, ((wlServicesStatusModel.getState() == null) ? "DOWN"
					: wlServicesStatusModel.getState().contentEquals("STATE_ACTIVE") ? "UP" : "DOWN"));

		}

		return eurekaWLCollectionModelList;
	}

	private List<EurekaWeblogicServicesStatusModel> invokeEurekaServices(String product) {
		List<EurekaWeblogicServicesStatusModel> eurekaWLCollectionModelList = new ArrayList<>();

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(this.eodConfigTblHashmap.get(product + "_EUREKA_API").trim());
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

		return eurekaWLCollectionModelList;
	}

	private EurekaWeblogicServicesStatusModel buildEurekaWeblogicServicesStatusModel(Application application) {
		EurekaWeblogicServicesStatusModel statusModel = new EurekaWeblogicServicesStatusModel();
		int eurInstcnt = 0;
		String failedEurekaInsts = "";
		// String webLogicStatus =
		// this.wLStatusofAppsHashmap.getOrDefault(application.getName().toUpperCase(),
		// "DOWN");
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
		return statusModel;
	}

	@Override
	public ResponseEntity<List<EodErrorLogModel>> getEodErrorLogs() {
		ResponseEntity<List<EodErrorLogModel>> responseDetail = null;
		List<EodErrorLogModel> eodErrorLogModelList = EodErrorTrackerUtils.convertEntityListToVOList(
				this.tblEodErrorTrackerRepository.findAll(Sort.by(Sort.Direction.ASC, "srNo")));
		responseDetail = ok(eodErrorLogModelList);
		return responseDetail;
	}

	@Override
	public ResponseEntity<List<EodErrorLogModel>> saveEodErrors(EodSaveErrorLogModel eodSaveErrorLogModel) {
		this.tblEodErrorTrackerRepository
				.saveAndFlush(EodErrorTrackerUtils.convertErroLogToEntity(eodSaveErrorLogModel));

		ResponseEntity<List<EodErrorLogModel>> responseDetail = null;
		List<EodErrorLogModel> eodErrorLogModelList = EodErrorTrackerUtils.convertEntityListToVOList(
				this.tblEodErrorTrackerRepository.findAll(Sort.by(Sort.Direction.ASC, "srNo")));
		responseDetail = ok(eodErrorLogModelList);

		return responseDetail;
	}

	@Override
	public /* ResponseEntity<EodUserDetailsResModel> */ResponseEntity<String> saveUserDetails(
			EodUserDetailsReqModel eodUserDetailsReqModel) {
		// ResponseEntity<EodUserDetailsResModel> responseDetail = null;
		ResponseEntity<String> responseDetail = ok("Success");
		Optional<TblEodUserDetails> tblEodUserDets = this.tblEodUserDetailsRepository
				.findById(eodUserDetailsReqModel.getEmail());

		if (tblEodUserDets.isPresent()) {
			debug("User " + tblEodUserDets.get().getUserName() + " is already registered");
			/* EodUserDetailsUtils.errDesc */String errDesc = "User '" + tblEodUserDets.get().getUserName()
					+ "' with Email '" + tblEodUserDets.get().getEmail_id() + "' is already registered";
			/*
			 * responseDetail = ok(EodUserDetailsUtils
			 * .convertEntityToRespVO(EodUserDetailsUtils.convertVOToEntity(
			 * eodUserDetailsReqModel)));
			 */
			// responseDetail = ok(errDesc,HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<String>(errDesc, HttpStatus.OK);
		}
		eodUserDetailsReqModel.setPassword(this.passwordEncoder.encode(eodUserDetailsReqModel.getPassword()));
		TblEodUserDetails tblEodUserDetails = this.tblEodUserDetailsRepository
				.saveAndFlush(EodUserDetailsUtils.convertVOToEntity(eodUserDetailsReqModel));

		// responseDetail =
		// ok(EodUserDetailsUtils.convertEntityToRespVO(tblEodUserDetails));
		return responseDetail;
	}

	@Override
	public ResponseEntity<?> loginInfo(UserLoginDto userLoginDto) throws Exception {
		String jwtToken = null;
		UserLoginRespDto userLoginResponse = new UserLoginRespDto();
		try {

			final UserDetails userDetails = this.customUserDetailsService
					.loadUserByUsername(userLoginDto.getUsername());

			this.authManager.authenticate(
					new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

			debug("Authorization completed");

			jwtToken = this.jwtUtils.generateToken(userDetails);
			userLoginResponse.setStatus("Success");
			userLoginResponse.setToken(jwtToken);
			userLoginResponse.setData(EodUserDetailsUtils.convertEntityToRespVO(
					this.tblEodUserDetailsRepository.findUserByEmail(userLoginDto.getUsername())));

		} catch (UsernameNotFoundException e) {
			debug("Inside UsernameNotFoundException");
			userLoginResponse
					.setStatus(new String("User email '" + userLoginDto.getUsername() + "' is not registered"));
			return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
			/*
			 * return new ResponseEntity<>(new String("User email '" +
			 * userLoginDto.getUsername() + "' is not registered"), HttpStatus.FORBIDDEN);
			 */

		} catch (BadCredentialsException e) {
			debug("Inside BadCredentialsException");
			userLoginResponse.setStatus(new String("Bad Credentials"));
			return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);

		} catch (Exception e) {
			debug("Inside exception 2" + e.getMessage());
			userLoginResponse.setStatus(e.getMessage());
			return new ResponseEntity<>(userLoginResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			// ok(new String(e.getMessage()));
		}

		return ResponseEntity.ok(userLoginResponse); // Response entity with Token may come later
	}

	@Override
	public ResponseEntity<ApiHealthCheckResponseModel> checkApiHealth(String apiName) {

		Optional<TblApihealthcheckDetails> tblApiDetails = this.tblApiHealthcheckDetRepository.findById(apiName);
		ApiHealthCheckResponseModel response = new ApiHealthCheckResponseModel();
		CallWebserviceRest callWebserviceRest = new CallWebserviceRest();

		response.setApiName(apiName);
		String[] serviceResponse = new String[4];

		try {

			switch (tblApiDetails.get().getReqMethod().toUpperCase()) {
			case "GET":
				System.out.println("Get request");

				serviceResponse = callWebserviceRest.getRequest(tblApiDetails.get().getReqMethod(),
						tblApiDetails.get().getRequestHeader(), tblApiDetails.get().getRequestParam(),
						tblApiDetails.get().getRequestBody(), tblApiDetails.get().getUrl());
				break;
			case "POST":
				serviceResponse = callWebserviceRest.postRequest(tblApiDetails.get().getReqMethod(),
						tblApiDetails.get().getRequestHeader(), tblApiDetails.get().getRequestParam(),
						tblApiDetails.get().getRequestBody(), tblApiDetails.get().getUrl());
				break;
			case "PUT":

				break;
			case "PATCH":

				break;
			default:

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to invoke the API:" + e.getMessage());
		}

		HttpStatus httpStatus = HttpStatus.resolve(Integer.valueOf(serviceResponse[0]));
		response.setStatus(httpStatus.toString());
		response.setResponseTime(serviceResponse[2]);
		response.setResponseSize(serviceResponse[3]);
		return new ResponseEntity<ApiHealthCheckResponseModel>(response, httpStatus);
	}

	@Override
	public ResponseEntity<SaveExternalApiDetResponse> saveExternalApiDetails(SaveExternalApiDetails apiDetails) {

		SaveExternalApiDetResponse responseDetail = new SaveExternalApiDetResponse();
		Optional<TblApihealthcheckDetails> tblApiDetails = this.tblApiHealthcheckDetRepository
				.findById(apiDetails.getUrl());

		if (tblApiDetails.isPresent()) {
			System.out.println("Api Name " + tblApiDetails.get().getApiName() + " is already saved");
			/* EodUserDetailsUtils.errDesc */String errDesc = "Api Name '" + tblApiDetails.get().getApiName()
					+ "' with URL '" + tblApiDetails.get().getUrl() + "' is already registered";

			responseDetail.setStatus(errDesc);
			return new ResponseEntity<SaveExternalApiDetResponse>(responseDetail, HttpStatus.OK);
		}

		if (!validateApiDetails(apiDetails, responseDetail)) {
			// responseDetail.setStatus("Kindly enter valid JSON Request Body");
			return new ResponseEntity<SaveExternalApiDetResponse>(responseDetail, HttpStatus.OK);
		}

		TblApihealthcheckDetails tblApihealthcheckDetails = this.tblApiHealthcheckDetRepository
				.saveAndFlush(EodConfigUtils.convertVOToEntity(apiDetails));

		responseDetail.setStatus("Success");
		return new ResponseEntity<SaveExternalApiDetResponse>(responseDetail, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<SaveExternalApiDetResponse> modifyExternalApiDetails(SaveExternalApiDetails apiDetails) {

		SaveExternalApiDetResponse responseDetail = new SaveExternalApiDetResponse();
		Optional<TblApihealthcheckDetails> tblApiDetails = this.tblApiHealthcheckDetRepository
				.findById(apiDetails.getUrl());

		if (tblApiDetails.isPresent()) {

			if (!validateApiDetails(apiDetails, responseDetail)) {
				// responseDetail.setStatus("Kindly enter valid JSON Request Body");
				return new ResponseEntity<SaveExternalApiDetResponse>(responseDetail, HttpStatus.OK);
			}

			this.tblApiHealthcheckDetRepository.deleteById(apiDetails.getUrl());
			TblApihealthcheckDetails tblApihealthcheckDetails = this.tblApiHealthcheckDetRepository
					.saveAndFlush(EodConfigUtils.convertVOToEntity(apiDetails));

		}

		responseDetail.setStatus("Success");
		return new ResponseEntity<SaveExternalApiDetResponse>(responseDetail, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<SaveExternalApiDetResponse> deleteExternalApiDetails(String id) {
		SaveExternalApiDetResponse responseDetail = new SaveExternalApiDetResponse();
		this.tblApiHealthcheckDetRepository.deleteByCustomId(id);
		responseDetail.setStatus("Success");
		return new ResponseEntity<SaveExternalApiDetResponse>(responseDetail, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<List<ApiDetailsResponse>> fetchAllApiDetails() {
		// TODO Auto-generated method stub
		List<ApiDetailsResponse> resonseDetails = new ArrayList<ApiDetailsResponse>();

		resonseDetails = EodConfigUtils.convertApiEntityListToVOList(this.tblApiHealthcheckDetRepository.findAll());

		return new ResponseEntity<List<ApiDetailsResponse>>(resonseDetails, HttpStatus.OK);

	}

	private boolean validateApiDetails(SaveExternalApiDetails apiDetails, SaveExternalApiDetResponse responseDetail) {
		debug("Validation JSON requests");
		if (!ValidateJson.isValid(apiDetails.getRequestBody())) {
			debug("Kindly enter valid JSON Request Body, returning false");
			responseDetail.setStatus("Kindly enter valid JSON Request Body");
			return false;
		}

		if (!ValidateJson.isValid(apiDetails.getRequestHeader())) {
			debug("Kindly enter valid JSON Request Header, returning false");
			responseDetail.setStatus("Kindly enter valid JSON Request Header");
			return false;
		}

		if (!ValidateJson.isValid(apiDetails.getRequestParam())) {
			debug("Kindly enter valid JSON Request Param, returning false");
			responseDetail.setStatus("Kindly enter valid JSON Request Param");
			return false;
		}

		return true;
	}

	@Override
	public ResponseEntity<EurekaApplicationResponse> getEurekaStatus() {
		ResponseEntity<EurekaApplicationResponse> responseDetails;

		Instance instance = new Instance();
		instance.setPort(new Port());
		instance.setDataCenterInfo(new DataCenterInfo());
		instance.setSecurePort(new Port());

		Application application = new Application();
		application.addInstanceCollection(instance);
		application.setName("CMC-REPORT-SERVICES");

		Applications applications = new Applications();

		applications.addApplication(application);
		applications.setVersions__delta("1");
		applications.setApps__hashcode("UP_71_");

		EurekaApplicationResponse eurekaApplicationResponse = new EurekaApplicationResponse();
		eurekaApplicationResponse.setApplications(applications);

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://100.76.135.191:7017/plato-discovery-service/eureka/apps");
		LOGGER.info("URL getting called {} ", builder.toUriString());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity(headers);
		LOGGER.info("Entity is : {}", entity.toString());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EurekaApplicationResponse> ApplicationsResponse = null;
		ApplicationsResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				EurekaApplicationResponse.class, new Object[0]);
		debug("Total Services Deployed:" + ApplicationsResponse.getBody().getApplications().getApps__hashcode());

		responseDetails = ApplicationsResponse;
		String quote = "'";
		ApplicationsResponse.getBody().getApplications().getApplication()
				.forEach(action -> debug("INSERT INTO TBL_WLOGIC_EUREKA_CONFIG VALUES(" + quote + quote + "," + quote
						+ action.getName() + quote + "," + quote + action.getInstanceCollection().get(0).getInstanceId()
						+ quote + ");"));

		// responseDetails = ok(eurekaApplicationResponse);
		return responseDetails;
	}

}
