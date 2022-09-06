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
import java.util.UUID;

/**
 * The persistent class for the ACCOUNTBALANCE database table.
 *
 */
@Entity
@Table(name = "TBL_EOD_CONFIG")
@NamedQueries({ @NamedQuery(name = "TblEodConfig.findAll", query = "SELECT c FROM TblEodConfig c ORDER BY c.id")})
public class TblEodConfig implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	// private String id = UUID.randomUUID().toString();
	private Integer id;

	@Column(name = "KEY")
	private String key;

	@Column(name = "VALUE")
	private String value;

	public TblEodConfig() {
	}

	public Integer getId() {
		return this.id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TblEodConfig [id=" + id + ", key=" + key + ", value=" + value + "]";
	}



}
