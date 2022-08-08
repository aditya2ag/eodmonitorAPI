package oracle.fsgbu.eod.monitor.application.services.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface IEodHistoryRepoModel {

	Integer getMaxRunTime();

	String getEocStage();

	Date getBranchDate();

}