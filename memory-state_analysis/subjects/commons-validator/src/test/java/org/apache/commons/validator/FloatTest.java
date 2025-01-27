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

/**
 * Performs Validation Test for <code>float</code> validations.
 *
 * @version $Revision$
 */
public class FloatTest extends AbstractNumberTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }


    public FloatTest() {
        ACTION = "float";
        FORM_KEY = "floatForm";
    }

    /**
     * Tests the float validation.
     */
    @Test
    public void testFloat() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue("0");

        valueTest(info, true);
    }

    /**
     * Tests the float validation.
     */
    @Test
    public void testFloatMin() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue(Float.valueOf(Float.MIN_VALUE).toString());

        valueTest(info, true);
    }
    @Test
    /**
     * Tests the float validation.
     */
    public void testFloatMax() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();
        info.setValue(Float.valueOf(Float.MAX_VALUE).toString());

        valueTest(info, true);
    }
    @Test
    /**
     * Tests the float validation failure.
     */
    public void testFloatFailure() throws ValidatorException {
        // Create bean to run test on.
        ValueBean info = new ValueBean();

        valueTest(info, false);
    }

}                                                         