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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oracle.fsgbu.eod.monitor.application.services.entities.TblEodErrorTracker;
import oracle.fsgbu.eod.monitor.application.services.entities.TblEodUserDetails;

public interface TblEodUserDetailsRepository extends JpaRepository<TblEodUserDetails, String> {

	@Query("SELECT u FROM TblEodUserDetails u WHERE u.email_id = :emailId AND record_stat = 'O'")
	TblEodUserDetails findUserByEmail(@Param("emailId") String emailId);
}
