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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TBL_APIHEALTHCHECK_DETAILS")
@NamedQueries({ @NamedQuery(name = "TblApihealthcheckDetails.findAll", query = "SELECT c FROM TblApihealthcheckDetails c ORDER BY c.apiName")})
public class TblApihealthcheckDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ID")
	private String id = UUID.randomUUID().toString();
	
	@Column(name = "API_NAME")
	private String apiName;
	
	@Id
	@Column(name = "URL")
	private String url;

	@Column(name = "REQ_METHOD")
	private String reqMethod;

	@Column(name = "REQUEST_HEADER")
	private String requestHeader;

	@Column(name = "REQUEST_PARAM")
	private String requestParam;

	@Column(name = "REQUEST_BODY")
	@Lob
	private String requestBody;


}
