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

import oracle.fsgbu.eod.monitor.application.services.dto.ApiDetailsResponse;
import oracle.fsgbu.eod.monitor.application.services.dto.EodConfigModel;
import oracle.fsgbu.eod.monitor.application.services.dto.SaveExternalApiDetails;
import oracle.fsgbu.eod.monitor.application.services.entities.TblApihealthcheckDetails;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.html.parser.Entity;

public class EodConfigUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(EodConfigUtils.class);

	public static EodConfigModel convertEntityToVO(TblEodConfig entity) {
		EodConfigModel vo = null;
		if (entity != null) {
			vo = new EodConfigModel();
			vo.setId(entity.getId());
			vo.setKey(entity.getKey());
			vo.setValue(entity.getValue());
		}
		return vo;
	}

	public static List<EodConfigModel> convertEntityListToVOList(List<TblEodConfig> entityList) {
		List<EodConfigModel> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertEntityToVO(entity)));
		return voList;
	}

	public static TblEodConfig convertVOToEntity(EodConfigModel vo) {
		TblEodConfig entity = null;
		if (vo != null) {
			entity = new TblEodConfig();
			entity.setId(vo.getId());
			entity.setKey(vo.getKey());
			entity.setValue(vo.getValue());
		}
		return entity;
	}

	public static String convertEntityListToString(List<TblEodConfig> entityList) {
		String voResponse = null;

		voResponse = "{\n" + convertEntityToVOList(entityList) + "\n}";
		System.out.println(voResponse);
		return voResponse;
	}

	private static String convertEntityToVOList(List<TblEodConfig> entityList) {
		String vo = "";
		String quote = "\"";

		for (int k = 0; k < entityList.size(); k++) {
			vo = vo.concat(
					(quote + entityList.get(k).getKey() + quote + ":" + quote + entityList.get(k).getValue() + quote));
			if (k < (entityList.size() - 1)) {
				vo = vo.concat(",\n");
			}

		}
		return vo;
	}

	public static HashMap<String, String> convertEntityListToHashmap(List<TblEodConfig> entityList) {
		HashMap<String, String> voResponse = new HashMap<String, String>();
		entityList.forEach(action -> voResponse.put(action.getKey(), action.getValue()));
		return voResponse;
	}

	
	public static TblApihealthcheckDetails convertVOToEntity(SaveExternalApiDetails vo) {
		TblApihealthcheckDetails entity = null;
		if (vo != null) {
			entity = new TblApihealthcheckDetails();
			entity.setApiName(vo.getApiName());
			entity.setUrl(vo.getUrl());
			entity.setReqMethod(vo.getReqMethod());
			entity.setRequestHeader(vo.getRequestHeader());
			entity.setRequestBody(vo.getRequestBody());
			entity.setRequestParam(vo.getRequestParam());

		}
		return entity;
	}
	
	public static ApiDetailsResponse convertEntityToVO(TblApihealthcheckDetails entity) {
		ApiDetailsResponse vo = null;
		if (entity != null) {
			vo = new ApiDetailsResponse();
			vo.setId(entity.getId());
			vo.setApiName(entity.getApiName());
			vo.setUrl(entity.getUrl());
		}
		return vo;
	}

	public static List<ApiDetailsResponse> convertApiEntityListToVOList(List<TblApihealthcheckDetails> entityList) {
		List<ApiDetailsResponse> voList = new ArrayList<>();
		entityList.forEach(entity -> voList.add(convertEntityToVO(entity)));
		return voList;
	}
	
	
}
