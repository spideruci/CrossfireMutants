/*
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package org.apache.commons.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation") // tests some deprecated classes
public class ArgumentIsOptionTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    private Options options;
    private CommandLineParser parser;

    @BeforeEach
    public void setUp() {
        options = new Options().addOption("p", false, "Option p").addOption("attr", true, "Option accepts argument");
        parser = new PosixParser();
    }

    @Test
    public void testOption() throws Exception {
        final String[] args = {"-p"};

        final CommandLine cl = parser.parse(options, args);
        assertTrue("Confirm -p is set", cl.hasOption("p"));
        assertFalse("Confirm -attr is not set", cl.hasOption("attr"));
        assertEquals("Confirm all arguments recognized", 0, cl.getArgs().length);
    }

    @Test
    public void testOptionAndOptionWithArgument() throws Exception {
        final String[] args = {"-p", "-attr", "p"};

        final CommandLine cl = parser.parse(options, args);
        assertTrue("Confirm -p is set", cl.hasOption("p"));
        assertTrue("Confirm -attr is set", cl.hasOption("attr"));
        assertEquals("Confirm arg of -attr", "p", cl.getOptionValue("attr"));
        assertEquals("Confirm all arguments recognized", 0, cl.getArgs().length);
    }

    @Test
    public void testOptionWithArgument() throws Exception {
        final String[] args = {"-attr", "p"};

        final CommandLine cl = parser.parse(options, args);
        assertFalse("Confirm -p is set", cl.hasOption("p"));
        assertTrue("Confirm -attr is set", cl.hasOption("attr"));
        assertEquals("Confirm arg of -attr", "p", cl.getOptionValue("attr"));
        assertEquals("Confirm all arguments recognized", 0, cl.getArgs().length);
    }
}
