package oracle.fsgbu.eod.monitor.application.services.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IEodCurrentRunningBatchRepoModel {

	Integer getId();

	String getBranchcode();

	String getEocstage();

	String getEocbatch();

	String getEocbatchstatus();

	LocalDateTime getStarttime();

	LocalDateTime getEndtime();

	//LocalDateTime getDuration();
	String getDuration();
}
