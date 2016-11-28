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

import java.util.HashSet;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTime;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import model.logger.AuditElement;
import model.logger.LoggerPayload;
import model.logger.MetricElement;
import model.logger.Severity;


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

	public void trace(String format) {
		processLogs(Severity.DEBUG, format, new Object());
	}

	public void trace(String format, Object arg) {
		processLogs(Severity.DEBUG, format, arg);
	}

	public void trace(String format, Object arg1, Object arg2) {
		processLogs(Severity.DEBUG, format, arg1, arg2);

	}

	public void trace(String format, Object... arguments) {
		processLogs(Severity.DEBUG, format, arguments);
	}

	public void trace(String format, Throwable t) {
		processLogs(Severity.DEBUG, format, t);
	}

	public boolean isDebugEnabled() {
		return true;
	}

	public void debug(String format) {
		processLogs(Severity.DEBUG, format, new Object());
	}

	public void debug(String format, Object arg) {
		processLogs(Severity.DEBUG, format, new Object());
	}

	public void debug(String format, Object arg1, Object arg2) {
		processLogs(Severity.DEBUG, format, arg1, arg2);
	}

	public void debug(String format, Object... arguments) {
		processLogs(Severity.DEBUG, format, arguments);
	}

	public void debug(String format, Throwable t) {
		processLogs(Severity.DEBUG, format, t);
	}

	public boolean isInfoEnabled() {
		return true;
	}

	public void info(String format) {
		processLogs(Severity.INFORMATIONAL, format, new Object());
	}

	public void info(String format, Object arg) {
		processLogs(Severity.INFORMATIONAL, format, arg);
	}

	public void info(String format, Object arg1, Object arg2) {
		processLogs(Severity.INFORMATIONAL, format, arg1, arg2);
	}

	public void info(String format, Object... arguments) {
		processLogs(Severity.INFORMATIONAL, format, arguments);
	}

	public void info(String format, Throwable t) {
		processLogs(Severity.INFORMATIONAL, format, t);
	}

	public boolean isWarnEnabled() {
		return true;
	}

	public void warn(String format) {
		processLogs(Severity.WARNING, format, new Object());
	}

	public void warn(String format, Object arg) {
		processLogs(Severity.WARNING, format, arg);
	}

	public void warn(String format, Object... arguments) {
		processLogs(Severity.WARNING, format, arguments);
	}

	public void warn(String format, Object arg1, Object arg2) {
		processLogs(Severity.WARNING, format, arg1, arg2);
	}

	public void warn(String format, Throwable t) {
		processLogs(Severity.WARNING, format, t);
	}

	public boolean isErrorEnabled() {
		return true;
	}

	public void error(String format) {
		processLogs(Severity.ERROR, format, new Object());
	}

	public void error(String format, Object arg) {
		processLogs(Severity.ERROR, format, arg);
	}

	public void error(String format, Object arg1, Object arg2) {
		processLogs(Severity.ERROR, format, arg1, arg2);
	}

	public void error(String format, Object... arguments) {
		processLogs(Severity.ERROR, format, arguments);
	}

	public void error(String format, Throwable t) {
		processLogs(Severity.ERROR, format, t);
	}
	
	/**
	 * 
	 * @param severity
	 *            Severity codes per syslog rfc-5424 standard
	 * @param message
	 *            String message of the log
	 * @param arguments
	 *            Optional log arguments, audit / metric / exception types
	 */
	public void processLogs(Severity severity, String format, Object... arguments) {
		String message = format;
		LoggerPayload payload = new LoggerPayload();
		HashSet<String> argumentSet = new HashSet<String>();

		for (int i = 0; i < arguments.length; i++) {
			Object obj = arguments[i];

			if (obj instanceof AuditElement && !argumentSet.contains("auditElement")) {
				argumentSet.add("AuditElement");
				payload.setAuditData((AuditElement) obj);
			} else if (obj instanceof MetricElement && !argumentSet.contains("metricElement")) {
				argumentSet.add("MetricElement");
				payload.setMetricData((MetricElement) obj);
			} else if (obj instanceof Throwable && !argumentSet.contains("exception")) {
				argumentSet.add("exception");
				Throwable exception = (Throwable) obj;
				String exceptionStackTrace = ExceptionUtils.getStackTrace(exception);
				
				System.out.println(exceptionStackTrace);
				message = String.format("%s - %s", message, exceptionStackTrace);
			}
		}

		sendLogs(payload, message, severity);
	}
	
	/**
	 * Sends the logger payload to pz-logger
	 * 
	 * @param loggerPayload payload
	 * 
	 */
	private void sendLogs(LoggerPayload payload, String logMessage, Severity severity) {
		
		// Setting generic fields on logger payload
		payload.setSeverity(severity);
		payload.setMessage(logMessage);
		payload.setMessageId(logMessage.hashCode());
		payload.setTimestamp(new DateTime());

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Log to console if requested
			try {
				if (logToConsole.booleanValue()) {
					System.out.println(payload.toString());
				}
			} catch (Exception exception) { /* Do nothing. */
				System.out.println(String.format("%s: %s", "Application property is not set", exception));
			}

			// post to pz-logger
			String url = String.format("%s/%s", PZ_LOGGER_URL, PZ_LOGGER_ENDPOINT);
			restTemplate.postForEntity(url, new HttpEntity<LoggerPayload>(payload, headers), String.class);
		} catch (Exception exception) {
			System.out.println(String.format("%s: %s", "PiazzaLogger could not log", exception));
		}
	}
}