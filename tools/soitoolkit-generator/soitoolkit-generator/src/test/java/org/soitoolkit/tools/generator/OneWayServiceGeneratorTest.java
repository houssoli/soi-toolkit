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
package org.soitoolkit.tools.generator;

import static org.junit.Assert.assertEquals;
import static org.soitoolkit.tools.generator.model.enums.DeploymentModelEnum.STANDALONE_DEPLOY;
import static org.soitoolkit.tools.generator.model.enums.DeploymentModelEnum.WAR_DEPLOY;
import static org.soitoolkit.tools.generator.model.enums.MuleVersionEnum.MULE_3_1_1;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.FILE;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.FTP;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.HTTP;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.IMAP;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.JDBC;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.JMS;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.POP3;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.SERVLET;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.SFTP;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.SMTP;
import static org.soitoolkit.tools.generator.model.enums.TransportEnum.VM;
import static org.soitoolkit.tools.generator.model.impl.ModelUtil.capitalize;
import static org.soitoolkit.tools.generator.util.SystemUtil.BUILD_COMMAND;
import static org.soitoolkit.tools.generator.util.SystemUtil.CLEAN_COMMAND;
import static org.soitoolkit.tools.generator.util.SystemUtil.ECLIPSE_AND_TEST_REPORT_COMMAND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.soitoolkit.tools.generator.model.IModel;
import org.soitoolkit.tools.generator.model.ModelFactory;
import org.soitoolkit.tools.generator.model.enums.DeploymentModelEnum;
import org.soitoolkit.tools.generator.model.enums.MuleVersionEnum;
import org.soitoolkit.tools.generator.model.enums.TransformerEnum;
import org.soitoolkit.tools.generator.model.enums.TransportEnum;
import org.soitoolkit.tools.generator.util.SystemUtil;
import org.soitoolkit.tools.generator.util.PreferencesUtil;

public class OneWayServiceGeneratorTest {

	private static final List<TransportEnum> TRANSPORTS = new ArrayList<TransportEnum>();
	private static final String TEST_OUT_FOLDER = PreferencesUtil.getDefaultRootFolder() + "/jUnitTests";
	private static final String VERSION = "1.0-SNAPSHOT";
	private static final String MAVEN_HOME = PreferencesUtil.getMavenHome();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOneWayServices310() throws IOException {
		doTestOneWayServices("org.soitoolkit.tool.generator",       "onewaySA310",         MULE_3_1_1, STANDALONE_DEPLOY);
		doTestOneWayServices("org.soitoolkit.tool.generator-tests", "Oneway-Tests-SA-310", MULE_3_1_1, STANDALONE_DEPLOY);

		doTestOneWayServices("org.soitoolkit.tool.generator",       "onewayWD310",         MULE_3_1_1, WAR_DEPLOY);
		doTestOneWayServices("org.soitoolkit.tool.generator-tests", "Oneway-Tests-WD-310", MULE_3_1_1, WAR_DEPLOY);
	}

	private void doTestOneWayServices(String groupId, String artifactId, MuleVersionEnum muleVersion, DeploymentModelEnum deploymentModel) throws IOException {
		TransportEnum[] inboundTransports  = {VM, JMS, JDBC, FILE, FTP, HTTP}; // Waiting for embedded SFTP: SFTP, //, IMAP}; // POP3
		TransportEnum[] outboundTransports = {VM, JMS, JDBC, FILE, FTP}; // Waiting for embedded SFTP: SFTP, //, SMTP};  

		// FIXME, ADD SERVLET INSTEAD OF REDEFINE THE WHOLE ARRAY!!!
		if (deploymentModel == WAR_DEPLOY) {
			inboundTransports  = new TransportEnum[] {VM, JMS, JDBC, FILE, FTP, HTTP, SERVLET}; // Waiting for embedded SFTP: SFTP, //, IMAP}; // POP3
		}
		
		createEmptyIntegrationComponent(groupId, artifactId, muleVersion, deploymentModel);	

		for (TransportEnum inboundTransport : inboundTransports) {
			for (TransportEnum outboundTransport : outboundTransports) {
				if (inboundTransport == JMS  && outboundTransport == JDBC) continue;
				if (inboundTransport == JDBC && outboundTransport == JMS)  continue;
				createOneWayService(groupId, artifactId, inboundTransport, outboundTransport, TransformerEnum.JAVA);
			}
		}

		performMavenBuild(groupId, artifactId);
	}

	private void createEmptyIntegrationComponent(String groupId, String artifactId, MuleVersionEnum muleVersion, DeploymentModelEnum deploymentModel) throws IOException {
		
		int noOfExpectedFiles = (deploymentModel == STANDALONE_DEPLOY) ? 54 : 66;
		
		String projectFolder = TEST_OUT_FOLDER + "/" + artifactId;

		TRANSPORTS.add(VM);
		TRANSPORTS.add(JMS);
		TRANSPORTS.add(JDBC);
		TRANSPORTS.add(FILE);
		TRANSPORTS.add(FTP);
		TRANSPORTS.add(SFTP);
		if (deploymentModel == WAR_DEPLOY) {
			TRANSPORTS.add(SERVLET);
		}
		TRANSPORTS.add(POP3);
		TRANSPORTS.add(IMAP);
		TRANSPORTS.add(SMTP);

		SystemUtil.delDirs(projectFolder);
		assertEquals(0, SystemUtil.countFiles(projectFolder));
		new IntegrationComponentGenerator(System.out, groupId, artifactId, VERSION, muleVersion, deploymentModel, TRANSPORTS, TEST_OUT_FOLDER).startGenerator();
		assertEquals("Missmatch in expected number of created files and folders", noOfExpectedFiles, SystemUtil.countFiles(projectFolder));
	}

	private void createOneWayService(String groupId, String artifactId, TransportEnum inboundTransport, TransportEnum outboundTransport, TransformerEnum transformerType) throws IOException {
		String projectFolder = TEST_OUT_FOLDER + "/" + artifactId;

		String service = inboundTransport.name().toLowerCase() + "To" + capitalize(outboundTransport.name().toLowerCase());

		int noOfFilesBefore = SystemUtil.countFiles(projectFolder);

		IModel model = ModelFactory.newModel(groupId, artifactId, VERSION, service, null, null, null);
		new OnewayServiceGenerator(System.out, groupId, artifactId, service, inboundTransport, outboundTransport, transformerType, projectFolder + "/trunk/" + model.getServiceProjectFilepath()).startGenerator();
		
		int expectedNoOfFiles = 10;
		if (inboundTransport == HTTP || inboundTransport == SERVLET) {
			expectedNoOfFiles++;
		}
		if (inboundTransport == JDBC) {
			expectedNoOfFiles++; // from-db-transformer
		}
		if (outboundTransport == JDBC) {
			expectedNoOfFiles++; // to-db-transformer
		}
//		TODO: Wait with attachments... 	    
//		if (inboundTransport == POP3 || inboundTransport == IMAP) {
//			expectedNoOfFiles += 2; // png + pdf attachment
//		}
		assertEquals("Missmatch in expected number of created files and folders", expectedNoOfFiles, SystemUtil.countFiles(projectFolder) - noOfFilesBefore);
	}

	private void performMavenBuild(String groupId, String artifactId) throws IOException {
		String PROJECT_FOLDER = TEST_OUT_FOLDER + "/" + artifactId;

		boolean testOk = false;
		
		try {
			SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + BUILD_COMMAND, PROJECT_FOLDER + "/trunk");
			testOk = true;
		} finally {
			// Always try to create eclipsefiles and test reports 
			SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + ECLIPSE_AND_TEST_REPORT_COMMAND, PROJECT_FOLDER + "/trunk");
		}
		
		// If the build runs fine then also perform a clean-command to save GB's of diskspace...
		if (testOk) SystemUtil.executeCommand(MAVEN_HOME + "/bin/" + CLEAN_COMMAND, PROJECT_FOLDER + "/trunk");
	}

}