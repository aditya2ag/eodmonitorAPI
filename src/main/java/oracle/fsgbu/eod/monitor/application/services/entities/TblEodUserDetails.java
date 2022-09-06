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
@Table(name = "TBL_EOD_USER_DETAILS")
@NamedQueries({ @NamedQuery(name = "TblEodUserDetails.findAll", query = "SELECT c FROM TblEodUserDetails c") })
public class TblEodUserDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EMAIL_ID")
	private String email_id;

	@Column(name = "NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "USER_ROLE")
	private String userRole;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "RECORD_STAT")
	private String recordStat;

	public String getRecordStat() {
		return recordStat;
	}

	public void setRecordStat(String recordStat) {
		this.recordStat = recordStat;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "TblEodUserDetails [email_id=" + email_id + ", userName=" + userName + ", password=" + password
				+ ", role=" + role + ", location=" + location + ", recordStat=" + recordStat + "]";
	}

}
