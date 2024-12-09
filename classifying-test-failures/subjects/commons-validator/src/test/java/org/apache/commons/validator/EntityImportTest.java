/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Locale;

import static junit.framework.TestCase.assertNotNull;


/**                                                       
 * Tests entity imports.
 *
 * @version $Revision$
 */
public class EntityImportTest extends AbstractCommonTest {

    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

//    public EntityImportTest(String name) {
//        super(name);
//    }

    /**
     * Tests the entity import loading the <code>byteForm</code> form.
     */
    @Test
    public void testEntityImport() throws Exception {
        URL url = getClass().getResource("EntityImportTest-config.xml");
        ValidatorResources resources = new ValidatorResources(url.toExternalForm());
        assertNotNull("Form should be found", resources.getForm(Locale.getDefault(), "byteForm"));
    }  

    /**
     * Tests loading ValidatorResources from a URL
     */
    @Test
    public void testParseURL() throws Exception {
        URL url = getClass().getResource("EntityImportTest-config.xml");
        ValidatorResources resources = new ValidatorResources(url);
        assertNotNull("Form should be found", resources.getForm(Locale.getDefault(), "byteForm"));
    }
}
