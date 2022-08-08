/*******************************************************************************
 * This source is part of the Oracle Banking Credit Facility Process Management Software Product.
 * Copyright (c) 2021 , 2022 , Oracle and/or its affiliates.  All rights reserved.
 *
 * No part of this work may be reproduced, stored in a retrieval system, adopted or transmitted in any form or by any means,
 * electronic, mechanical, photographic, graphic, optic recording or otherwise,
 * translated in any language or computer language, without the prior written permission of Oracle and/or its affiliates.
 *
 * Oracle Financial Services Software Limited.
 * Oracle Park, Off Western Express Highway,
 * Goregaon (East),
 * Mumbai - 400 063, India.
 ******************************************************************************/
package oracle.fsgbu.eod.monitor.application.services.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.fsgbu.eod.monitor.application.services.dto.EodCurrentRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodHistoryModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodTopRunningBatchModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodCurrentRunningBatchRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodHistoryRepoModel;
import oracle.fsgbu.eod.monitor.application.services.dto.IEodTopRunningBatchRepoModel;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEocRunChart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EodCurrentRunningBatchUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(EodCurrentRunningBatchUtils.class);

	private static void debug(String msg) {
		LOGGER.debug("EodCurrentRunningBatchUtils-->" + msg);
		System.out.println("EodCurrentRunningBatchUtils-->" + msg);
	}
	
	public static EodCurrentRunningBatchModel convertEntityToVO(TblEocRunChart entity) {
		EodCurrentRunningBatchModel vo = null;
		if (entity != null) {
			vo = new EodCurrentRunningBatchModel();
			vo.setEocstage(entity.getEocStage());
			vo.setBranchcode(entity.getBranchCode());

		}
		return vo;
	}

	public static List<EodCurrentRunningBatchModel> convertEntityListToVOList(List<TblEocRunChart> entityList) {
		List<EodCurrentRunningBatchModel> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertEntityToVO(entity)));
		return voList;
	}

	public static TblEocRunChart convertVOToEntity(EodCurrentRunningBatchModel vo) {
		TblEocRunChart entity = null;
		if (vo != null) {
			entity = new TblEocRunChart();
			entity.setEocStage(vo.getEocstage());
			entity.setBranchCode(vo.getBranchcode());
		}
		return entity;
	}

	public static List<EodCurrentRunningBatchModel> convertIEntityListToVOList(
			List<IEodCurrentRunningBatchRepoModel> entityList) {
		List<EodCurrentRunningBatchModel> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertIEntityToVO(entity)));
		return voList;
	}

	private static EodCurrentRunningBatchModel convertIEntityToVO(IEodCurrentRunningBatchRepoModel entity) {
		EodCurrentRunningBatchModel vo = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Duration duration = null;
		if (entity != null) {

			/*
			 * if (null != entity.getStarttime() && null != entity.getEndtime()) { duration
			 * = Duration.between(entity.getStarttime(), entity.getEndtime()); }
			 */
			vo = new EodCurrentRunningBatchModel();
			vo.setId(entity.getId());
			vo.setEocbatchstatus(entity.getEocbatchstatus());
			vo.setEocbatch(entity.getEocbatch());
			vo.setEocstage(entity.getEocstage());
			vo.setBranchcode(entity.getBranchcode());
			if (null != entity.getStarttime()) {
				vo.setStarttime(formatter.format(entity.getStarttime()));
			}
			if (null != entity.getEndtime()) {
				vo.setEndtime(formatter.format(entity.getEndtime()));
			}
			/*
			 * if (null != duration) vo.setDuration(duration.toString());
			 */

			vo.setDuration(entity.getDuration());

			// data to be populated later
			vo.setMonthlyavg("0");
			vo.setDailyavg("0");
			vo.setHolidayavg("0");

			// debug("Start time:"
			// +LocalDateTime.parse(entity.getStarttime().format(formatter), formatter));
			// debug("duration:"+duration.getSeconds());
		}
		return vo;
	}

	public static List<EodTopRunningBatchModel> convertITopRunningBatchEntityListToVOList(
			List<IEodTopRunningBatchRepoModel> entityList) {
		List<EodTopRunningBatchModel> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertIEntityToVO(entity)));
		return voList;
	}

	private static EodTopRunningBatchModel convertIEntityToVO(IEodTopRunningBatchRepoModel entity) {
		EodTopRunningBatchModel vo = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (entity != null) {
			vo = new EodTopRunningBatchModel();
			vo.setId(entity.getId());
			vo.setEocbatchstatus(entity.getEocbatchstatus());
			vo.setEocbatch(entity.getEocbatch());
			vo.setEocstage(entity.getEocstage());
			vo.setBranchcode(entity.getBranchcode());
			if (null != entity.getStarttime()) {
				vo.setStarttime(formatter.format(entity.getStarttime()));
			}
			if (null != entity.getEndtime()) {
				vo.setEndtime(formatter.format(entity.getEndtime()));
			}
			/*
			 * if (null != duration) vo.setDuration(duration.toString());
			 */

			vo.setDuration(entity.getDuration());
			vo.setDailyavg(entity.getDailyavg());
			vo.setMonthlyavg(entity.getMonthlyavg());
			vo.setHolidayavg(entity.getHolidayavg());

			// debug("Start time:"
			// +LocalDateTime.parse(entity.getStarttime().format(formatter), formatter));
			// debug("duration:"+duration.getSeconds());
		}
		return vo;
	}

	public static HashMap<String, Object> /* List<EodHistoryModel> */ convertIHistoryEntityListToVOList(
			List<IEodHistoryRepoModel> entityList) {
		List<EodHistoryModel> voList = new ArrayList<>();
		HashMap<String, Object> voListHashMap = new HashMap<String, Object>();

		// entityList.forEach(entity -> voList.add(convertIEntityToVO(entity)));
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		String prevEocStage = null;
		List<Integer> data = new ArrayList<Integer>();
		List<String> categoryDate = new ArrayList<String>();
		boolean uniqueDatesDone = false;
		for (IEodHistoryRepoModel eodHistoryRepoModel : entityList) {
			debug(eodHistoryRepoModel.getBranchDate().toString());
			if (!uniqueDatesDone) {
				categoryDate.add(formatter.format(eodHistoryRepoModel.getBranchDate()));
			}

			if (null == prevEocStage) {
				prevEocStage = eodHistoryRepoModel.getEocStage();
				data.add(eodHistoryRepoModel.getMaxRunTime());
				continue;
			}

			if (prevEocStage.contentEquals(eodHistoryRepoModel.getEocStage())) {
				data.add(eodHistoryRepoModel.getMaxRunTime());
				debug("prevEocStage<-->eodHistoryRepoModel.getEocStage()" + prevEocStage + "<-->"
						+ eodHistoryRepoModel.getEocStage());
			} else {
				debug("eodHistoryRepoModel.getEocStage()" + eodHistoryRepoModel.getEocStage());
				voList.add(convertIEntityToVO(eodHistoryRepoModel, data));
				uniqueDatesDone = true;
				prevEocStage = eodHistoryRepoModel.getEocStage();
				// data.clear();
				data = new ArrayList<Integer>();
				data.add(eodHistoryRepoModel.getMaxRunTime());

			}
		}
		voListHashMap.put("EodHistoryModel", voList);
		voListHashMap.put("categoryDate", categoryDate);
		return voListHashMap;
	}

	private static EodHistoryModel convertIEntityToVO(IEodHistoryRepoModel entity, List<Integer> data) {
		EodHistoryModel vo = null;
		debug("Construction Done for stage:" + entity.getEocStage());
		if (entity != null) {
			vo = new EodHistoryModel();
			vo.setName(entity.getEocStage());
			vo.setData(data);
		}
		return vo;
	}

}
