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
package org.apache.commons.text;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrMatcher}.
 *
 * @deprecated This class will be removed in 2.0.
 */
@Deprecated
public class StrMatcherTest  {

    private static final char[] BUFFER1 = "0,1\t2 3\n\r\f\u0000'\"".toCharArray();

    private static final char[] BUFFER2 = "abcdef".toCharArray();


    @Test
    public void testCharMatcher_char() {
        final StrMatcher matcher = StrMatcher.charMatcher('c');
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 0)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 2)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 3)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 4)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 5)).isEqualTo(0);});
    }

    @Test
    public void testCharSetMatcher_charArray() {
        final StrMatcher matcher = StrMatcher.charSetMatcher("ace".toCharArray());
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 0)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 2)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 3)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 4)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 5)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(StrMatcher.charSetMatcher()).isSameAs(StrMatcher.noneMatcher());});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(StrMatcher.charSetMatcher((char[]) null)).isSameAs(StrMatcher.noneMatcher());});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(StrMatcher.charSetMatcher("a".toCharArray()) instanceof StrMatcher.CharMatcher).isTrue();});
    }

    @Test
    public void testCharSetMatcher_String() {
        final StrMatcher matcher = StrMatcher.charSetMatcher("ace");
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 0)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 2)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 3)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 4)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 5)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.charSetMatcher("")).isSameAs(StrMatcher.noneMatcher());});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.charSetMatcher((String) null)).isSameAs(StrMatcher.noneMatcher());});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.charSetMatcher("a") instanceof StrMatcher.CharMatcher).isTrue();});
    }

    @Test
    public void testCommaMatcher() {
        final StrMatcher matcher = StrMatcher.commaMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.commaMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 0)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 1)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 2)).isEqualTo(0);});
    }

    @Test
    public void testDoubleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.doubleQuoteMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(StrMatcher.doubleQuoteMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(matcher.isMatch(BUFFER1, 11)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(matcher.isMatch(BUFFER1, 12)).isEqualTo(1);});
    }

    @Test
    public void testMatcherIndices() {
        // remember that the API contract is tight for the isMatch() method
        // all the onus is on the caller, so invalid inputs are not
        // the concern of StrMatcher, and are not bugs
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1, 1, BUFFER2.length)).isEqualTo(2);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1, 0, 3)).isEqualTo(2);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1, 0, 2)).isEqualTo(0);});
    }

    @Test
    public void testNoneMatcher() {
        final StrMatcher matcher = StrMatcher.noneMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.noneMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 0)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 1)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 2)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 3)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 4)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 5)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 6)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 7)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 8)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 9)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 10)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(matcher.isMatch(BUFFER1, 11)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 12)).isEqualTo(0);});
    }

    @Test
    public void testQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.quoteMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.quoteMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 10)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 11)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 12)).isEqualTo(1);});
    }

    @Test
    public void testSingleQuoteMatcher() {
        final StrMatcher matcher = StrMatcher.singleQuoteMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.singleQuoteMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 10)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 11)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 12)).isEqualTo(0);});
    }

    @Test
    public void testSpaceMatcher() {
        final StrMatcher matcher = StrMatcher.spaceMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.spaceMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 4)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 5)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 6)).isEqualTo(0);});
    }

    @Test
    public void testSplitMatcher() {
        final StrMatcher matcher = StrMatcher.splitMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.splitMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 2)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 3)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 4)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 5)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 6)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 7)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 8)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(matcher.isMatch(BUFFER1, 9)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(matcher.isMatch(BUFFER1, 10)).isEqualTo(0);});
    }

    @Test
    public void testStringMatcher_String() {
        final StrMatcher matcher = StrMatcher.stringMatcher("bc");
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 0)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 1)).isEqualTo(2);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 2)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 3)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 4)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER2, 5)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.stringMatcher("")).isSameAs(StrMatcher.noneMatcher());});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(StrMatcher.stringMatcher((String) null)).isSameAs(StrMatcher.noneMatcher());});
    }

    @Test
    public void testTabMatcher() {
        final StrMatcher matcher = StrMatcher.tabMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.tabMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 2)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(matcher.isMatch(BUFFER1, 3)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 4)).isEqualTo(0);});
    }

    @Test
    public void testTrimMatcher() {
        final StrMatcher matcher = StrMatcher.trimMatcher();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(StrMatcher.trimMatcher()).isSameAs(matcher);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 2)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 3)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 4)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 5)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 6)).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 7)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 8)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 9)).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(matcher.isMatch(BUFFER1, 10)).isEqualTo(1);});
    }

}
