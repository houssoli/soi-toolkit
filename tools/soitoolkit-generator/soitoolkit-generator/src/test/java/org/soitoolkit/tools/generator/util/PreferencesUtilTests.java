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
package org.soitoolkit.tools.generator.util;

import static org.junit.Assert.assertTrue;
import static org.soitoolkit.tools.generator.util.MiscUtil.convertStreamToString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PreferencesUtilTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDefaultSftpIdentityFile() throws FileNotFoundException {
    	File f = new File(PreferencesUtil.getDefaultSftpIdentityFile());
    	assertTrue(f.exists());
    	assertTrue(f.canRead());
    	assertTrue(convertStreamToString(new FileInputStream(f)).length() > 0);
	}
}
