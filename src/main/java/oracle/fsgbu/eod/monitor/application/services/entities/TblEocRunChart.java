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
@Table(name = "AETB_EOC_RUNCHART")
@NamedQueries({ @NamedQuery(name = "TblEocRunChart.findAll", query = "SELECT c FROM TblEocRunChart c") })
public class TblEocRunChart implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BRANCH_CODE")
	private String branchCode;

	@Column(name = "EOD_DATE")
	private LocalDate eodDate;

	@Column(name = "EOC_STAGE")
	private String eocStage;

	@Column(name = "EOC_STAGE_STATUS")
	private String eocStageStatus;

	@Column(name = "EOC_SEQ_NO")
	private int eocSeqNo;

	public int getEocSeqNo() {
		return eocSeqNo;
	}

	public void setEocSeqNo(int eocSeqNo) {
		this.eocSeqNo = eocSeqNo;
	}

	public String getEocStageStatus() {
		return eocStageStatus;
	}

	public void setEocStageStatus(String eocStageStatus) {
		this.eocStageStatus = eocStageStatus;
	}

	public int getEocBatchStatusCount() {
		return eocBatchStatusCount;
	}

	public void setEocBatchStatusCount(int eocBatchStatusCount) {
		this.eocBatchStatusCount = eocBatchStatusCount;
	}

	private int eocBatchStatusCount;

	public TblEocRunChart() {
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public LocalDate getEodDate() {
		return eodDate;
	}

	public void setEodDate(LocalDate eodDate) {
		this.eodDate = eodDate;
	}

	public String getEocStage() {
		return eocStage;
	}

	public void setEocStage(String eocStage) {
		this.eocStage = eocStage;
	}

	@Override
	public String toString() {
		return "TblEocRunChart [eodDate=" + eodDate + ", eocStage=" + eocStage + ", eocStageStatus=" + eocStageStatus
				+ "]";
	}

}
