package com.wraith.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * User: rowan.massey Date: 24/02/13 Time: 20:07
 */
@Configuration
public class ApplicationRestConfig extends RepositoryRestMvcConfiguration {

	@Override
	protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		//The following code displays all dates in ISO8601 format in the JSON payload.
		objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(new ISO8601DateFormat());
	}
}
