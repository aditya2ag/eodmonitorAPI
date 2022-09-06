package oracle.fsgbu.eod.monitor.application.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerCustomConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(get_apiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo get_apiInfo() {
		return new ApiInfo("Oracle EOD monitor application APIs", "This application API is developed by Oracle", "1.0",
				"https://www.oracle.com/legal/copyright.html",
				new Contact("Oracle", "https://www.oracle.com/corporate/contact/", null), "License of APIs",
				"https://www.oracle.com/legal/terms.html", new ArrayList<>());
	}
	// Copyright © 2022 ,Oracle and/or its affiliates. All rights reserved.
}
