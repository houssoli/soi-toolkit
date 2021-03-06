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
package org.soitoolkit.tools.generator.model;

import java.util.Map;

public interface IModel {

	public Object resolveParameter(String parameterName);

	public Object resolveParameter(String parameterName, Object defaultValue);

	public XmlNamespaceModel getXmlNamespace();

	public ServiceDescriptorModel getSd();

	public Map<String, Object> getExt();

	public String getDollarSymbol();
	
	public String getXmlTimestamp();

	public String getGroupId();

	public String getArtifactId();

	public String getCapitalizedArtifactId();

	public String getLowercaseArtifactId();

	public String getCapitalizedJavaArtifactId();
	
	public String getVersion();

	public String getService();

	public String getCapitalizedService();

	public String getLowercaseService();

	public String getInitialLowercaseService();

	public String getUppercaseService();

	public String getCapitalizedJavaService();

	public String getLowercaseJavaService();

	public String getInitialLowercaseJavaService();

	public String getSoitoolkitVersion();

	public String getSuperpomGroupId();

	public String getSuperpomArtifactId();

	public String getSuperpomVersion();

	public String getParentPom();

	public String getIntegrationComponentProject();

	public String getServiceProject();
	public String getServiceProjectFilepath();

	public String getSchemaProject();
	public String getSchemaProjectFilepath();

	public String getStandaloneProject();
	public String getStandaloneProjectFilepath();

	public String getTeststubStandaloneProject();
	public String getTeststubStandaloneProjectFilepath();
	
	public String getWebProject();
	public String getWebProjectFilepath();

	public String getTeststubWebProject();
	public String getTeststubWebProjectFilepath();

	public String getJavaPackage();
	public String getJavaPackageFilepath();
	
	public String getDefaultFtpUsername();
	public String getDefaultFtpPassword();

	public String getDefaultSftpUsername();
	public String getDefaultSftpIdentityFile();
    public String getDefaultSftpIdentityPassphrase();

    // JMS Naming...
    public String getJmsInQueue();
    public String getJmsOutQueue();
    public String getJmsDLQueue();
    public String getJmsRequestQueue();
    public String getJmsResponseQueue();
    public String getJmsLogInfoQueue();
    public String getJmsLogErrorQueue();

    // HTTP ports...
    public String getServletPort();
    public String getHttpPort();
    public String getHttpTeststubPort();
    
    // Mule version
    public String getMuleVersion();
    
    // Deploy Model
    public boolean isStandaloneDeployModel();
    public boolean isWarDeployModel();

    // Transports
    public boolean isJms();
    public boolean isJdbc();
	public boolean isFtp();
	public boolean isSftp();
	public boolean isServlet();
    public boolean isPop3();
    public boolean isImap();
    public boolean isSmtp();
    public String getInboundTransport();
    public String getOutboundTransport();
    
    public boolean isServiceTransactional();
    public boolean isServiceXaTransactional();
    public boolean isInboundEndpointFilebased();
    public boolean isOutboundEndpointFilebased();
    
    // Selected type of transformer
    public String getTransformerType();

    // Property files
    public String getConfigPropertyFile();
	public String getSecurityPropertyFile();

}