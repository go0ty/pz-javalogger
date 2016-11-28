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
package impl.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.impl.PiazzaLoggerFactory;

import model.logger.AuditElement;
import model.logger.MetricElement;

/**
 * Tests the Piazza Logger implementation.
 * 
 * @author Patrick.Doody
 *
 */
public class PiazzaLoggerTest {
	private Logger logger;
	private AuditElement auditElement;
	private MetricElement metricElement;
	private RuntimeException exception;

	/**
	 * Setting up test variables.
	 */
	@Before
	public void setup() {
		PiazzaLoggerFactory loggerFactory = new PiazzaLoggerFactory();
		logger = loggerFactory.getLogger("Test");
		auditElement = new AuditElement("actorA", "tests", "acteeB");
		metricElement = new MetricElement("testing", "50", "Test");
		exception = new RuntimeException("Error during runtime");
	}

	/**
	 * Tests the logger statements in the logger, with various severities.
	 */
	@Ignore
	@Test
	public void testLoggerStatements() {
		// Test various log statements and severities
		String logMessage = "This is a test log message.";

		// Trace
		logger.trace(logMessage);
		logger.trace(logMessage, exception);
		logger.trace(logMessage, auditElement);
		logger.trace(logMessage, auditElement, metricElement, exception);
		logger.trace(logMessage, auditElement, metricElement);

		// Debug
		logger.debug(logMessage);
		logger.debug(logMessage, exception);
		logger.debug(logMessage, auditElement);
		logger.debug(logMessage, auditElement, metricElement, exception);
		logger.debug(logMessage, auditElement, metricElement);

		// Info
		logger.info(logMessage);
		logger.info(logMessage, exception);
		logger.info(logMessage, auditElement);
		logger.info(logMessage, auditElement, metricElement, exception);
		logger.info(logMessage, auditElement, metricElement);

		// Warning
		logger.warn(logMessage);
		logger.warn(logMessage, exception);
		logger.warn(logMessage, auditElement);
		logger.warn(logMessage, auditElement, metricElement, exception);
		logger.warn(logMessage, auditElement, metricElement);

		// Error
		logger.error(logMessage);
		logger.error(logMessage, exception);
		logger.error(logMessage, auditElement);
		logger.error(logMessage, auditElement, metricElement, exception);
		logger.error(logMessage, auditElement, metricElement);
	}
}
