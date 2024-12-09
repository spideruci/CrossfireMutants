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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils} class.
 */
public class WordUtilsTest {

    @Test
    public void testAbbreviateForLowerThanMinusOneValues() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> assertThat(WordUtils.abbreviate("01 23 45 67 89", 9, -10, null)).isEqualTo("01 23 45 67"));});
    }

     @Test
    public void testAbbreviateForLowerValue() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("012 3456789", 0, 5, null)).isEqualTo("012");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01234 56789", 5, 10, null)).isEqualTo("01234");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 9, -1, null)).isEqualTo("01 23 45 67");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 9, 10, null)).isEqualTo("01 23 45 6");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("0123456789", 15, 20, null)).isEqualTo("0123456789");});
    }

     @Test
    public void testAbbreviateForLowerValueAndAppendedString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("012 3456789", 0, 5, null)).isEqualTo("012");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01234 56789", 5, 10, "-")).isEqualTo("01234-");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 9, -1, "abc")).isEqualTo("01 23 45 67abc");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 9, 10, "")).isEqualTo("01 23 45 6");});
    }

     @Test
    public void testAbbreviateForNullAndEmptyString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate(null, 1, -1, "")).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("", 1, -1, "")).isEqualTo(StringUtils.EMPTY);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("0123456790", 0, 0, "")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate(" 0123456790", 0, -1, "")).isEqualTo("");});
    }

     @Test
    public void testAbbreviateForUpperLimit() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("0123456789", 0, 5, "")).isEqualTo("01234");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("012 3456789", 2, 5, "")).isEqualTo("012");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("0123456789", 0, -1, "")).isEqualTo("0123456789");});
    }

     @Test
    public void testAbbreviateForUpperLimitAndAppendedString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(WordUtils.abbreviate("0123456789", 0, 5, "-")).isEqualTo("01234-");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("012 3456789", 2, 5, null)).isEqualTo("012");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("0123456789", 0, -1, "")).isEqualTo("0123456789");});
    }

    @Test
    public void testAbbreviateUpperLessThanLowerValues() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThatIllegalArgumentException().isThrownBy(() -> assertThat(WordUtils.abbreviate("0123456789", 5, 2, "")).isEqualTo("01234"));});
    }

     @Test
    public void testCapitalize_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize(null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("  ")).isEqualTo("  ");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("I")).isEqualTo("I");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i")).isEqualTo("I");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i am here 123")).isEqualTo("I Am Here 123");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("I Am Here 123")).isEqualTo("I Am Here 123");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i am HERE 123")).isEqualTo("I Am HERE 123");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("I AM HERE 123")).isEqualTo("I AM HERE 123");});
    }

    @Test
    public void testCapitalizeFully_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully(null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("  ")).isEqualTo("  ");});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("I")).isEqualTo("I");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i")).isEqualTo("I");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i am here 123")).isEqualTo("I Am Here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("I Am Here 123")).isEqualTo("I Am Here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i am HERE 123")).isEqualTo("I Am Here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("I AM HERE 123")).isEqualTo("I Am Here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("alphabet")).isEqualTo("Alphabet");}); // single word
    }

    @Test
    public void testCapitalizeFully_Text88() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i am fine now", new char[] {})).isEqualTo("I am fine now");});
    }

    @Test
    public void testCapitalizeFullyWithDelimiters_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully(null, null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("", ArrayUtils.EMPTY_CHAR_ARRAY)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("  ", ArrayUtils.EMPTY_CHAR_ARRAY)).isEqualTo("  ");});

        char[] chars = {'-', '+', ' ', '@'};
        char[] finalChars = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("I", finalChars)).isEqualTo("I");});
        char[] finalChars1 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i", finalChars1)).isEqualTo("I");});
        char[] finalChars2 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i-am here+123", finalChars2)).isEqualTo("I-Am Here+123");});
        char[] finalChars3 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("I Am+Here-123", finalChars3)).isEqualTo("I Am+Here-123");});
        char[] finalChars4 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i+am-HERE 123", finalChars4)).isEqualTo("I+Am-Here 123");});
        char[] finalChars5 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(WordUtils.capitalizeFully("I-AM HERE+123", finalChars5)).isEqualTo("I-Am Here+123");});
        chars = new char[] {'.'};
        char[] finalChars6 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i aM.fine", finalChars6)).isEqualTo("I am.Fine");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("i am.fine", null)).isEqualTo("I Am.fine");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("alphabet", null)).isEqualTo("Alphabet"); });// single word
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalizeFully("alphabet", new char[] {'!'})).isEqualTo("Alphabet");});// no matching delim
    }

    @Test
    public void testCapitalizeWithDelimiters_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize(null, null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("", ArrayUtils.EMPTY_CHAR_ARRAY)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY)).isEqualTo("  ");});

        char[] chars = {'-', '+', ' ', '@'};
        char[] finalChars = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("I", finalChars)).isEqualTo("I");});
        char[] finalChars1 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i", finalChars1)).isEqualTo("I");});
        char[] finalChars2 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i-am here+123", finalChars2)).isEqualTo("I-Am Here+123");});
        char[] finalChars3 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("I Am+Here-123", finalChars3)).isEqualTo("I Am+Here-123");});
        char[] finalChars4 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i+am-HERE 123", finalChars4)).isEqualTo("I+Am-HERE 123");});
        char[] finalChars5 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("I-AM HERE+123", finalChars5)).isEqualTo("I-AM HERE+123");});
        chars = new char[] {'.'};
        char[] finalChars6 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i aM.fine", finalChars6)).isEqualTo("I aM.Fine");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.capitalize("i am.fine", null)).isEqualTo("I Am.fine");});
    }

     @Test
    public void testConstructor() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(new WordUtils()).isNotNull();});
        final Constructor<?>[] cons = WordUtils.class.getDeclaredConstructors();
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(cons.length).isEqualTo(1);});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(Modifier.isPublic(cons[0].getModifiers())).isTrue();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(Modifier.isPublic(WordUtils.class.getModifiers())).isTrue();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(Modifier.isFinal(WordUtils.class.getModifiers())).isFalse();});
    }

    @Test
    public void testContainsAllWords_StringString() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords(null, (String) null)).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords(null, "")).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords(null, "ab")).isFalse();});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("", (String) null)).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("", "")).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("", "ab")).isFalse();});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("foo", (String) null)).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("bar", "")).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("zzabyycdxx", "by")).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor")).isTrue();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor")).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"))
            .isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("ab", "b")).isFalse();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.containsAllWords("ab", "z")).isFalse();});
    }

    @Test
    public void testContainsAllWordsWithNull() {
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(WordUtils.containsAllWords("M", (CharSequence) null)).isFalse();});
    }

     @Test
    public void testInitials_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ")).isEqualTo("");});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I")).isEqualTo("I");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i")).isEqualTo("i");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee")).isEqualTo("BJL");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("   Ben \n   John\tLee\t")).isEqualTo("BJL");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee")).isEqualTo("BJ");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee")).isEqualTo("BJ.L");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i am here 123")).isEqualTo("iah1");});
    }

     @Test
    public void testInitials_String_charArray() {
        char[] array = null;
         char[] finalArray = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null, finalArray)).isNull();});
         char[] finalArray1 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("", finalArray1)).isEqualTo("");});
         char[] finalArray2 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ", finalArray2)).isEqualTo("");});
         char[] finalArray3 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I", finalArray3)).isEqualTo("I");});
         char[] finalArray4 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i", finalArray4)).isEqualTo("i");});
         char[] finalArray5 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("SJC", finalArray5)).isEqualTo("S");});
         char[] finalArray6 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee", finalArray6)).isEqualTo("BJL");});
         char[] finalArray7 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("   Ben \n   John\tLee\t", finalArray7)).isEqualTo("BJL");});
         char[] finalArray8 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee", finalArray8)).isEqualTo("BJ");});
         char[] finalArray9 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee", finalArray9)).isEqualTo("BJ.L");});
         char[] finalArray10 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Kay O'Murphy", finalArray10)).isEqualTo("KO");});
         char[] finalArray11 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i am here 123", finalArray11)).isEqualTo("iah1");});

        array = ArrayUtils.EMPTY_CHAR_ARRAY;
         char[] finalArray12 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null, finalArray12)).isNull();});
         char[] finalArray13 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("", finalArray13)).isEqualTo("");});
         char[] finalArray14 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ", finalArray14)).isEqualTo("");});
         char[] finalArray15 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I", finalArray15)).isEqualTo("");});
         char[] finalArray16 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i", finalArray16)).isEqualTo("");});
         char[] finalArray17 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("SJC", finalArray17)).isEqualTo("");});
         char[] finalArray18 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee", finalArray18)).isEqualTo("");});
         char[] finalArray68 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("   Ben \n   John\tLee\t", finalArray68)).isEqualTo("");});
         char[] finalArray19 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee", finalArray19)).isEqualTo("");});
         char[] finalArray20 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee", finalArray20)).isEqualTo("");});
         char[] finalArray21 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Kay O'Murphy", finalArray21)).isEqualTo("");});
         char[] finalArray22 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i am here 123", finalArray22)).isEqualTo("");});

        array = " ".toCharArray();
         char[] finalArray23 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null, finalArray23)).isNull();});
         char[] finalArray24 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("", finalArray24)).isEqualTo("");});
         char[] finalArray25 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ", finalArray25)).isEqualTo("");});
         char[] finalArray26 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I", finalArray26)).isEqualTo("I");});
         char[] finalArray27 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i", finalArray27)).isEqualTo("i");});
         char[] finalArray28 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("SJC", finalArray28)).isEqualTo("S");});
         char[] finalArray29 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee", finalArray29)).isEqualTo("BJL");});
         char[] finalArray30 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee", finalArray30)).isEqualTo("BJ");});
         char[] finalArray31 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("   Ben \n   John\tLee\t", finalArray31)).isEqualTo("B\nJ");});
         char[] finalArray32 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee", finalArray32)).isEqualTo("BJ.L");});
         char[] finalArray33 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Kay O'Murphy", finalArray33)).isEqualTo("KO");});
         char[] finalArray34 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i am here 123", finalArray34)).isEqualTo("iah1");});

        array = " .".toCharArray();
         char[] finalArray35 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null, finalArray35)).isNull();});
         char[] finalArray36 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("", finalArray36)).isEqualTo("");});
         char[] finalArray37 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ", finalArray37)).isEqualTo("");});
         char[] finalArray38 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I", finalArray38)).isEqualTo("I");});
         char[] finalArray39 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i", finalArray39)).isEqualTo("i");});
         char[] finalArray40 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("SJC", finalArray40)).isEqualTo("S");});
         char[] finalArray41 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee", finalArray41)).isEqualTo("BJL");});
         char[] finalArray42 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee", finalArray42)).isEqualTo("BJL");});
         char[] finalArray43 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee", finalArray43)).isEqualTo("BJL");});
         char[] finalArray44 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Kay O'Murphy", finalArray44)).isEqualTo("KO");});
         char[] finalArray45 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(WordUtils.initials("i am here 123", finalArray45)).isEqualTo("iah1");});

        array = " .'".toCharArray();
         char[] finalArray46 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null, finalArray46)).isNull();});
         char[] finalArray47 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("", finalArray47)).isEqualTo("");});
         char[] finalArray48 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ", finalArray48)).isEqualTo("");});
         char[] finalArray49 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I", finalArray49)).isEqualTo("I");});
         char[] finalArray51 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i", finalArray51)).isEqualTo("i");});
         char[] finalArray50 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("SJC", finalArray50)).isEqualTo("S");});
         char[] finalArray52 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee", finalArray52)).isEqualTo("BJL");});
         char[] finalArray53 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee", finalArray53)).isEqualTo("BJL");});
         char[] finalArray54 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee", finalArray54)).isEqualTo("BJL");});
         char[] finalArray55 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Kay O'Murphy", finalArray55)).isEqualTo("KOM");});
         char[] finalArray56 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i am here 123", finalArray56)).isEqualTo("iah1");});

        array = "SIJo1".toCharArray();
         char[] finalArray57 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(null, finalArray57)).isNull();});
         char[] finalArray58 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("", finalArray58)).isEqualTo("");});
         char[] finalArray59 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("  ", finalArray59)).isEqualTo(" ");});
         char[] finalArray60 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("I", finalArray60)).isEqualTo("");});
         char[] finalArray61 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i", finalArray61)).isEqualTo("i");});
         char[] finalArray62 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("SJC", finalArray62)).isEqualTo("C");});
         char[] finalArray63 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben John Lee", finalArray63)).isEqualTo("Bh");});
         char[] finalArray64 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Ben J.Lee", finalArray64)).isEqualTo("B.");});
         char[] finalArray65 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(" Ben   John  . Lee", finalArray65)).isEqualTo(" h");});
         char[] finalArray66 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("Kay O'Murphy", finalArray66)).isEqualTo("K");});
         char[] finalArray67 = array;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("i am here 123", finalArray67)).isEqualTo("i2");});
    }

    @Test
    public void testInitialsSurrogatePairs() {
        // Tests with space as default delimiter
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("\uD800\uDF00\uD800\uDF01 \uD800\uDF02\uD800\uDF03"))
            .isEqualTo("\uD800\uDF00\uD800\uDF02");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("\uD800\uDF00\uD800\uDF01 \uD800\uDF02\uD800\uDF03", null))
            .isEqualTo("\uD800\uDF00\uD800\uDF02");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("\uD800\uDF00 \uD800\uDF02 ", null)).isEqualTo("\uD800\uDF00\uD800\uDF02");});

        // Tests with UTF-16 as delimiters
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("\uD800\uDF00\uD800\uDF01.\uD800\uDF02\uD800\uDF03", new char[] {'.'}))
            .isEqualTo("\uD800\uDF00\uD800\uDF02");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("\uD800\uDF00\uD800\uDF01A\uD800\uDF02\uD800\uDF03", new char[] {'A'}))
            .isEqualTo("\uD800\uDF00\uD800\uDF02");});

        // Tests with UTF-32 as delimiters
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials(
                "\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF02\uD800\uDF03", new char[] {'\uD800', '\uDF14'}))
            .isEqualTo("\uD800\uDF00\uD800\uDF02");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.initials("\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF18\uD800\uDF02\uD800\uDF03",
                        new char[] {'\uD800', '\uDF14', '\uD800', '\uDF18'}))
            .isEqualTo("\uD800\uDF00\uD800\uDF02");});
    }

    @Test
    public void testLANG1292() {
        // Prior to fix, this was throwing StringIndexOutOfBoundsException
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 70);
    }

    @Test
    public void testLANG673() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 0, 40, "")).isEqualTo("01");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 10, 40, "")).isEqualTo("01 23 45 67");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.abbreviate("01 23 45 67 89", 40, 40, "")).isEqualTo("01 23 45 67 89");});
    }

     @Test
    public void testSwapCase_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase(null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("  ")).isEqualTo("  ");});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("I")).isEqualTo("i");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("i")).isEqualTo("I");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("i am here 123")).isEqualTo("I AM HERE 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("I Am Here 123")).isEqualTo("i aM hERE 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("i am HERE 123")).isEqualTo("I AM here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase("I AM HERE 123")).isEqualTo("i am here 123");});

        final String test = "This String contains a TitleCase character: \u01C8";
        final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.swapCase(test)).isEqualTo(expect);});
    }

    @Test
    public void testText123() throws Exception {
        // Prior to fix, this was throwing StringIndexOutOfBoundsException
        WordUtils.wrap("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Integer.MAX_VALUE);
    }

    @Test
    public void testUncapitalize_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize(null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("")).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("  ")).isEqualTo("  ");});

        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(WordUtils.uncapitalize("I")).isEqualTo("i");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("i")).isEqualTo("i");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("i am here 123")).isEqualTo("i am here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I Am Here 123")).isEqualTo("i am here 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("i am HERE 123")).isEqualTo("i am hERE 123");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I AM HERE 123")).isEqualTo("i aM hERE 123");});
    }

    @Test
    public void testUnCapitalize_Text88() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I am fine now", new char[] {})).isEqualTo("i am fine now");});
    }

    @Test
    public void testUncapitalizeWithDelimiters_String() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize(null, null)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("", ArrayUtils.EMPTY_CHAR_ARRAY)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("  ", ArrayUtils.EMPTY_CHAR_ARRAY)).isEqualTo("  ");});

        char[] chars = {'-', '+', ' ', '@'};
        char[] finalChars = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I", finalChars)).isEqualTo("i");});
        char[] finalChars1 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("i", finalChars1)).isEqualTo("i");});
        char[] finalChars2 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("i am-here+123", finalChars2)).isEqualTo("i am-here+123");});
        char[] finalChars3 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I+Am Here-123", finalChars3)).isEqualTo("i+am here-123");});
        char[] finalChars4 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("i-am+HERE 123", finalChars4)).isEqualTo("i-am+hERE 123");});
        char[] finalChars5 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I AM-HERE+123", finalChars5)).isEqualTo("i aM-hERE+123");});
        chars = new char[] {'.'};
        char[] finalChars6 = chars;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I AM.FINE", finalChars6)).isEqualTo("i AM.fINE");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.uncapitalize("I AM.FINE", null)).isEqualTo("i aM.FINE");});
    }

     @Test
    public void testWrap_StringInt() {
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, 20)).isNull();});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, -1)).isNull();});

         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", 20)).isEqualTo("");});
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", -1)).isEqualTo("");});

        // normal
        final String systemNewLine = System.lineSeparator();
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine
                + "to be wrapped after" + systemNewLine + "20 columns.";
         String finalInput = input;
         String finalExpected = expected;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput, 20)).isEqualTo(finalExpected);});

        // long word at end
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump" + systemNewLine + "to the commons" + systemNewLine + "website -" + systemNewLine
                + "https://commons.apache.org";
         String finalInput1 = input;
         String finalExpected1 = expected;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput1, 20)).isEqualTo(finalExpected1);});

        // long word in middle
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here," + systemNewLine + "https://commons.apache.org," + systemNewLine + "to jump to the"
                + systemNewLine + "commons website";
         String finalInput2 = input;
         String finalExpected2 = expected;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput2, 20)).isEqualTo(finalExpected2);});

        // leading spaces on a new line are stripped
        // trailing spaces are not stripped
        input = "word1             word2                        word3";
        expected = "word1  " + systemNewLine + "word2  " + systemNewLine + "word3";
         String finalInput3 = input;
         String finalExpected3 = expected;
         ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput3, 7)).isEqualTo(finalExpected3);});
    }

    @Test
    public void testWrap_StringIntStringBoolean() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, 20, "\n", false)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, 20, "\n", true)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, 20, null, true)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, 20, null, false)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, -1, null, true)).isNull();});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(null, -1, null, false)).isNull();});

        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", 20, "\n", false)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", 20, "\n", true)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", 20, null, false)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", 20, null, true)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", -1, null, false)).isEqualTo("");});
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("", -1, null, true)).isEqualTo("");});

        // normal
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";
        String finalInput = input;
        String finalExpected = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput, 20, "\n", false)).isEqualTo(finalExpected);});
        String finalInput1 = input;
        String finalExpected1 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput1, 20, "\n", true)).isEqualTo(finalExpected1);});

        // unusual newline char
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";
        String finalInput2 = input;
        String finalExpected2 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput2, 20, "<br />", false)).isEqualTo(finalExpected2);});
        String finalInput3 = input;
        String finalExpected3 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput3, 20, "<br />", true)).isEqualTo(finalExpected3);});

        // short line length
        input = "Here is one line";
        expected = "Here\nis one\nline";
        String finalInput4 = input;
        String finalExpected4 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput4, 6, "\n", false)).isEqualTo(finalExpected4);});
        expected = "Here\nis\none\nline";
        String finalInput5 = input;
        String finalExpected5 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput5, 2, "\n", false)).isEqualTo(finalExpected5);});
        String finalInput6 = input;
        String finalExpected6 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput6, -1, "\n", false)).isEqualTo(finalExpected6);});

        // system newline char
        final String systemNewLine = System.lineSeparator();
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after"
                + systemNewLine + "20 columns.";
        String finalInput7 = input;
        String finalExpected7 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput7, 20, null, false)).isEqualTo(finalExpected7);});
        String finalInput8 = input;
        String finalExpected8 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput8, 20, null, true)).isEqualTo(finalExpected8);});

        // with extra spaces
        input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        expected = "Here:  is  one  line\nof  text  that  is \ngoing  to  be \nwrapped  after  20 \ncolumns.";
        String finalInput9 = input;
        String finalExpected9 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput9, 20, "\n", false)).isEqualTo(finalExpected9);});
        String finalInput10 = input;
        String finalExpected10 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput10, 20, "\n", true)).isEqualTo(finalExpected10);});

        // with tab
        input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        expected = "Here is\tone line of\ntext that is going\nto be wrapped after\n20 columns.";
        String finalInput11 = input;
        String finalExpected11 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput11, 20, "\n", false)).isEqualTo(finalExpected11);});
        String finalInput12 = input;
        String finalExpected12 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput12, 20, "\n", true)).isEqualTo(finalExpected12);});

        // with tab at wrapColumn
        input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        expected = "Here is one line\nof\ttext that is\ngoing to be wrapped\nafter 20 columns.";
        String finalInput13 = input;
        String finalExpected13 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput13, 20, "\n", false)).isEqualTo(finalExpected13);});
        String finalInput14 = input;
        String finalExpected14 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> { assertThat(WordUtils.wrap(finalInput14, 20, "\n", true)).isEqualTo(finalExpected14);});

        // difference because of long word
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org";
        String finalInput15 = input;
        String finalExpected15 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput15, 20, "\n", false)).isEqualTo(finalExpected15);});
        expected = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apac\nhe.org";
        String finalInput16 = input;
        String finalExpected16 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput16, 20, "\n", true)).isEqualTo(finalExpected16);});

        // difference because of long word in middle
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website";
        String finalInput17 = input;
        String finalExpected17 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput17, 20, "\n", false)).isEqualTo(finalExpected17);});
        expected = "Click here,\nhttps://commons.apac\nhe.org, to jump to\nthe commons website";
        String finalInput18 = input;
        String finalExpected18 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput18, 20, "\n", true)).isEqualTo(finalExpected18);});
    }

    @Test
    public void testWrap_StringIntStringBooleanString() {

        // no changes test
        String input = "flammable/inflammable";
        String expected = "flammable/inflammable";
        String finalInput = input;
        String finalExpected = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput, 30, "\n", false, "/")).isEqualTo(finalExpected);});

        // wrap on / and small width
        expected = "flammable\ninflammable";
        String finalInput1 = input;
        String finalExpected1 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput1, 2, "\n", false, "/")).isEqualTo(finalExpected1);});

        // wrap long words on / 1
        expected = "flammable\ninflammab\nle";
        String finalInput2 = input;
        String finalExpected2 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput2, 9, "\n", true, "/")).isEqualTo(finalExpected2);});

        // wrap long words on / 2
        expected = "flammable\ninflammable";
        String finalInput3 = input;
        String finalExpected3 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput3, 15, "\n", true, "/")).isEqualTo(finalExpected3);});

        // wrap long words on / 3
        input = "flammableinflammable";
        expected = "flammableinflam\nmable";
        String finalInput4 = input;
        String finalExpected4 = expected;
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap(finalInput4, 15, "\n", true, "/")).isEqualTo(finalExpected4);});
    }

    @Test
    public void testWrapAtMiddleTwice() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("abcdefggabcdef", 2, "\n", false, "(?=g)")).isEqualTo("abcdef\n\nabcdef");});
    }



    @Test
    public void testWrapAtStartAndEnd() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("nabcdefabcdefn", 2, "\n", false, "(?=n)")).isEqualTo("\nabcdefabcdef\n");});
    }

    @Test
    public void testWrapWithMultipleRegexMatchOfLength0() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("abcdefabcdef", 2, "\n", false, "(?=d)")).isEqualTo("abc\ndefabc\ndef");});
    }

    @Test
    public void testWrapWithRegexMatchOfLength0() {
        ProxyAssertions.customized.Customized.runAssertion(() -> {assertThat(WordUtils.wrap("abcdef", 2, "\n", false, "(?=d)")).isEqualTo("abc\ndef");});
    }

}
