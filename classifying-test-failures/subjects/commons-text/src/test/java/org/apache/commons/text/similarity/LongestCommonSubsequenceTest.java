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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link LongestCommonSubsequence}.
 */
public class LongestCommonSubsequenceTest {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testGettingLogestCommonSubsequenceNullNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.logestCommonSubsequence(null, null));});
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testGettingLogestCommonSubsequenceNullString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.logestCommonSubsequence(null, "right"));});
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testGettingLogestCommonSubsequenceStringNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.logestCommonSubsequence(" ", null));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceApplyNullNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.apply(null, null));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceApplyNullString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.apply(null, "right"));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceApplyStringNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.apply(" ", null));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceNullNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.longestCommonSubsequence(null, null));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceNullString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.longestCommonSubsequence(null, "right"));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceStringNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.longestCommonSubsequence(" ", null));});
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("", "")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(subject.logestCommonSubsequence("left", "")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("", "right")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("frog", "fog")).isEqualTo("fog");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("fly", "ant")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("elephant", "hippo")).isEqualTo("h");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("ABC Corporation", "ABC Corp")).isEqualTo("ABC Corp");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."))
            .isEqualTo("D  H Enterprises Inc");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"))
            .isEqualTo("My Gym Childrens Fitness");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA")).isEqualTo("PENNSYLVNIA");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("left", "right")).isEqualTo("t");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("leettteft", "ritttght")).isEqualTo("tttt");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.logestCommonSubsequence("the same string", "the same string")).isEqualTo("the same string");});
    }

    @Test
    public void testLongestCommonSubsequence() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("", "")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("left", "")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("", "right")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("frog", "fog")).isEqualTo("fog");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("fly", "ant")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("elephant", "hippo")).isEqualTo("h");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("ABC Corporation", "ABC Corp")).isEqualTo("ABC Corp");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."))
            .isEqualTo("D  H Enterprises Inc");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"))
            .isEqualTo("My Gym Childrens Fitness");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA")).isEqualTo("PENNSYLVNIA");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("left", "right")).isEqualTo("t");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("leettteft", "ritttght")).isEqualTo("tttt");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.longestCommonSubsequence("the same string", "the same string")).isEqualTo("the same string");});
    }

    @Test
    public void testLongestCommonSubsequenceApply() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("", "")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("left", "")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("", "right")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("frog", "fog")).isEqualTo(3);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("fly", "ant")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("elephant", "hippo")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("ABC Corporation", "ABC Corp")).isEqualTo(8);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("D N H Enterprises Inc", "D & H Enterprises, Inc.")).isEqualTo(20);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness")).isEqualTo(24);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("PENNSYLVANIA", "PENNCISYLVNIA")).isEqualTo(11);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("left", "right")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("leettteft", "ritttght")).isEqualTo(4);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("the same string", "the same string")).isEqualTo(15);});
    }
}
