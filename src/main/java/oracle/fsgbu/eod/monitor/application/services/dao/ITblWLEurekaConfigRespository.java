package oracle.fsgbu.eod.monitor.application.services.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import oracle.fsgbu.eod.monitor.application.services.dto.ITblWLEurekaConfigModel;
import oracle.fsgbu.eod.monitor.application.services.dto.ITblWLEurekaInstancesModel;
import oracle.fsgbu.eod.monitor.application.services.entities.TblWLogicEurekaConfig;

//@Repository
public interface ITblWLEurekaConfigRespository extends JpaRepository<TblWLogicEurekaConfig, String> {

	@Query(value = "SELECT DISTINCT WL_APPNAME  AS WLAppName,EUREKA_APPNAME AS EurekaAppName FROM TBL_WLOGIC_EUREKA_CONFIG ORDER BY WL_APPNAME", nativeQuery = true)
	public List<ITblWLEurekaConfigModel> fetchWLEurekaMappings();

	@Query(value = "SELECT EUREKA_APPNAME AppNameinEureka,COUNT(EUREKA_INSTANCE) TotalInstCount FROM TBL_WLOGIC_EUREKA_CONFIG GROUP BY EUREKA_APPNAME", nativeQuery = true)
	public List<ITblWLEurekaInstancesModel> fetchTotalEurekaInstances();

}
