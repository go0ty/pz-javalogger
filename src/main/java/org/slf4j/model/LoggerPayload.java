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
package org.slf4j.model;

import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Defines logger payload for Syslog RFC 5424 protocol to be used in ELK-Stack
 * logging/audit/metrics.
 * 
 * @author Sonny.Saniev
 * 
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoggerPayload {

	public int facility = 1;
	public Severity severity;
	public int version = 1;
	public DateTime timestamp;
	public String hostName;
	public String ipAddress;
	public String application;
	public int process;
	public int messageId;
	private AuditElement auditData;
	private MetricElement metricData;
	public String message;

	public LoggerPayload() {

	}

	public int getFacility() {
		return facility;
	}

	public void setFacility(int facility) {
		this.facility = facility;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public AuditElement getAuditData() {
		return auditData;
	}

	public void setAuditData(AuditElement auditData) {
		this.auditData = auditData;
	}

	public MetricElement getMetricData() {
		return metricData;
	}

	public void setMetricData(MetricElement metricData) {
		this.metricData = metricData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder obj = new StringBuilder();
		String auditDataStr = getAuditData() != null ? getAuditData().toString() : "";
		String metricDataStr = getMetricData() != null ? getMetricData().toString() : "";
		obj.append("facility: " + getFacility() + "\n");
		obj.append("severity: " + getSeverity() + "\n");
		obj.append("version : " + getSeverity() + "\n");
		obj.append("timeStamp: " + getTimestamp() + "\n");
		obj.append("hostName : " + getHostName() + "\n");
		obj.append("ipAddress : " + getIpAddress() + "\n");
		obj.append("application : " + getApplication() + "\n");
		obj.append("process : " + getProcess() + "\n");
		obj.append("messageId : " + getMessageId() + "\n");
		obj.append("auditData : " + auditDataStr + "\n");
		obj.append("metricData : " + metricDataStr + "\n");
		obj.append("message : " + getMessage() + "\n");

		return obj.toString();
	}
}