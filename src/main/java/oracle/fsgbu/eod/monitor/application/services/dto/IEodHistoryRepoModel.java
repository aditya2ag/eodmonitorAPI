package oracle.fsgbu.eod.monitor.application.services.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface IEodHistoryRepoModel {

	Double getMaxRunTime();

	String getEocStage();

	Date getBranchDate();

}
