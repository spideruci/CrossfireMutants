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

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class TypeHandlerTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    public static class Instantiable {
    }

    public static final class NotInstantiable {
        private NotInstantiable() {
        }
    }

    @Test
    public void testCreateValueClass() throws Exception {
        final Object clazz = TypeHandler.createValue(Instantiable.class.getName(), PatternOptionBuilder.CLASS_VALUE);
        assertEquals(Instantiable.class, clazz);
    }

    @Test
    public void testCreateValueClass_notFound() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue("what ever", PatternOptionBuilder.CLASS_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueDate() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> {
            TypeHandler.createValue("what ever", PatternOptionBuilder.DATE_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueExistingFile() throws Exception {
        try (FileInputStream result = TypeHandler.createValue("src/test/resources/org/apache/commons/cli/existing-readable.file",
            PatternOptionBuilder.EXISTING_FILE_VALUE)) {
            assertNotNull(result);
        }
    }

    @Test
    public void testCreateValueExistingFile_nonExistingFile() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue("non-existing.file", PatternOptionBuilder.EXISTING_FILE_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueFile() throws Exception {
        final File result = TypeHandler.createValue("some-file.txt", PatternOptionBuilder.FILE_VALUE);
        assertEquals("some-file.txt", result.getName());
    }

    @Test
    public void testCreateValueFiles() throws Exception {
        assertThrows(UnsupportedOperationException.class, () -> {
            TypeHandler.createValue("some.files", PatternOptionBuilder.FILES_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueInteger_failure() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue("just-a-string", Integer.class);
        }) ;

    }

    @Test
    public void testCreateValueNumber_Double() throws Exception {
        assertEquals(1.5d, TypeHandler.createValue("1.5", PatternOptionBuilder.NUMBER_VALUE));
    }

    @Test
    public void testCreateValueNumber_Long() throws Exception {
        assertEquals(Long.valueOf(15), TypeHandler.createValue("15", PatternOptionBuilder.NUMBER_VALUE));
    }

    @Test
    public void testCreateValueNumber_noNumber() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue("not a number", PatternOptionBuilder.NUMBER_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueObject_InstantiableClass() throws Exception {
        final Object result = TypeHandler.createValue(Instantiable.class.getName(), PatternOptionBuilder.OBJECT_VALUE);
        assertTrue(result instanceof Instantiable);
    }

    @Test
    public void testCreateValueObject_notInstantiableClass() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue(NotInstantiable.class.getName(), PatternOptionBuilder.OBJECT_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueObject_unknownClass() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue("unknown", PatternOptionBuilder.OBJECT_VALUE);
        }) ;

    }

    @Test
    public void testCreateValueString() throws Exception {
        assertEquals("String", TypeHandler.createValue("String", PatternOptionBuilder.STRING_VALUE));
    }

    @Test
    public void testCreateValueURL() throws Exception {
        final String urlString = "https://commons.apache.org";
        final URL result = TypeHandler.createValue(urlString, PatternOptionBuilder.URL_VALUE);
        assertEquals(urlString, result.toString());
    }

    @Test
    public void testCreateValueURL_malformed() throws Exception {
        assertThrows(ParseException.class, () -> {
            TypeHandler.createValue("malformed-url", PatternOptionBuilder.URL_VALUE);
        }) ;
    }
}
