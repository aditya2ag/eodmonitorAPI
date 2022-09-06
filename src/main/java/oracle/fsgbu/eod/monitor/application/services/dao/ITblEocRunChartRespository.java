package oracle.fsgbu.eod.monitor.application.services.dao;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.bytebuddy.asm.Advice.Local;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodCurrentAbortedBatchRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodCurrentRunningBatchRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodHistoryRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodStatusRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodTopRunningBatchRepoModel;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEocRunChart;

//@Repository
public interface ITblEocRunChartRespository extends JpaRepository<TblEocRunChart, String> {

/*	@Query(value = "SELECT count(e.EOC_STAGE_STATUS) as BatchCount, e.EOC_STAGE_STATUS as EocBatchStatus FROM AETB_EOC_RUNCHART e WHERE e.eod_date = :eodDate GROUP BY e.EOC_STAGE_STATUS", nativeQuery = true)
	public List<IEodStatusRepoModel> fetchBatchStatus(@Param("eodDate") LocalDate eodDate);
*/
	@Query(value = "SELECT count(e.EOC_STATUS) as BatchCount, e.EOC_STATUS as EocBatchStatus FROM AETB_EOC_BRANCHES e WHERE e.eod_date = :eodDate GROUP BY e.EOC_STATUS", nativeQuery = true)
	public List<IEodStatusRepoModel> fetchBatchStatus(@Param("eodDate") LocalDate eodDate);

	
	@Query(value ="SELECT (row_number() OVER(PARTITION BY '' ORDER BY branch_code)) AS Id, branch_code AS Branchcode, eoc_stage AS Eocstage, eoc_batch AS Eocbatch, eoc_batch_status AS Eocbatchstatus, start_time AS Starttime, end_time AS Endtime, ROUND(((end_time - start_time) * 24 * 60),1) AS Duration from aetb_eoc_programs order by branch_code,eoc_stage_seq,eoc_batch_seq", nativeQuery = true) 
	public List<IEodCurrentRunningBatchRepoModel> fetchAllBatcheStatus();
	 
	//Aborted batches only.
	@Query(value = "SELECT (row_number() OVER(PARTITION BY '' ORDER BY branch_code)) AS Id, branch_code AS Branchcode, eoc_stage AS Eocstage, eoc_batch AS Eocbatch, eoc_batch_status AS Eocbatchstatus, start_time AS Starttime, end_time AS Endtime,ROUND(((nvl((((24 * 60) * EXTRACT(DAY FROM(end_time - start_time)  DAY(9) TO SECOND)) + (60 * EXTRACT(HOUR FROM(end_time - start_time) DAY(9) TO SECOND)) + EXTRACT(MINUTE FROM(end_time - start_time) DAY(9) TO SECOND)) * 60, 0) + nvl(EXTRACT(SECOND FROM(end_time - start_time) DAY(9) TO SECOND),0)) / 60), 1) AS Duration, error_code AS Error, error AS ErrorDesc from aetb_eoc_programs where nvl(eoc_batch_status,'N') IN ('A') and start_time IS NOT NULL order by branch_code,eoc_stage_seq,eoc_batch_seq", nativeQuery = true)
	public List<IEodCurrentAbortedBatchRepoModel> fetchCurrentAbortedBatches();
	

	//TopRunning Batches
	//@Query(value = "SELECT tbl_main.Id Id,  tbl_main.Branchcode Branchcode,  tbl_main.Eocstage Eocstage,  tbl_main.Eocbatch Eocbatch,  tbl_main.Eocbatchstatus Eocbatchstatus,  tbl_main.Starttime Starttime,  tbl_main.Endtime Endtime,  tbl_main.Duration Duration,  NVL(tbl_main.DailyAvg, 0) Dailyavg,  NVL(tbl_mothlyAvg.MonthlyAvg,0) Monthlyavg,  0 Holidayavg  FROM (SELECT branch_code AS Branchcode,eoc_stage AS Eocstage,eoc_batch AS Eocbatch,ROUND((SUM(ROUND(((nvl((((24 * 60) *   EXTRACT(DAY   FROM(end_time - start_time)   DAY(9) TO SECOND)) +   (60 * EXTRACT(HOUR FROM(end_time -   start_time) DAY(9) TO   SECOND)) +   EXTRACT(MINUTE  FROM(end_time - start_time)  DAY(9) TO SECOND)) * 60,   0) +  nvl(EXTRACT(SECOND FROM(end_time - start_time) DAY(9) TO SECOND),   0)) / 60),  1)) / 6), 4) AS MonthlyAvg FROM aetb_eoc_programs_history hi    WHERE eoc_batch_status = 'C' AND eod_date IN :monthendClause GROUP BY branch_code, eoc_stage, eoc_batch) tbl_mothlyAvg RIGHT OUTER JOIN (SELECT maint_tbl.Id Id, maint_tbl.Branchcode Branchcode, maint_tbl.Eocstage Eocstage, maint_tbl.Eocbatch Eocbatch, maint_tbl.Eocbatchstatus Eocbatchstatus, maint_tbl.Starttime Starttime, maint_tbl.Endtime Endtime, maint_tbl.Duration Duration, NVL(hist_tbl.DailyAvg, 0) Dailyavg, 0 Monthlyavg, 0 Holidayavg FROM (SELECT branch_code AS Branchcode,    eoc_stage AS Eocstage,    eoc_batch AS Eocbatch,    ROUND((SUM(ROUND(((nvl((((24 * 60) *  EXTRACT(DAY    FROM(end_time -    start_time)    DAY(9) TO    SECOND)) +  (60 *  EXTRACT(HOUR    FROM(end_time -    start_time)    DAY(9) TO    SECOND)) +  EXTRACT(MINUTE   FROM(end_time -   start_time)   DAY(9) TO   SECOND)) * 60,  0) + nvl(EXTRACT(SECOND  FROM(end_time -  start_time)  DAY(9) TO  SECOND),  0)) / 60), 1)) / 30),4) AS DailyAvg    FROM aetb_eoc_programs_history hi   WHERE eoc_batch_status = 'C'AND eod_date >=    ((SELECT today   FROM sttm_dates  WHERE branch_code = hi.branch_code) - 30)   GROUP BY branch_code, eoc_stage, eoc_batch) hist_tbl RIGHT OUTER JOIN (SELECT (row_number()OVER(PARTITION BY '' ORDER BY branch_code)) AS Id,    branch_code AS Branchcode,    eoc_stage AS Eocstage,    eoc_batch AS Eocbatch,    eoc_batch_status AS Eocbatchstatus,    start_time AS Starttime,    end_time AS Endtime,    ROUND(((nvl((((24 * 60) * EXTRACT(DAY   FROM(NVL(end_time,SYSDATE) -   start_time)   DAY(9) TO   SECOND)) + (60 * EXTRACT(HOUR   FROM(NVL(end_time,SYSDATE) -   start_time)   DAY(9) TO   SECOND)) + EXTRACT(MINUTE  FROM(NVL(end_time,SYSDATE) -  start_time)   DAY(9) TO  SECOND)) * 60, 0) +nvl(EXTRACT(SECOND FROM(NVL(end_time,SYSDATE) - start_time) DAY(9) TO SECOND), 0)) / 60),1) AS Duration    FROM aetb_eoc_programs   WHERE eoc_batch_status = 'W'   ORDER BY branch_code,  eoc_stage_seq,  eoc_batch_seq) maint_tbl   ON (hist_tbl.Branchcode = maint_tbl.Branchcode AND hist_tbl.Eocstage = maint_tbl.Eocstage AND hist_tbl.Eocbatch = maint_tbl.Eocbatch)) tbl_main ON (tbl_mothlyAvg.Branchcode = tbl_main.Branchcode AND tbl_mothlyAvg.Eocstage = tbl_main.Eocstage AND tbl_mothlyAvg.Eocbatch = tbl_main.Eocbatch)", nativeQuery = true)
	//public List<IEodTopRunningBatchRepoModel> fetchTopRunningBatches(@Param("monthendClause") List<LocalDate> monthendClause);

	@Query(value = "SELECT tbl_main.Id Id, tbl_main.Branchcode Branchcode, tbl_main.Eocstage Eocstage, tbl_main.Eocbatch Eocbatch, tbl_main.Eocbatchstatus Eocbatchstatus, tbl_main.Starttime Starttime, tbl_main.Endtime Endtime, tbl_main.Duration Duration, NVL(tbl_main.DailyAvg, 0) Dailyavg, NVL(tbl_mothlyAvg.MonthlyAvg, 0) Monthlyavg, tbl_main.sessionId SessionId, tbl_main.serialNo SerialNo, 0 Holidayavg   FROM (SELECT branch_code AS Branchcode,  eoc_stage AS Eocstage,  eoc_batch AS Eocbatch,  ROUND((SUM(ROUND(((end_time - start_time) * 24 * 60),     1)) / 6),4) AS MonthlyAvg    FROM aetb_eoc_programs_history hi   WHERE eoc_batch_status = 'C' AND eod_date IN :monthendClause GROUP BY branch_code, eoc_stage, eoc_batch) tbl_mothlyAvg  RIGHT OUTER JOIN (SELECT maint_tbl.Id Id, maint_tbl.Branchcode Branchcode, maint_tbl.Eocstage Eocstage, maint_tbl.Eocbatch Eocbatch,maint_tbl.Eocbatchstatus Eocbatchstatus,maint_tbl.Starttime Starttime,maint_tbl.Endtime Endtime,maint_tbl.Duration Duration,NVL(hist_tbl.DailyAvg, 0) Dailyavg,maint_tbl.sessionId sessionId,maint_tbl.serialNo serialNo,0 Monthlyavg,0 Holidayavg FROM (SELECT branch_code AS Branchcode,       eoc_stage AS Eocstage,       eoc_batch AS Eocbatch,       ROUND((SUM(ROUND(((end_time - start_time) * 24 * 60),1)) / 30),4) AS DailyAvg FROM aetb_eoc_programs_history hi WHERE eoc_batch_status = 'C'  AND eod_date >=       ((SELECT today   FROM sttm_dates  WHERE branch_code = hi.branch_code) - 30)GROUP BY branch_code, eoc_stage, eoc_batch) hist_tbl       RIGHT OUTER JOIN (SELECT (row_number() OVER(PARTITION BY '' ORDER BY branch_code)) AS Id, branch_code AS Branchcode,eoc_stage AS Eocstage, eoc_batch AS Eocbatch,eoc_batch_status AS Eocbatchstatus,start_time AS Starttime, end_time AS Endtime, ROUND(((NVL(end_time,sysdate) - start_time) * 24 * 60), 1) AS Duration,cu.session_id sessionId,cu.serial_no serialNo FROM aetb_eoc_programs cu WHERE eoc_batch_status = 'W' ORDER BY branch_code, eoc_stage_seq, eoc_batch_seq) maint_tbl ON (hist_tbl.Branchcode = maint_tbl.Branchcode AND  hist_tbl.Eocstage = maint_tbl.Eocstage AND  hist_tbl.Eocbatch = maint_tbl.Eocbatch)) tbl_main ON (tbl_mothlyAvg.Branchcode = tbl_main.Branchcode AND  tbl_mothlyAvg.Eocstage = tbl_main.Eocstage AND tbl_mothlyAvg.Eocbatch = tbl_main.Eocbatch)", nativeQuery = true)
	public List<IEodTopRunningBatchRepoModel> fetchTopRunningBatches(@Param("monthendClause") List<LocalDate> monthendClause);

	
	/*
	 * @Query(value =
	 * "SELECT MAX(actual_eoc_run_time) MaxRunTime,eoc_stage EocStage,branch_date BranchDate FROM AETB_EOC_STAGE_RUNTIME_CU WHERE branch_date BETWEEN (branch_date - :eodHistoryDays) AND branch_date GROUP BY eoc_stage, branch_date ORDER BY eoc_stage, branch_date"
	 * , nativeQuery = true) public List<IEodHistoryRepoModel>
	 * fetchEodHistoryData(@Param("eodHistoryDays") Integer eodHistoryDays);
	 */
	
	/*
	 * @Query(value =
	 * "SELECT MAX(stage_run_time) MaxRunTime,eoc_stage EocStage,branch_date BranchDate FROM aevw_eoc_stage_runtime t WHERE branch_date BETWEEN ((select prev_working_day from sttm_dates WHERE branch_code = t.branch_code) - :eodHistoryDays) AND branch_date AND branch_code IN ('020', '021', '022') GROUP BY eoc_stage, branch_date ORDER BY eoc_stage, branch_date"
	 * , nativeQuery = true) public List<IEodHistoryRepoModel>
	 * fetchEodHistoryData(@Param("eodHistoryDays") Integer eodHistoryDays);
	 */
	
	@Query(value = "SELECT MAX(stage_run_time) MaxRunTime,eoc_stage EocStage,branch_date BranchDate FROM aevw_eoc_stage_runtime t WHERE branch_date BETWEEN ((select prev_working_day from sttm_dates WHERE branch_code = t.branch_code) - :eodHistoryDays) AND branch_date AND branch_code IN :historyBrns GROUP BY eoc_stage, branch_date ORDER BY eoc_stage, branch_date", nativeQuery = true)
	public List<IEodHistoryRepoModel> fetchEodHistoryData(@Param("eodHistoryDays") Integer eodHistoryDays,@Param("historyBrns") List<String> historyBrns);
	
	
	@Query(value ="SELECT today FROM sttm_dates WHERE branch_code = (SELECT ho_branch FROM sttm_bank)",nativeQuery=true)
	public Timestamp fetchCoreSystemDate();
	
	
	/*Temp code*/
	
	@Query(value ="SELECT value FROM tbl_eod_config WHERE key = 'HistoryBranches' ",nativeQuery=true)
	public String historyBranches();
	
	
}
