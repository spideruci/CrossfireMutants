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

import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;

public class LevenshteinDetailedDistanceTest {

    private static final LevenshteinDetailedDistance UNLIMITED_DISTANCE = new LevenshteinDetailedDistance();

    @Test
    public void testApplyThrowsIllegalArgumentExceptionAndCreatesLevenshteinDetailedDistanceTakingInteger() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> {
            final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
            final CharSequence charSequence = new TextStringBuilder();

            levenshteinDetailedDistance.apply(charSequence, null);
        });});
    }

    @Test
    public void testApplyWithNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDetailedDistance(0).apply(null, null));});
    }

    @Test
    public void testConstructorWithNegativeThreshold() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> new LevenshteinDetailedDistance(-1));});
    }

    @Test
    public void testCreatesLevenshteinDetailedDistanceTakingInteger6() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);
        final LevenshteinResults levenshteinResults =
                levenshteinDetailedDistance.apply("", "Distance: 38, Insert: 0, Delete: 0, Substitute: 0");

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(levenshteinResults.getSubstituteCount()).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(levenshteinResults.getDeleteCount()).isEqualTo(0);});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(levenshteinResults.getInsertCount()).isEqualTo(0);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(levenshteinResults.getDistance()).isEqualTo(-1);});
    }

    @Test
    public void testEquals() {
     final LevenshteinDetailedDistance classBeingTested = new LevenshteinDetailedDistance();
     LevenshteinResults actualResult = classBeingTested.apply("hello", "hallo");
     LevenshteinResults expectedResult = new LevenshteinResults(1, 0, 0, 1);
        LevenshteinResults finalExpectedResult = expectedResult;
        LevenshteinResults finalActualResult = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult).isEqualTo(finalActualResult);});

     actualResult = classBeingTested.apply("zzzzzzzz", "hippo");
     expectedResult = new LevenshteinResults(8, 0, 3, 5);
        LevenshteinResults finalExpectedResult1 = expectedResult;
        LevenshteinResults finalActualResult1 = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult1).isEqualTo(finalActualResult1);});

     actualResult = classBeingTested.apply("", "");
     expectedResult = new LevenshteinResults(0, 0, 0, 0);
        LevenshteinResults finalExpectedResult2 = expectedResult;
        LevenshteinResults finalActualResult2 = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult2).isEqualTo(finalActualResult2);});
    }

    @Test
    public void testGetDefaultInstanceOne() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance =
                LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults =
                levenshteinDetailedDistance.apply("Distance: -2147483643, Insert: 0, Delete: 0, Substitute: 0",
                        "Distance: 0, Insert: 2147483536, Delete: 0, Substitute: 0");

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(levenshteinResults.getDistance()).isEqualTo(21);});
    }

    @Test
    public void testGetDefaultInstanceTwo() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance =
                LevenshteinDetailedDistance.getDefaultInstance();
        final LevenshteinResults levenshteinResults =
                levenshteinDetailedDistance.apply("Distance: 2147483647, Insert: 0, Delete: 0, Substitute: 0",
                        "Distance: 0, Insert: 2147483647, Delete: 0, Substitute: 0");

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(levenshteinResults.getDistance()).isEqualTo(20);});
    }

    @Test
    public void testGetLevenshteinDetailedDistance_NullString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply("a", null));});
    }

    @Test
    public void testGetLevenshteinDetailedDistance_NullStringInt() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply(null, "a"));});
    }

    @Test
    public void testGetLevenshteinDetailedDistance_StringNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply(null, "a"));});
    }

    @Test
    public void testGetLevenshteinDetailedDistance_StringNullInt() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> UNLIMITED_DISTANCE.apply("a", null));});
    }

    @Test
    public void testGetLevenshteinDetailedDistance_StringString() {
        LevenshteinResults result = UNLIMITED_DISTANCE.apply("", "");
        LevenshteinResults finalResult = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult.getDistance()).isEqualTo(0);});
        LevenshteinResults finalResult1 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult1.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult2 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult2.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult3 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult3.getSubstituteCount()).isEqualTo(0);});

        result = UNLIMITED_DISTANCE.apply("", "a");
        LevenshteinResults finalResult4 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult4.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult5 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult5.getInsertCount()).isEqualTo(1);});
        LevenshteinResults finalResult6 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult6.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult7 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult7.getSubstituteCount()).isEqualTo(0);});

        result = UNLIMITED_DISTANCE.apply("aaapppp", "");
        LevenshteinResults finalResult8 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult8.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult9 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult9.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult10 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult10.getDeleteCount()).isEqualTo(7);});
        LevenshteinResults finalResult11 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult11.getSubstituteCount()).isEqualTo(0);});

        result = UNLIMITED_DISTANCE.apply("frog", "fog");
        LevenshteinResults finalResult12 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult12.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult13 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult13.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult14 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult14.getDeleteCount()).isEqualTo(1);});
        LevenshteinResults finalResult15 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult15.getSubstituteCount()).isEqualTo(0);});

        result = UNLIMITED_DISTANCE.apply("fly", "ant");
        LevenshteinResults finalResult16 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult16.getDistance()).isEqualTo(3);});
        LevenshteinResults finalResult17 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult17.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult18 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult18.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult19 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult19.getSubstituteCount()).isEqualTo(3);});

        result = UNLIMITED_DISTANCE.apply("elephant", "hippo");
        LevenshteinResults finalResult20 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult20.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult21 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult21.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult22 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult22.getDeleteCount()).isEqualTo(3);});
        LevenshteinResults finalResult23 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult23.getSubstituteCount()).isEqualTo(4);});

        result = UNLIMITED_DISTANCE.apply("hippo", "elephant");
        LevenshteinResults finalResult24 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult24.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult25 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult25.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult26 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult26.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult27 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult27.getSubstituteCount()).isEqualTo(4);});

        result = UNLIMITED_DISTANCE.apply("hippo", "zzzzzzzz");
        LevenshteinResults finalResult28 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult28.getDistance()).isEqualTo(8);});
        LevenshteinResults finalResult29 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult29.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult30 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult30.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult31 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult31.getSubstituteCount()).isEqualTo(5);});

        result = UNLIMITED_DISTANCE.apply("zzzzzzzz", "hippo");
        LevenshteinResults finalResult32 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult32.getDistance()).isEqualTo(8);});
        LevenshteinResults finalResult33 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult33.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult34 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult34.getDeleteCount()).isEqualTo(3);});
        LevenshteinResults finalResult35 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult35.getSubstituteCount()).isEqualTo(5);});

        result = UNLIMITED_DISTANCE.apply("hello", "hallo");
        LevenshteinResults finalResult36 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult36.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult37 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult37.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult38 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult38.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult39 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult39.getSubstituteCount()).isEqualTo(1);});
    }

    @Test
    public void testGetLevenshteinDetailedDistance_StringStringInt() {

        LevenshteinResults result = new LevenshteinDetailedDistance(0).apply("", "");

        LevenshteinResults finalResult = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult.getDistance()).isEqualTo(0);});
        LevenshteinResults finalResult1 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult1.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult2 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult2.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult3 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult3.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(8).apply("aaapppp", "");
        LevenshteinResults finalResult4 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult4.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult5 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult5.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult6 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult6.getDeleteCount()).isEqualTo(7);});
        LevenshteinResults finalResult7 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult7.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(7).apply("aaapppp", "");
        LevenshteinResults finalResult8 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult8.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult9 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult9.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult10 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult10.getDeleteCount()).isEqualTo(7);});
        LevenshteinResults finalResult11 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult11.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(6).apply("aaapppp", "");
        LevenshteinResults finalResult12 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult12.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult13 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult13.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult14 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult14.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult15 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult15.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(0).apply("b", "a");
        LevenshteinResults finalResult16 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult16.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult17 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult17.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult18 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult18.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult19 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult19.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(0).apply("a", "b");
        LevenshteinResults finalResult20 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult20.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult21 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult21.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult22 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult22.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult23 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult23.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(0).apply("aa", "aa");
        LevenshteinResults finalResult24 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(finalResult24.getDistance()).isEqualTo(0);});
        LevenshteinResults finalResult25 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult25.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult26 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult26.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult27 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult27.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(2).apply("aa", "aa");
        LevenshteinResults finalResult28 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult28.getDistance()).isEqualTo(0);});
        LevenshteinResults finalResult29 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult29.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult30 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult30.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult31 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult31.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(2).apply("aaa", "bbb");
        LevenshteinResults finalResult32 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult32.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult33 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult33.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult34 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult34.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult35 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult35.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(3).apply("aaa", "bbb");
        LevenshteinResults finalResult36 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult36.getDistance()).isEqualTo(3);});
        LevenshteinResults finalResult37 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult37.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult38 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult38.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult39 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult39.getSubstituteCount()).isEqualTo(3);});

        result = new LevenshteinDetailedDistance(10).apply("aaaaaa", "b");
        LevenshteinResults finalResult40 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(finalResult40.getDistance()).isEqualTo(6);});
        LevenshteinResults finalResult41 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(finalResult41.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult42 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(finalResult42.getDeleteCount()).isEqualTo(5);});
        LevenshteinResults finalResult43 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult43.getSubstituteCount()).isEqualTo(1);});

        result = new LevenshteinDetailedDistance(8).apply("aaapppp", "b");
        LevenshteinResults finalResult44 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult44.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult45 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult45.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult46 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult46.getDeleteCount()).isEqualTo(6);});
        LevenshteinResults finalResult47 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult47.getSubstituteCount()).isEqualTo(1);});

        result = new LevenshteinDetailedDistance(4).apply("a", "bbb");
        LevenshteinResults finalResult48 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult48.getDistance()).isEqualTo(3);});
        LevenshteinResults finalResult49 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult49.getInsertCount()).isEqualTo(2);});
        LevenshteinResults finalResult50 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult50.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult51 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult51.getSubstituteCount()).isEqualTo(1);});

        result = new LevenshteinDetailedDistance(7).apply("aaapppp", "b");
        LevenshteinResults finalResult52 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult52.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult53 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult53.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult54 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult54.getDeleteCount()).isEqualTo(6);});
        LevenshteinResults finalResult55 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult55.getSubstituteCount()).isEqualTo(1);});

        result = new LevenshteinDetailedDistance(3).apply("a", "bbb");
        LevenshteinResults finalResult56 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult56.getDistance()).isEqualTo(3);});
        LevenshteinResults finalResult57 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult57.getInsertCount()).isEqualTo(2);});
        LevenshteinResults finalResult58 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult58.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult59 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult59.getSubstituteCount()).isEqualTo(1);});

        result = new LevenshteinDetailedDistance(2).apply("a", "bbb");
        LevenshteinResults finalResult60 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult60.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult61 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult61.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult62 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult62.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult63 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult63.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(2).apply("bbb", "a");
        LevenshteinResults finalResult64 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult64.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult65 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult65.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult66 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult66.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult67 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult67.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(6).apply("aaapppp", "b");
        LevenshteinResults finalResult68 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult68.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult69 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult69.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult70 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult70.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult71 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult71.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(1).apply("a", "bbb");
        LevenshteinResults finalResult72 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(finalResult72.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult73 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult73.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult74 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult74.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult75 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult75.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(1).apply("bbb", "a");
        LevenshteinResults finalResult76 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult76.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult77 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult77.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult78 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult78.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult79 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult79.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(1).apply("12345", "1234567");
        LevenshteinResults finalResult81 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult81.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult80 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult80.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult82 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult82.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult83 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult83.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(1).apply("1234567", "12345");
        LevenshteinResults finalResult84 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult84.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult85 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult85.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult86 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult86.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult87 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult87.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(1).apply("frog", "fog");
        LevenshteinResults finalResult88 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult88.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult89 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult89.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult90 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult90.getDeleteCount()).isEqualTo(1);});
        LevenshteinResults finalResult91 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult91.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(3).apply("fly", "ant");
        LevenshteinResults finalResult92 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult92.getDistance()).isEqualTo(3);});
        LevenshteinResults finalResult93 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult93.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult94 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult94.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult95 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult95.getSubstituteCount()).isEqualTo(3);});

        result = new LevenshteinDetailedDistance(7).apply("elephant", "hippo");
        LevenshteinResults finalResult96 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult96.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult97 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult97.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult98 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult98.getDeleteCount()).isEqualTo(3);});
        LevenshteinResults finalResult99 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult99.getSubstituteCount()).isEqualTo(4);});

        result = new LevenshteinDetailedDistance(6).apply("elephant", "hippo");
        LevenshteinResults finalResult100 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult100.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult101 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult101.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult102 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult102.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult103 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult103.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(7).apply("hippo", "elephant");
        LevenshteinResults finalResult104 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult104.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult105 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult105.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult106 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult106.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult107 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult107.getSubstituteCount()).isEqualTo(4);});

        result = new LevenshteinDetailedDistance(7).apply("hippo", "elephant");
        LevenshteinResults finalResult108 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult108.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult109 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult109.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult110 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult110.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult111 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult111.getSubstituteCount()).isEqualTo(4);});

        result = new LevenshteinDetailedDistance(6).apply("hippo", "elephant");
        LevenshteinResults finalResult112 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult112.getDistance()).isEqualTo(-1);});
        LevenshteinResults finalResult113 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult113.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult114 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult114.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult115 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult115.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(8).apply("hippo", "zzzzzzzz");
        LevenshteinResults finalResult116 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult116.getDistance()).isEqualTo(8);});
        LevenshteinResults finalResult117 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult117.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult118 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult118.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult119 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult119.getSubstituteCount()).isEqualTo(5);});

        result = new LevenshteinDetailedDistance(8).apply("zzzzzzzz", "hippo");
        LevenshteinResults finalResult120 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult120.getDistance()).isEqualTo(8);});
        LevenshteinResults finalResult121 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult121.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult122 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult122.getDeleteCount()).isEqualTo(3);});
        LevenshteinResults finalResult123 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult123.getSubstituteCount()).isEqualTo(5);});

        result = new LevenshteinDetailedDistance(1).apply("hello", "hallo");
        LevenshteinResults finalResult124 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult124.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult125 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult125.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult126 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult126.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult127 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult127.getSubstituteCount()).isEqualTo(1);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("frog", "fog");
        LevenshteinResults finalResult128 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult128.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult129 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult129.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult130 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult130.getDeleteCount()).isEqualTo(1);});
        LevenshteinResults finalResult131 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult131.getSubstituteCount()).isEqualTo(0);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("fly", "ant");
        LevenshteinResults finalResult132 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult132.getDistance()).isEqualTo(3);});
        LevenshteinResults finalResult133 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult133.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult134 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult134.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult135 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult135.getSubstituteCount()).isEqualTo(3);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("elephant", "hippo");
        LevenshteinResults finalResult136 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult136.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult137 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult137.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult138 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult138.getDeleteCount()).isEqualTo(3);});
        LevenshteinResults finalResult139 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult139.getSubstituteCount()).isEqualTo(4);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "elephant");
        LevenshteinResults finalResult140 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult140.getDistance()).isEqualTo(7);});
        LevenshteinResults finalResult141 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult141.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult142 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult142.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult143 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult143.getSubstituteCount()).isEqualTo(4);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hippo", "zzzzzzzz");
        LevenshteinResults finalResult144 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(finalResult144.getDistance()).isEqualTo(8);});
        LevenshteinResults finalResult145 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult145.getInsertCount()).isEqualTo(3);});
        LevenshteinResults finalResult146 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult146.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult147 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult147.getSubstituteCount()).isEqualTo(5);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("zzzzzzzz", "hippo");
        LevenshteinResults finalResult148 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult148.getDistance()).isEqualTo(8);});
        LevenshteinResults finalResult149 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult149.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult150 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult150.getDeleteCount()).isEqualTo(3);});
        LevenshteinResults finalResult151 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult151.getSubstituteCount()).isEqualTo(5);});

        result = new LevenshteinDetailedDistance(Integer.MAX_VALUE).apply("hello", "hallo");
        LevenshteinResults finalResult152 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult152.getDistance()).isEqualTo(1);});
        LevenshteinResults finalResult153 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult153.getInsertCount()).isEqualTo(0);});
        LevenshteinResults finalResult154 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult154.getDeleteCount()).isEqualTo(0);});
        LevenshteinResults finalResult155 = result;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalResult155.getSubstituteCount()).isEqualTo(1);});
    }

    @Test
    public void testGetThreshold() {
        final LevenshteinDetailedDistance levenshteinDetailedDistance = new LevenshteinDetailedDistance(0);

        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(levenshteinDetailedDistance.getThreshold()).isEqualTo(0);});
    }

    @Test
    public void testHashCode() {
     final LevenshteinDetailedDistance classBeingTested = new LevenshteinDetailedDistance();
     LevenshteinResults actualResult = classBeingTested.apply("aaapppp", "");
     LevenshteinResults expectedResult = new LevenshteinResults(7, 0, 7, 0);
        LevenshteinResults finalExpectedResult = expectedResult;
        LevenshteinResults finalActualResult = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult.hashCode()).isEqualTo(finalActualResult.hashCode());});

     actualResult = classBeingTested.apply("frog", "fog");
     expectedResult = new LevenshteinResults(1, 0, 1, 0);
        LevenshteinResults finalExpectedResult1 = expectedResult;
        LevenshteinResults finalActualResult1 = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult1.hashCode()).isEqualTo(finalActualResult1.hashCode());});

     actualResult = classBeingTested.apply("elephant", "hippo");
     expectedResult = new LevenshteinResults(7, 0, 3, 4);
        LevenshteinResults finalExpectedResult2 = expectedResult;
        LevenshteinResults finalActualResult2 = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult2.hashCode()).isEqualTo(finalActualResult2.hashCode());});
    }

    @Test
    public void testToString() {
     final LevenshteinDetailedDistance classBeingTested = new LevenshteinDetailedDistance();
     LevenshteinResults actualResult = classBeingTested.apply("fly", "ant");
     LevenshteinResults expectedResult = new LevenshteinResults(3, 0, 0, 3);
     LevenshteinResults finalExpectedResult = expectedResult;
        LevenshteinResults finalActualResult = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult.toString()).isEqualTo(finalActualResult.toString());});

     actualResult = classBeingTested.apply("hippo", "elephant");
     expectedResult = new LevenshteinResults(7, 3, 0, 4);
     LevenshteinResults finalExpectedResult1 = expectedResult;
        LevenshteinResults finalActualResult1 = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult1.toString()).isEqualTo(finalActualResult1.toString());});

     actualResult = classBeingTested.apply("", "a");
     expectedResult = new LevenshteinResults(1, 1, 0, 0);
     LevenshteinResults finalExpectedResult2 = expectedResult;
        LevenshteinResults finalActualResult2 = actualResult;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(finalExpectedResult2.toString()).isEqualTo(finalActualResult2.toString());});
    }

}
