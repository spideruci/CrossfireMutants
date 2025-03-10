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
package org.apache.commons.text.translate;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CodePointTranslatorTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    @Test
    public void testAboveReturningNonNull() throws IOException {
        final NumericEntityEscaper numericEntityEscaper = NumericEntityEscaper.above(0);
        final UnicodeEscaper unicodeEscaper = new UnicodeEscaper();
        final String string = unicodeEscaper.toUtf16Escape(0);
        try (PipedReader pipedReader = new PipedReader(); PipedWriter pipedWriter = new PipedWriter(pipedReader)) {
            assertThat(numericEntityEscaper.translate(string, 0, pipedWriter)).isEqualTo(1);
        }
    }

}
