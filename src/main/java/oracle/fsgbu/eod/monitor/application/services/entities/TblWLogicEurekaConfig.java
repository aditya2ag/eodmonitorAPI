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
package oracle.fsgbu.eod.monitor.application.services.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

/**
 * The persistent class for the ACCOUNTBALANCE database table.
 *
 */
@Entity
@Table(name = "TBL_WLOGIC_EUREKA_CONFIG")
@NamedQueries({ @NamedQuery(name = "TblWLogicEurekaConfig.findAll", query = "SELECT c FROM TblWLogicEurekaConfig c") })
public class TblWLogicEurekaConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "WL_APPNAME")
	private String weblogicAppName;

	@Column(name = "EUREKA_APPNAME")
	private LocalDate eurekaAppName;

	@Column(name = "EUREKA_INSTANCE")
	private String eurekaInstance;

	public String getWeblogicAppName() {
		return weblogicAppName;
	}

	public void setWeblogicAppName(String weblogicAppName) {
		this.weblogicAppName = weblogicAppName;
	}

	public LocalDate getEurekaAppName() {
		return eurekaAppName;
	}

	public void setEurekaAppName(LocalDate eurekaAppName) {
		this.eurekaAppName = eurekaAppName;
	}

	public String getEurekaInstance() {
		return eurekaInstance;
	}

	public void setEurekaInstance(String eurekaInstance) {
		this.eurekaInstance = eurekaInstance;
	}

	@Override
	public String toString() {
		return "TblWLogicEurekaConfig [weblogicAppName=" + weblogicAppName + ", eurekaAppName=" + eurekaAppName
				+ ", eurekaInstance=" + eurekaInstance + "]";
	}

	

}
