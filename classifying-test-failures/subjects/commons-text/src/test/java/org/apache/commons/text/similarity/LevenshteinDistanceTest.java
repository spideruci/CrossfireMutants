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
package org.apache.commons.text.similarity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link LevenshteinDistance}.
 */
public class LevenshteinDistanceTest {

    private static final LevenshteinDistance UNLIMITED_DISTANCE = new LevenshteinDistance();

    @Test
    public void testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDistanceTakingInteger() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(0).apply(null, null));});
    }

    @Test
    public void testConstructorWithNegativeThreshold() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDistance(-1));});
    }

    @Test
    public void testGetLevenshteinDistance_NullString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply("a", null));});
    }

    @Test
    public void testGetLevenshteinDistance_NullStringInt() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply(null, "a"));});
    }

    @Test
    public void testGetLevenshteinDistance_StringNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply(null, "a"));});
    }

    @Test
    public void testGetLevenshteinDistance_StringNullInt() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply("a", null));});
    }

    @Test
    public void testGetLevenshteinDistance_StringString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(UNLIMITED_DISTANCE.apply("", "")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("", "a")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("aaapppp", "")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("frog", "fog")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("fly", "ant")).isEqualTo(3);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("elephant", "hippo")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("hippo", "elephant")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(UNLIMITED_DISTANCE.apply("hippo", "zzzzzzzz")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("zzzzzzzz", "hippo")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(UNLIMITED_DISTANCE.apply("hello", "hallo")).isEqualTo(1);});
    }

    @Test
    public void testGetLevenshteinDistance_StringStringInt() {
        // empty strings
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(0).apply("", "")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(8).apply("aaapppp", "")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(7).apply("aaapppp", "")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(6).apply("aaapppp", "")).isEqualTo(-1);});

        // unequal strings, zero threshold
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(0).apply("b", "a")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(0).apply("a", "b")).isEqualTo(-1);});

        // equal strings
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(0).apply("aa", "aa")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(2).apply("aa", "aa")).isEqualTo(0);});

        // same length
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(2).apply("aaa", "bbb")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(3).apply("aaa", "bbb")).isEqualTo(3);});

        // big stripe
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(10).apply("aaaaaa", "b")).isEqualTo(6);});

        // distance less than threshold
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(8).apply("aaapppp", "b")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(4).apply("a", "bbb")).isEqualTo(3);});

        // distance equal to threshold
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(7).apply("aaapppp", "b")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(3).apply("a", "bbb")).isEqualTo(3);});

        // distance greater than threshold
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(2).apply("a", "bbb")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(2).apply("bbb", "a")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(6).apply("aaapppp", "b")).isEqualTo(-1);});

        // stripe runs off array, strings not similar
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(1).apply("a", "bbb")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(1).apply("bbb", "a")).isEqualTo(-1);});

        // stripe runs off array, strings are similar
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(1).apply("12345", "1234567")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(1).apply("1234567", "12345")).isEqualTo(-1);});

        // old getLevenshteinDistance test cases
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(1).apply("frog", "fog")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(3).apply("fly", "ant")).isEqualTo(3);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(7).apply("elephant", "hippo")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(6).apply("elephant", "hippo")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(7).apply("hippo", "elephant")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(6).apply("hippo", "elephant")).isEqualTo(-1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(8).apply("hippo", "zzzzzzzz")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(8).apply("zzzzzzzz", "hippo")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(1).apply("hello", "hallo")).isEqualTo(1);});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("frog", "fog")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("fly", "ant")).isEqualTo(3);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("elephant", "hippo")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("hippo", "elephant")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("hippo", "zzzzzzzz")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("zzzzzzzz", "hippo")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(Integer.MAX_VALUE).apply("hello", "hallo")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance(1).apply("abc", "acb")).isEqualTo(-1);});
    }

    @Test
    public void testGetThresholdDirectlyAfterObjectInstantiation() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new LevenshteinDistance().getThreshold()).isNull();});
    }

}
