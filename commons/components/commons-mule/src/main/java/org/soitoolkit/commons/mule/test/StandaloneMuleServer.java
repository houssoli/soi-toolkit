/* 
 * Licensed to the soi-toolkit project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The soi-toolkit project licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soitoolkit.commons.mule.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Minimal configuration of mule used for manual testing against a stand alone Mule server suitable to be started from within the development environment, e.g. Eclipse.
 * 
 * NOTE: The natural name for this class shoud be MuleServer but since that name already is taken by org.mule.MuleServer we use another name to minimize risk for confusing naming conflicts...
 *
 * @author Magnus Larsson
 *
 */
public class StandaloneMuleServer {

	private static Logger log = LoggerFactory.getLogger(StandaloneMuleServer.class);
	
	// Configuration parameters set by the constructor
	protected String muleServerId = null;
	protected String muleConfig = null;
    
    // The underlying mule server and servlet container
    org.mule.MuleServer muleServer = null;

	/**
	 * Constructor that takes configuration parameters
	 * 
	 * @param muleServerId
	 * @param muleConfig
	 */
    public StandaloneMuleServer(String muleServerId, String muleConfig) {
    	this.muleServerId = muleServerId;
    	this.muleConfig = muleConfig;
	}

    /**
     * Convenience method that both starts and stops mule 
     * 
     * @throws Exception
     */
	public void run() throws Exception {

		// Start me up...
        log.info("Startup...");
		start();

        // Run until the return key is hit...
        log.info("Hit the RETURN - key to shutdown");
        System.in.read();

        // Bye, bye...
        log.info("Shutdown...");
        shutdown();
        log.info("Shutdown complete");
	}

	/**
	 * Start up mule...
	 * 
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void start() throws InterruptedException, Exception {
		
        log.info("Startup Mule...");

        // Before launching Mule ESB set its server id
		System.setProperty("mule.serverId", muleServerId);

		// Before launching Mule ESB alse ensure that CXF use LOG4J for logging
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");

		// Startup Mule ESB in the background
		muleServer = new org.mule.MuleServer(muleConfig);
        muleServer.start(true, true);
	}

	/**
	 * Shutdown mule
	 * 
	 * @throws Exception
	 */
	public void shutdown() throws Exception {
		
        log.info("Shutdown Mule...");

        // Shutdown mule server
        muleServer.shutdown();
	}
}