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
package org.soitoolkit.tools.generator.model.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.soitoolkit.tools.generator.model.enums.DeploymentModelEnum;
import org.soitoolkit.tools.generator.model.enums.MuleVersionEnum;
import org.soitoolkit.tools.generator.model.enums.TransformerEnum;
import org.soitoolkit.tools.generator.model.enums.TransportEnum;

public class DefaultModelImplTest {

	@Test
	public void testIsGroupIdSuffixedWithArtifactId() {
		DefaultModelImpl dm = new DefaultModelImpl();
		
		String groupId = "se.callista.soitoolkit.test";
		String artifactId = "test";
		String version = null;
		String service = null;
		MuleVersionEnum muleVersion = null;
		DeploymentModelEnum dependencyModel = null;
		List<TransportEnum> transports = null;
		TransportEnum inboundTransport = null;
		TransportEnum outboundTransport = null;
		TransformerEnum transformerType = null;
		String serviceDescriptor = null;
		List<String> operations = null;
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertTrue(dm.isGroupIdSuffixedWithArtifactId());

		groupId = "test";
		artifactId = "test";
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertTrue(dm.isGroupIdSuffixedWithArtifactId());

		groupId = "se.callista.soitoolkit.test";
		artifactId = null;
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertFalse(dm.isGroupIdSuffixedWithArtifactId());

		groupId = null;
		artifactId = "test";
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertFalse(dm.isGroupIdSuffixedWithArtifactId());

		groupId = "se.callista.soitoolkit.test";
		artifactId = "notFound";
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertFalse(dm.isGroupIdSuffixedWithArtifactId());

		groupId = "se.callista.soitoolkit.test";
		artifactId = "te";
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertFalse(dm.isGroupIdSuffixedWithArtifactId());

		groupId = "short";
		artifactId = "loooooong";
		dm.initModel(groupId, artifactId, version, service, muleVersion, dependencyModel, transports, inboundTransport, outboundTransport, transformerType, serviceDescriptor, operations);
		assertFalse(dm.isGroupIdSuffixedWithArtifactId());
	}

}
