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

import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.model.AuditElement;
import org.slf4j.model.MetricElement;

/**
 * Logging class to write log statements to the Pz-Logger REST endpoint.
 * 
 * @author Patrick.Doody
 *
 */
public class PiazzaLogger extends MarkerIgnoringBase {
	private static final long serialVersionUID = -8501125831479851574L;
	/**
	 * The URL to Piazza Logger, where all messages will be routed.
	 */
	private static String PZ_LOGGER_URL = null;
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
	}

	public boolean isTraceEnabled() {
		return true;
	}

	public void trace(String msg) {
		// TODO Auto-generated method stub

	}

	public void trace(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	public void trace(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	public void trace(String format, Object... arguments) {

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