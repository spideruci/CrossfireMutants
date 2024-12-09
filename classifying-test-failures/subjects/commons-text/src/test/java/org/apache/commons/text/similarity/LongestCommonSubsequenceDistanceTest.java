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
 * Tests {@link LongestCommonSubsequenceDistance}.
 */
public class LongestCommonSubsequenceDistanceTest {

    private static LongestCommonSubsequenceDistance subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequenceDistance();
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("", "")).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("left", "")).isEqualTo(4);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("", "right")).isEqualTo(5);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("frog", "fog")).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("fly", "ant")).isEqualTo(6);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("elephant", "hippo")).isEqualTo(11);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("ABC Corporation", "ABC Corp")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("D N H Enterprises Inc", "D & H Enterprises, Inc.")).isEqualTo(4);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness")).isEqualTo(9);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("PENNSYLVANIA", "PENNCISYLVNIA")).isEqualTo(3);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("left", "right")).isEqualTo(7);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("leettteft", "ritttght")).isEqualTo(9);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(subject.apply("the same string", "the same string")).isEqualTo(0);});
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistanceNullNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.apply(null, null));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistanceNullString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.apply(null, "right"));});
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistanceStringNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> subject.apply(" ", null));});
    }

}
