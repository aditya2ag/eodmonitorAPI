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
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsReqModel;
import oracle.fsgbu.eod.monitor.application.services.dto.EodUserDetailsResModel;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodConfig;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodErrorTracker;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodUserDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.parser.Entity;

public class EodUserDetailsUtils {
	
	public static String errDesc = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(EodUserDetailsUtils.class);
	
	
	public static EodUserDetailsReqModel convertEntityToVO(TblEodUserDetails entity) {
		EodUserDetailsReqModel vo = null;
		if (entity != null) {
			vo = new EodUserDetailsReqModel();
			vo.setName(entity.getUserName());
			vo.setEmail(entity.getEmail_id());
			vo.setPassword(entity.getPassword());
			vo.setRole(entity.getRole());
			vo.setUserRole(entity.getUserRole());
			vo.setLocation(entity.getLocation());
		}
		return vo;
	}

	public static List<EodUserDetailsReqModel> convertEntityListToVOList(List<TblEodUserDetails> entityList) {
		List<EodUserDetailsReqModel> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertEntityToVO(entity)));
		return voList;
	}

	public static EodUserDetailsResModel convertEntityToRespVO(TblEodUserDetails entity) {
		EodUserDetailsResModel vo = null;
		if (entity != null) {
			vo = new EodUserDetailsResModel();
			vo.setName(entity.getUserName());
			vo.setEmail(entity.getEmail_id());
			vo.setRole(entity.getRole());
			vo.setUserRole(entity.getUserRole());
			vo.setLocation(entity.getLocation());
			//vo.setErrorDesc(errDesc);
		}
		return vo;
	}
	
	public static TblEodUserDetails convertVOToEntity(EodUserDetailsReqModel vo) {
		TblEodUserDetails entity = null;
		if (vo != null) {
			entity = new TblEodUserDetails();
			entity.setUserName(vo.getName());
			entity.setEmail_id(vo.getEmail());
			entity.setPassword(vo.getPassword());
			entity.setRole(vo.getRole());
			entity.setUserRole(vo.getUserRole());
			entity.setLocation(vo.getLocation());
			entity.setRecordStat("O");
		}
		return entity;
	}

	/*
	 * 
	 * public static List<TblEodErrorTracker>
	 * convertVOErroListToEntityList(List<EodErrorLogModel> eodErrorLogModel) {
	 * List<TblEodErrorTracker> voList = new ArrayList<>();
	 * eodErrorLogModel.forEach(entity -> voList.add(convertVOToEntity(entity)));
	 * return voList; }
	 */

}
