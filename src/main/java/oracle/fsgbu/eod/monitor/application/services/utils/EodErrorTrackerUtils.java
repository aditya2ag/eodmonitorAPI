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

import org.springframework.util.StringUtils;

import oracle.fsgbu.eod.monitor.application.services.dto.EodConfigModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodSaveErrorLogModel;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodConfig;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodErrorTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.parser.Entity;

public class EodErrorTrackerUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(EodErrorTrackerUtils.class);

	public static EodErrorLogModel convertEntityToVO(TblEodErrorTracker entity) {
		EodErrorLogModel vo = null;
		if (entity != null) {
			vo = new EodErrorLogModel();
			vo.setSrno(entity.getSrNo());
			vo.setDate(entity.getEodDate());
			vo.setUser(entity.getUserName());
			vo.setError(entity.getError());
			vo.setDescription(entity.getErrdesc());
			vo.setResolution(entity.getResolution());
		}
		return vo;
	}

	public static List<EodErrorLogModel> convertEntityListToVOList(List<TblEodErrorTracker> entityList) {
		List<EodErrorLogModel> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertEntityToVO(entity)));
		return voList;
	}

	public static TblEodErrorTracker convertVOToEntity(EodErrorLogModel vo) {
		TblEodErrorTracker entity = null;
		if (vo != null) {
			entity = new TblEodErrorTracker();
			entity.setSrNo(vo.getSrno());
			entity.setUserName(vo.getUser());
			entity.setEodDate(vo.getDate());
			entity.setError(vo.getError());
			entity.setErrdesc(vo.getDescription());
			entity.setResolution(vo.getResolution());
		}
		return entity;
	}

	public static TblEodErrorTracker convertErroLogToEntity(EodSaveErrorLogModel eodErrorLogModel) {
		TblEodErrorTracker vo = null;
		if (eodErrorLogModel != null) {
			vo = new TblEodErrorTracker();
			vo.setEodDate(eodErrorLogModel.getDate());
			vo.setUserName(eodErrorLogModel.getUser());
			vo.setError(eodErrorLogModel.getError());
			vo.setErrdesc(eodErrorLogModel.getDescription());
			vo.setResolution(eodErrorLogModel.getResolution());
		}
		return vo;
	}

	public static List<TblEodErrorTracker> convertVOErroListToEntityList(List<EodSaveErrorLogModel> eodErrorLogModel) {
		List<TblEodErrorTracker> voList = new ArrayList<>();
		eodErrorLogModel.forEach(entity -> voList.add(convertErroLogToEntity(entity)));
		return voList;
	}

}
