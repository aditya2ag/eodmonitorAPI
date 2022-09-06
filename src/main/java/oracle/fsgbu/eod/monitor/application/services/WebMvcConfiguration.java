package oracle.fsgbu.eod.monitor.application.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		System.out.println("Inside WebMvcConfiguration");
		registry.addMapping("/*").allowedOrigins("*").allowCredentials(false).allowedHeaders("*").maxAge(3600)
				.allowedMethods("*");
	}

}
