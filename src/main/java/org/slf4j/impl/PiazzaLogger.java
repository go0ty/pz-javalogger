/**
 * Copyright 2016, RadiantBlue Technologies, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package org.slf4j.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.model.AuditElement;
import org.slf4j.model.LoggerPayload;
import org.slf4j.model.MetricElement;
import org.slf4j.model.Severity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * Logging class to write log statements to the Pz-Logger REST endpoint.
 * 
 * @author Patrick.Doody
 *
 */
public class PiazzaLogger extends MarkerIgnoringBase {
	private static final long serialVersionUID = -8501125831479851574L;
	
	private Boolean logToConsole = false;
	
	private static RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * The URL to Piazza Logger, where all messages will be routed.
	 */
	private static String PZ_LOGGER_URL = null;
	private static String PZ_LOGGER_ENDPOINT = "syslog";
	private static boolean INITIALIZED = false;
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Logger name
	 */
	public PiazzaLogger(String name) {
		this.name = name;
	}

	/**
	 * Initializes the logger component. This will scan the environment for the
	 * URL to Piazza Logger REST endpoint.
	 */
	static void init() {
		if (INITIALIZED) {
			return;
		}
		INITIALIZED = true;
		// Scan the environment for the URL to Pz-Logger
		PZ_LOGGER_URL = System.getenv("logger.url");
		
		System.out.println(String.format("PiazzaLogger initialized for service %s, url: %s", "serviceName", PZ_LOGGER_URL));
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(7500).setMaxConnPerRoute(4000).build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	}

	public boolean isTraceEnabled() {
		return true;
	}

	public void trace(String msg) {
		// TODO Auto-generated method stub
		processLogs(Severity.DEBUG, msg, null);
	}

	public void trace(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	public void trace(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void trace(String format, Object... arguments) {
		processLogs(Severity.DEBUG, format, arguments);
	}

	public void processLogs(Severity severity, String format, Object... arguments) {

		Map<String, Object> elementMap = new HashMap<String, Object>();
		if (arguments != null) {
			for (int i = 0; i < arguments.length; i++) {
				Object obj = arguments[i];
				
				if( obj instanceof AuditElement && !elementMap.containsKey("AuditElement"))
				{
					elementMap.put("AuditElement", obj);
				}
				else if( obj instanceof MetricElement  && !elementMap.containsKey("MetricElement"))
				{
					elementMap.put("MetricElement", obj);
				}
			}
		}
		
	}
	/**
	 * Sends the logger payload to pz-logger
	 * 
	 * @param loggerPayload payload
	 * 
	 */
	private void sendLogs(LoggerPayload loggerPayload, String logMessage, Severity severity) {
		
		// Setting generic fields on logger payload
		loggerPayload.setSeverity(severity);
		loggerPayload.setMessage(logMessage);
		loggerPayload.setMessageId(logMessage.hashCode());
		loggerPayload.setTimestamp(new DateTime());

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Log to console if requested
			try {
				if (logToConsole.booleanValue()) {
					System.out.println(loggerPayload.toString());
				}
			} catch (Exception exception) { /* Do nothing. */
				System.out.println(String.format("%s: %s", "Application property is not set", exception));
			}

			// post to pz-logger
			String url = String.format("%s/%s", PZ_LOGGER_URL, PZ_LOGGER_ENDPOINT);
			restTemplate.postForEntity(url, new HttpEntity<LoggerPayload>(loggerPayload, headers), String.class);
		} catch (Exception exception) {
			System.out.println(String.format("%s: %s", "PiazzaLogger could not log", exception));
		}
	}

	public void trace(String msg, Throwable t) {
		// TODO Auto-generated method stub
	}

	public boolean isDebugEnabled() {
		return true;
	}

	public void debug(String msg) {
		// TODO Auto-generated method stub

	}

	public void debug(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	public void debug(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void debug(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	public void debug(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	public boolean isInfoEnabled() {
		return true;
	}

	public void info(String msg) {

	}

	public void info(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	public void info(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void info(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	public void info(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	public boolean isWarnEnabled() {
		return true;
	}

	public void warn(String msg) {
		// TODO Auto-generated method stub

	}

	public void warn(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	public void warn(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	public void warn(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void warn(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	public boolean isErrorEnabled() {
		return true;
	}

	public void error(String msg) {
		// TODO Auto-generated method stub

	}

	public void error(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	public void error(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void error(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	public void error(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}
}