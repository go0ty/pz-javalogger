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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.ILoggerFactory;

/**
 * Logger Factory implementation. Based on the SLF4J examples.
 * 
 * @author Patrick.Doody
 *
 */
public class PiazzaLoggerFactory implements ILoggerFactory {

	ConcurrentMap<String, Logger> loggerMap;

	/**
	 * Default constructor. Initializes the map and Logger.
	 */
	public PiazzaLoggerFactory() {
		loggerMap = new ConcurrentHashMap<String, Logger>();
		PiazzaLogger.init();
	}

	/**
	 * Gets a PiazzaLogger instance.
	 */
	public Logger getLogger(String name) {
		Logger simpleLogger = loggerMap.get(name);
		if (simpleLogger != null) {
			return simpleLogger;
		} else {
			Logger newInstance = new PiazzaLogger(name);
			Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
			return oldInstance == null ? newInstance : oldInstance;
		}
	}
}
