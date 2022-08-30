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
package oracle.fsgbu.eod.monitor.application.services.dao;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import oracle.fsgbu.eod.monitor.application.services.entities.TblApihealthcheckDetails;


@Repository
public interface ITblApiHealthcheckDetRepository extends JpaRepository<TblApihealthcheckDetails, String> {

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM TBL_APIHEALTHCHECK_DETAILS tb WHERE tb.ID = :id" , nativeQuery = true)
	public void deleteByCustomId(@Param("id") String id);
	
}
