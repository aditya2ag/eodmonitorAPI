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
@Table(name = "TBL_EOD_ERROR_TRACKER")
@NamedQueries({ @NamedQuery(name = "TblEodErrorTracker.findAll", query = "SELECT c FROM TblEodErrorTracker c") })
public class TblEodErrorTracker implements Serializable {
	private static final long serialVersionUID = 1L;
	//@Id
	@Column(name = "ID")
	private String id = UUID.randomUUID().toString();;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERRORLOG_SEQUENCE")
	@SequenceGenerator(name="ERRORLOG_SEQUENCE", sequenceName="SEQUENCE_ERRLOG", initialValue=1, allocationSize=10)
	@Column(name = "SR_NO")
	private int srNo;

	@Column(name = "EOD_DATE")
	private Date eodDate;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "ERROR")
	private String error;

	@Column(name = "ERR_DESC")
	private String errdesc;

	@Column(name = "RESOLUTION")
	private String resolution;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public Date getEodDate() {
		return eodDate;
	}

	public void setEodDate(Date eodDate) {
		this.eodDate = eodDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrdesc() {
		return errdesc;
	}

	public void setErrdesc(String errdesc) {
		this.errdesc = errdesc;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eodDate == null) ? 0 : eodDate.hashCode());
		result = prime * result + ((errdesc == null) ? 0 : errdesc.hashCode());
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resolution == null) ? 0 : resolution.hashCode());
		result = prime * result + srNo;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TblEodErrorTracker other = (TblEodErrorTracker) obj;
		if (eodDate == null) {
			if (other.eodDate != null)
				return false;
		} else if (!eodDate.equals(other.eodDate))
			return false;
		if (errdesc == null) {
			if (other.errdesc != null)
				return false;
		} else if (!errdesc.equals(other.errdesc))
			return false;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resolution == null) {
			if (other.resolution != null)
				return false;
		} else if (!resolution.equals(other.resolution))
			return false;
		if (srNo != other.srNo)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TblEodErrorTracker [id=" + id + ", srNo=" + srNo + ", eodDate=" + eodDate + ", userName=" + userName
				+ ", error=" + error + ", errdesc=" + errdesc + ", resolution=" + resolution + "]";
	}

}
