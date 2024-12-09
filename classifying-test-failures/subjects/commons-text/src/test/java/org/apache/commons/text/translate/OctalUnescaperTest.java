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

import org.junit.jupiter.api.Test;

/**
 * Tests {@link OctalUnescaper}.
 */
public class OctalUnescaperTest {

    @Test
    public void testBetween() {
        final OctalUnescaper oue = new OctalUnescaper();   //.between("1", "377");

        String input = "\\45";
        String result = oue.translate(input);
        String finalResult = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult).as("Failed to unescape octal characters via the between method").isEqualTo("\45");});

        input = "\\377";
        result = oue.translate(input);
        String finalResult1 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult1).as("Failed to unescape octal characters via the between method").isEqualTo("\377");});

        input = "\\377 and";
        result = oue.translate(input);
        String finalResult2 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult2).as("Failed to unescape octal characters via the between method").isEqualTo("\377 and");});

        input = "\\378 and";
        result = oue.translate(input);
        String finalResult3 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult3).as("Failed to unescape octal characters via the between method").isEqualTo("\37" + "8 and");});

        input = "\\378";
        result = oue.translate(input);
        String finalResult4 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult4).as("Failed to unescape octal characters via the between method").isEqualTo("\37" + "8");});

        input = "\\1";
        result = oue.translate(input);
        String finalResult5 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult5).as("Failed to unescape octal characters via the between method").isEqualTo("\1");});

        input = "\\036";
        result = oue.translate(input);
        String finalResult6 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult6).as("Failed to unescape octal characters via the between method").isEqualTo("\036");});

        input = "\\0365";
        result = oue.translate(input);
        String finalResult7 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult7).as("Failed to unescape octal characters via the between method").isEqualTo("\036" + "5");});

        input = "\\003";
        result = oue.translate(input);
        String finalResult8 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult8).as("Failed to unescape octal characters via the between method").isEqualTo("\003");});

        input = "\\0003";
        result = oue.translate(input);
        String finalResult9 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult9).as("Failed to unescape octal characters via the between method").isEqualTo("\000" + "3");});

        input = "\\279";
        result = oue.translate(input);
        String finalResult10 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult10).as("Failed to unescape octal characters via the between method").isEqualTo("\279");});

        input = "\\999";
        result = oue.translate(input);
        String finalResult11 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult11).as("Failed to ignore an out of range octal character via the between method")
            .isEqualTo("\\999");});
    }

}
