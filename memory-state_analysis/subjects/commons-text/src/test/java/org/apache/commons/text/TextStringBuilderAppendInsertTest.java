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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link TextStringBuilder}.
 */
public class TextStringBuilderAppendInsertTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    /** The system line separator. */
    private static final String SEP = System.lineSeparator();

    /** Test subclass of Object, with a toString method. */
    private static final Object FOO = new Object() {
        @Override
        public String toString() {
            return "foo";
        }
    };

    @Test
    public void testAppend_Boolean() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.append(true);
        assertThat(sb.toString()).isEqualTo("true");

        sb.append(false);
        assertThat(sb.toString()).isEqualTo("truefalse");

        sb.append('!');
        assertThat(sb.toString()).isEqualTo("truefalse!");
    }

    @Test
    public void testAppend_CharArray() {
        TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("NULL").append((char[]) null);
        assertThat(sb.toString()).isEqualTo("NULL");

        sb = new TextStringBuilder();
        sb.append(ArrayUtils.EMPTY_CHAR_ARRAY);
        assertThat(sb.toString()).isEqualTo("");

        sb.append(new char[] {'f', 'o', 'o'});
        assertThat(sb.toString()).isEqualTo("foo");
    }

    @Test
    public void testAppend_CharArray_int_int() {
        final TextStringBuilder sb0 = new TextStringBuilder();
        sb0.setNullText("NULL").append((char[]) null, 0, 1);
        assertThat(sb0.toString()).isEqualTo("NULL");

        final TextStringBuilder sb = new TextStringBuilder();
        sb.append(new char[] {'f', 'o', 'o'}, 0, 3);
        assertThat(sb.toString()).isEqualTo("foo");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new char[] {'b', 'a', 'r'}, -1, 1),
            "append(char[], -1,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new char[] {'b', 'a', 'r'}, 3, 1),
            "append(char[], 3,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new char[] {'b', 'a', 'r'}, 1, -1),
            "append(char[],, -1) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new char[] {'b', 'a', 'r'}, 1, 3),
            "append(char[], 1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new char[] {'b', 'a', 'r'}, -1, 3),
            "append(char[], -1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new char[] {'b', 'a', 'r'}, 4, 0),
            "append(char[], 4, 0) expected IndexOutOfBoundsException");

        sb.append(new char[] {'b', 'a', 'r'}, 3, 0);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new char[] {'a', 'b', 'c', 'b', 'a', 'r', 'd', 'e', 'f'}, 3, 3);
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppend_FormattedString() {
        TextStringBuilder sb;

        sb = new TextStringBuilder();
        sb.append("Hi", (Object[]) null);
        assertThat(sb.toString()).isEqualTo("Hi");

        sb = new TextStringBuilder();
        sb.append("Hi", "Alice");
        assertThat(sb.toString()).isEqualTo("Hi");

        sb = new TextStringBuilder();
        sb.append("Hi %s", "Alice");
        assertThat(sb.toString()).isEqualTo("Hi Alice");

        sb = new TextStringBuilder();
        sb.append("Hi %s %,d", "Alice", 5000);
        // group separator depends on system locale
        final char groupingSeparator = DecimalFormatSymbols.getInstance().getGroupingSeparator();
        final String expected = "Hi Alice 5" + groupingSeparator + "000";
        assertThat(sb.toString()).isEqualTo(expected);
    }

    @Test
    public void testAppend_Object() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendNull();
        assertThat(sb.toString()).isEqualTo("");

        sb.append((Object) null);
        assertThat(sb.toString()).isEqualTo("");

        sb.append(FOO);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append((StringBuffer) null);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuffer("baz"));
        assertThat(sb.toString()).isEqualTo("foobaz");

        sb.append(new TextStringBuilder("yes"));
        assertThat(sb.toString()).isEqualTo("foobazyes");

        sb.append((CharSequence) "Seq");
        assertThat(sb.toString()).isEqualTo("foobazyesSeq");

        sb.append(new StringBuilder("bld")); // Check it supports StringBuilder
        assertThat(sb.toString()).isEqualTo("foobazyesSeqbld");
    }

    @Test
    public void testAppend_PrimitiveNumber() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.append(0);
        assertThat(sb.toString()).isEqualTo("0");

        sb.append(1L);
        assertThat(sb.toString()).isEqualTo("01");

        sb.append(2.3f);
        assertThat(sb.toString()).isEqualTo("012.3");

        sb.append(4.5d);
        assertThat(sb.toString()).isEqualTo("012.34.5");
    }

    @Test
    public void testAppend_String() {
        TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("NULL").append((String) null);
        assertThat(sb.toString()).isEqualTo("NULL");

        sb = new TextStringBuilder();
        sb.append("foo");
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append("");
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append("bar");
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppend_String_int_int() {
        final TextStringBuilder sb0 = new TextStringBuilder();
        sb0.setNullText("NULL").append((String) null, 0, 1);
        assertThat(sb0.toString()).isEqualTo("NULL");

        final TextStringBuilder sb = new TextStringBuilder();
        sb.append("foo", 0, 3);
        assertThat(sb.toString()).isEqualTo("foo");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append("bar", -1, 1), "append(char[], -1,) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append("bar", 3, 1), "append(char[], 3,) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append("bar", 1, -1), "append(char[],, -1) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append("bar", 1, 3), "append(char[], 1, 3) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append("bar", -1, 3), "append(char[], -1, 3) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append("bar", 4, 0), "append(char[], 4, 0) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append((CharSequence) "bar", 2, 1), "append(char[], 2, 1) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append((CharSequence) "bar", 2, 2), "append(char[], 2, 2) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append((CharSequence) "bar", 2, -2), "append(char[], 2, -2) expected IndexOutOfBoundsException");
        assertThrows(IndexOutOfBoundsException.class, () -> sb.append((CharSequence) "bar", 2, 0), "append(char[], 2, 0) expected IndexOutOfBoundsException");

        sb.append("bar", 3, 0);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append("abcbardef", 3, 3);
        assertThat(sb.toString()).isEqualTo("foobar");

        sb.append((CharSequence) "abcbardef", 4, 7);
        assertThat(sb.toString()).isEqualTo("foobarard");
    }

    @Test
    public void testAppend_StringBuffer() {
        TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("NULL").append((StringBuffer) null);
        assertThat(sb.toString()).isEqualTo("NULL");

        sb = new TextStringBuilder();
        sb.append(new StringBuffer("foo"));
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuffer(""));
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuffer("bar"));
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppend_StringBuffer_int_int() {
        final TextStringBuilder sb0 = new TextStringBuilder();
        sb0.setNullText("NULL").append((StringBuffer) null, 0, 1);
        assertThat(sb0.toString()).isEqualTo("NULL");

        final TextStringBuilder sb = new TextStringBuilder();
        sb.append(new StringBuffer("foo"), 0, 3);
        assertThat(sb.toString()).isEqualTo("foo");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuffer("bar"), -1, 1),
            "append(char[], -1,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuffer("bar"), 3, 1), "append(char[], 3,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuffer("bar"), 1, -1),
            "append(char[],, -1) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuffer("bar"), 1, 3),
            "append(char[], 1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuffer("bar"), -1, 3),
            "append(char[], -1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuffer("bar"), 4, 0),
            "append(char[], 4, 0) expected IndexOutOfBoundsException");

        sb.append(new StringBuffer("bar"), 3, 0);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuffer("abcbardef"), 3, 3);
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppend_StringBuilder() {
        TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("NULL").append((String) null);
        assertThat(sb.toString()).isEqualTo("NULL");

        sb = new TextStringBuilder();
        sb.append(new StringBuilder("foo"));
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuilder(""));
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuilder("bar"));
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppend_StringBuilder_int_int() {
        final TextStringBuilder sb0 = new TextStringBuilder();
        sb0.setNullText("NULL").append((String) null, 0, 1);
        assertThat(sb0.toString()).isEqualTo("NULL");

        final TextStringBuilder sb = new TextStringBuilder();
        sb.append(new StringBuilder("foo"), 0, 3);
        assertThat(sb.toString()).isEqualTo("foo");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuilder("bar"), -1, 1),
            "append(StringBuilder, -1,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuilder("bar"), 3, 1),
            "append(StringBuilder, 3,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuilder("bar"), 1, -1),
            "append(StringBuilder,, -1) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuilder("bar"), 1, 3),
            "append(StringBuilder, 1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuilder("bar"), -1, 3),
            "append(StringBuilder, -1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new StringBuilder("bar"), 4, 0),
            "append(StringBuilder, 4, 0) expected IndexOutOfBoundsException");

        sb.append(new StringBuilder("bar"), 3, 0);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new StringBuilder("abcbardef"), 3, 3);
        assertThat(sb.toString()).isEqualTo("foobar");

        sb.append(new StringBuilder("abcbardef"), 4, 3);
        assertThat(sb.toString()).isEqualTo("foobarard");
    }

    @Test
    public void testAppend_TextStringBuilder() {
        TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("NULL").append((TextStringBuilder) null);
        assertThat(sb.toString()).isEqualTo("NULL");

        sb = new TextStringBuilder();
        sb.append(new TextStringBuilder("foo"));
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new TextStringBuilder(""));
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new TextStringBuilder("bar"));
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppend_TextStringBuilder_int_int() {
        final TextStringBuilder sb0 = new TextStringBuilder();
        sb0.setNullText("NULL").append((TextStringBuilder) null, 0, 1);
        assertThat(sb0.toString()).isEqualTo("NULL");

        final TextStringBuilder sb = new TextStringBuilder();
        sb.append(new TextStringBuilder("foo"), 0, 3);
        assertThat(sb.toString()).isEqualTo("foo");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new TextStringBuilder("bar"), -1, 1),
            "append(char[], -1,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new TextStringBuilder("bar"), 3, 1),
            "append(char[], 3,) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new TextStringBuilder("bar"), 1, -1),
            "append(char[],, -1) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new TextStringBuilder("bar"), 1, 3),
            "append(char[], 1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new TextStringBuilder("bar"), -1, 3),
            "append(char[], -1, 3) expected IndexOutOfBoundsException");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.append(new TextStringBuilder("bar"), 4, 0),
            "append(char[], 4, 0) expected IndexOutOfBoundsException");

        sb.append(new TextStringBuilder("bar"), 3, 0);
        assertThat(sb.toString()).isEqualTo("foo");

        sb.append(new TextStringBuilder("abcbardef"), 3, 3);
        assertThat(sb.toString()).isEqualTo("foobar");
    }

    @Test
    public void testAppendAll_Array() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendAll((Object[]) null);
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendAll();
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendAll("foo", "bar", "baz");
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.appendAll("foo", "bar", "baz");
        assertThat(sb.toString()).isEqualTo("foobarbaz");
    }

    @Test
    public void testAppendAll_Collection() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendAll((Collection<?>) null);
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendAll(Collections.EMPTY_LIST);
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendAll(Arrays.asList("foo", "bar", "baz"));
        assertThat(sb.toString()).isEqualTo("foobarbaz");
    }

    @Test
    public void testAppendAll_Iterator() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendAll((Iterator<?>) null);
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendAll(Collections.EMPTY_LIST.iterator());
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendAll(Arrays.asList("foo", "bar", "baz").iterator());
        assertThat(sb.toString()).isEqualTo("foobarbaz");
    }

    @Test
    public void testAppendFixedWidthPadLeft() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendFixedWidthPadLeft("foo", -1, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadLeft("foo", 0, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadLeft("foo", 1, '-');
        assertThat(sb.toString()).isEqualTo("o");

        sb.clear();
        sb.appendFixedWidthPadLeft("foo", 2, '-');
        assertThat(sb.toString()).isEqualTo("oo");

        sb.clear();
        sb.appendFixedWidthPadLeft("foo", 3, '-');
        assertThat(sb.toString()).isEqualTo("foo");

        sb.clear();
        sb.appendFixedWidthPadLeft("foo", 4, '-');
        assertThat(sb.toString()).isEqualTo("-foo");

        sb.clear();
        sb.appendFixedWidthPadLeft("foo", 10, '-');
        assertThat(sb.length()).isEqualTo(10);
        // 1234567890
        assertThat(sb.toString()).isEqualTo("-------foo");

        sb.clear();
        sb.setNullText("null");
        sb.appendFixedWidthPadLeft(null, 5, '-');
        assertThat(sb.toString()).isEqualTo("-null");
    }

    @Test
    public void testAppendFixedWidthPadLeft_int() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendFixedWidthPadLeft(123, -1, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadLeft(123, 0, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadLeft(123, 1, '-');
        assertThat(sb.toString()).isEqualTo("3");

        sb.clear();
        sb.appendFixedWidthPadLeft(123, 2, '-');
        assertThat(sb.toString()).isEqualTo("23");

        sb.clear();
        sb.appendFixedWidthPadLeft(123, 3, '-');
        assertThat(sb.toString()).isEqualTo("123");

        sb.clear();
        sb.appendFixedWidthPadLeft(123, 4, '-');
        assertThat(sb.toString()).isEqualTo("-123");

        sb.clear();
        sb.appendFixedWidthPadLeft(123, 10, '-');
        assertThat(sb.length()).isEqualTo(10);
        // 1234567890
        assertThat(sb.toString()).isEqualTo("-------123");
    }

    @Test
    public void testAppendFixedWidthPadRight() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendFixedWidthPadRight("foo", -1, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadRight("foo", 0, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadRight("foo", 1, '-');
        assertThat(sb.toString()).isEqualTo("f");

        sb.clear();
        sb.appendFixedWidthPadRight("foo", 2, '-');
        assertThat(sb.toString()).isEqualTo("fo");

        sb.clear();
        sb.appendFixedWidthPadRight("foo", 3, '-');
        assertThat(sb.toString()).isEqualTo("foo");

        sb.clear();
        sb.appendFixedWidthPadRight("foo", 4, '-');
        assertThat(sb.toString()).isEqualTo("foo-");

        sb.clear();
        sb.appendFixedWidthPadRight("foo", 10, '-');
        assertThat(sb.length()).isEqualTo(10);
        // 1234567890
        assertThat(sb.toString()).isEqualTo("foo-------");

        sb.clear();
        sb.setNullText("null");
        sb.appendFixedWidthPadRight(null, 5, '-');
        assertThat(sb.toString()).isEqualTo("null-");
    }

    @Test
    public void testAppendFixedWidthPadRight_int() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendFixedWidthPadRight(123, -1, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadRight(123, 0, '-');
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendFixedWidthPadRight(123, 1, '-');
        assertThat(sb.toString()).isEqualTo("1");

        sb.clear();
        sb.appendFixedWidthPadRight(123, 2, '-');
        assertThat(sb.toString()).isEqualTo("12");

        sb.clear();
        sb.appendFixedWidthPadRight(123, 3, '-');
        assertThat(sb.toString()).isEqualTo("123");

        sb.clear();
        sb.appendFixedWidthPadRight(123, 4, '-');
        assertThat(sb.toString()).isEqualTo("123-");

        sb.clear();
        sb.appendFixedWidthPadRight(123, 10, '-');
        assertThat(sb.length()).isEqualTo(10);
        // 1234567890
        assertThat(sb.toString()).isEqualTo("123-------");
    }

    @Test
    public void testAppendln_Boolean() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendln(true);
        assertThat(sb.toString()).isEqualTo("true" + SEP);

        sb.clear();
        sb.appendln(false);
        assertThat(sb.toString()).isEqualTo("false" + SEP);
    }

    @Test
    public void testAppendln_CharArray() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        final char[] input = "foo".toCharArray();
        sb.appendln(input);

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(input);
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_CharArray_int_int() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        final char[] input = "foo".toCharArray();
        sb.appendln(input, 0, 3);

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(input, 0, 3);
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_FormattedString() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln("Hello %s", "Alice");

        assertThat(sb.toString()).isEqualTo("Hello Alice" + SEP);

        verify(sb, times(2)).append(anyString()); // appendNewLine() calls append(String)
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_Object() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendln((Object) null);
        assertThat(sb.toString()).isEqualTo("" + SEP);

        sb.appendln(FOO);
        assertThat(sb.toString()).isEqualTo(SEP + "foo" + SEP);

        sb.appendln(Integer.valueOf(6));
        assertThat(sb.toString()).isEqualTo(SEP + "foo" + SEP + "6" + SEP);
    }

    @Test
    public void testAppendln_PrimitiveNumber() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendln(0);
        assertThat(sb.toString()).isEqualTo("0" + SEP);

        sb.clear();
        sb.appendln(1L);
        assertThat(sb.toString()).isEqualTo("1" + SEP);

        sb.clear();
        sb.appendln(2.3f);
        assertThat(sb.toString()).isEqualTo("2.3" + SEP);

        sb.clear();
        sb.appendln(4.5d);
        assertThat(sb.toString()).isEqualTo("4.5" + SEP);
    }

    @Test
    public void testAppendln_String() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln("foo");

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(2)).append(anyString()); // appendNewLine() calls append(String)
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_String_int_int() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln("foo", 0, 3);

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(2)).append(anyString(), anyInt(), anyInt()); // appendNewLine() calls append(String)
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_StringBuffer() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln(new StringBuffer("foo"));

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(any(StringBuffer.class));
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_StringBuffer_int_int() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln(new StringBuffer("foo"), 0, 3);

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(any(StringBuffer.class), anyInt(), anyInt());
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_StringBuilder() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln(new StringBuilder("foo"));

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(any(StringBuilder.class));
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_StringBuilder_int_int() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln(new StringBuilder("foo"), 0, 3);

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(any(StringBuilder.class), anyInt(), anyInt());
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_TextStringBuilder() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln(new TextStringBuilder("foo"));

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(any(TextStringBuilder.class));
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendln_TextStringBuilder_int_int() {
        final TextStringBuilder sb = spy(new TextStringBuilder());
        sb.appendln(new TextStringBuilder("foo"), 0, 3);

        assertThat(sb.toString()).isEqualTo("foo" + SEP);

        verify(sb, times(1)).append(any(TextStringBuilder.class), anyInt(), anyInt());
        verify(sb, times(1)).appendNewLine();
    }

    @Test
    public void testAppendNewLine() {
        TextStringBuilder sb = new TextStringBuilder("---");
        sb.appendNewLine().append("+++");
        assertThat(sb.toString()).isEqualTo("---" + SEP + "+++");

        sb = new TextStringBuilder("---");
        sb.setNewLineText("#").appendNewLine().setNewLineText(null).appendNewLine();
        assertThat(sb.toString()).isEqualTo("---#" + SEP);
    }

    @Test
    public void testAppendPadding() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.append("foo");
        assertThat(sb.toString()).isEqualTo("foo");

        sb.appendPadding(-1, '-');
        assertThat(sb.toString()).isEqualTo("foo");

        sb.appendPadding(0, '-');
        assertThat(sb.toString()).isEqualTo("foo");

        sb.appendPadding(1, '-');
        assertThat(sb.toString()).isEqualTo("foo-");

        sb.appendPadding(16, '-');
        assertThat(sb.length()).isEqualTo(20);
        // 12345678901234567890
        assertThat(sb.toString()).isEqualTo("foo-----------------");
    }

    @Test
    public void testAppendSeparator_char() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendSeparator(','); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.append("foo");
        assertThat(sb.toString()).isEqualTo("foo");
        sb.appendSeparator(',');
        assertThat(sb.toString()).isEqualTo("foo,");
    }

    @Test
    public void testAppendSeparator_char_char() {
        final TextStringBuilder sb = new TextStringBuilder();
        final char startSeparator = ':';
        final char standardSeparator = ',';
        final String foo = "foo";
        sb.appendSeparator(standardSeparator, startSeparator); // no effect
        assertThat(sb.toString()).isEqualTo(String.valueOf(startSeparator));
        sb.append(foo);
        assertThat(sb.toString()).isEqualTo(String.valueOf(startSeparator) + foo);
        sb.appendSeparator(standardSeparator, startSeparator);
        assertThat(sb.toString()).isEqualTo(String.valueOf(startSeparator) + foo + standardSeparator);
    }

    @Test
    public void testAppendSeparator_char_int() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendSeparator(',', 0); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.append("foo");
        assertThat(sb.toString()).isEqualTo("foo");
        sb.appendSeparator(',', 1);
        assertThat(sb.toString()).isEqualTo("foo,");

        sb.appendSeparator(',', -1); // no effect
        assertThat(sb.toString()).isEqualTo("foo,");
    }

    @Test
    public void testAppendSeparator_String() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendSeparator(","); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.append("foo");
        assertThat(sb.toString()).isEqualTo("foo");
        sb.appendSeparator(",");
        assertThat(sb.toString()).isEqualTo("foo,");
    }

    @Test
    public void testAppendSeparator_String_int() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendSeparator(null, -1); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.appendSeparator(null, 0); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.appendSeparator(null, 1); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.appendSeparator(",", -1); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.appendSeparator(",", 0); // no effect
        assertThat(sb.toString()).isEqualTo("");
        sb.append("foo");
        assertThat(sb.toString()).isEqualTo("foo");
        sb.appendSeparator(",", 1);
        assertThat(sb.toString()).isEqualTo("foo,");

        sb.appendSeparator(",", -1); // no effect
        assertThat(sb.toString()).isEqualTo("foo,");
    }

    @Test
    public void testAppendSeparator_String_String() {
        final TextStringBuilder sb = new TextStringBuilder();
        final String startSeparator = "order by ";
        final String standardSeparator = ",";
        final String foo = "foo";
        sb.appendSeparator(null, null);
        assertThat(sb.toString()).isEqualTo("");
        sb.appendSeparator(standardSeparator, null);
        assertThat(sb.toString()).isEqualTo("");
        sb.appendSeparator(standardSeparator, startSeparator);
        assertThat(sb.toString()).isEqualTo(startSeparator);
        sb.appendSeparator(null, null);
        assertThat(sb.toString()).isEqualTo(startSeparator);
        sb.appendSeparator(null, startSeparator);
        assertThat(sb.toString()).isEqualTo(startSeparator);
        sb.append(foo);
        assertThat(sb.toString()).isEqualTo(startSeparator + foo);
        sb.appendSeparator(standardSeparator, startSeparator);
        assertThat(sb.toString()).isEqualTo(startSeparator + foo + standardSeparator);
    }

    @Test
    public void testAppendWithNullText() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("NULL");
        assertThat(sb.toString()).isEqualTo("");

        sb.appendNull();
        assertThat(sb.toString()).isEqualTo("NULL");

        sb.append((Object) null);
        assertThat(sb.toString()).isEqualTo("NULLNULL");

        sb.append(FOO);
        assertThat(sb.toString()).isEqualTo("NULLNULLfoo");

        sb.append((String) null);
        assertThat(sb.toString()).isEqualTo("NULLNULLfooNULL");

        sb.append("");
        assertThat(sb.toString()).isEqualTo("NULLNULLfooNULL");

        sb.append("bar");
        assertThat(sb.toString()).isEqualTo("NULLNULLfooNULLbar");

        sb.append((StringBuffer) null);
        assertThat(sb.toString()).isEqualTo("NULLNULLfooNULLbarNULL");

        sb.append(new StringBuffer("baz"));
        assertThat(sb.toString()).isEqualTo("NULLNULLfooNULLbarNULLbaz");
    }

    @Test
    public void testAppendWithSeparators_Array() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendWithSeparators((Object[]) null, ",");
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendWithSeparators(ArrayUtils.EMPTY_OBJECT_ARRAY, ",");
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendWithSeparators(new Object[] {"foo", "bar", "baz"}, ",");
        assertThat(sb.toString()).isEqualTo("foo,bar,baz");

        sb.clear();
        sb.appendWithSeparators(new Object[] {"foo", "bar", "baz"}, null);
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.appendWithSeparators(new Object[] {"foo", null, "baz"}, ",");
        assertThat(sb.toString()).isEqualTo("foo,,baz");
    }

    @Test
    public void testAppendWithSeparators_Collection() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendWithSeparators((Collection<?>) null, ",");
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendWithSeparators(Collections.EMPTY_LIST, ",");
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", "bar", "baz"), ",");
        assertThat(sb.toString()).isEqualTo("foo,bar,baz");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", "bar", "baz"), null);
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", null, "baz"), ",");
        assertThat(sb.toString()).isEqualTo("foo,,baz");
    }

    @Test
    public void testAppendWithSeparators_Iterator() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.appendWithSeparators((Iterator<?>) null, ",");
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendWithSeparators(Collections.EMPTY_LIST.iterator(), ",");
        assertThat(sb.toString()).isEqualTo("");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", "bar", "baz").iterator(), ",");
        assertThat(sb.toString()).isEqualTo("foo,bar,baz");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", "bar", "baz").iterator(), null);
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", null, "baz").iterator(), ",");
        assertThat(sb.toString()).isEqualTo("foo,,baz");
    }

    @Test
    public void testAppendWithSeparatorsWithNullText() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("null");
        sb.appendWithSeparators(new Object[] {"foo", null, "baz"}, ",");
        assertThat(sb.toString()).isEqualTo("foo,null,baz");

        sb.clear();
        sb.appendWithSeparators(Arrays.asList("foo", null, "baz"), ",");
        assertThat(sb.toString()).isEqualTo("foo,null,baz");
    }

    @Test
    public void testInsert() {

        final TextStringBuilder sb = new TextStringBuilder();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, FOO));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, FOO));

        sb.insert(0, (Object) null);
        assertThat(sb.toString()).isEqualTo("barbaz");

        sb.insert(0, FOO);
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, "foo"));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, "foo"));

        sb.insert(0, (String) null);
        assertThat(sb.toString()).isEqualTo("barbaz");

        sb.insert(0, "foo");
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, new char[] {'f', 'o', 'o'}));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, new char[] {'f', 'o', 'o'}));

        sb.insert(0, (char[]) null);
        assertThat(sb.toString()).isEqualTo("barbaz");

        sb.insert(0, ArrayUtils.EMPTY_CHAR_ARRAY);
        assertThat(sb.toString()).isEqualTo("barbaz");

        sb.insert(0, new char[] {'f', 'o', 'o'});
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class,
            () -> sb.insert(-1, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 3, 3));

        assertThrows(IndexOutOfBoundsException.class,
            () -> sb.insert(7, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 3, 3));

        sb.insert(0, (char[]) null, 0, 0);
        assertThat(sb.toString()).isEqualTo("barbaz");

        sb.insert(0, ArrayUtils.EMPTY_CHAR_ARRAY, 0, 0);
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class,
            () -> sb.insert(0, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, -1, 3));

        assertThrows(IndexOutOfBoundsException.class,
            () -> sb.insert(0, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 10, 3));

        assertThrows(IndexOutOfBoundsException.class,
            () -> sb.insert(0, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 0, -1));

        assertThrows(IndexOutOfBoundsException.class,
            () -> sb.insert(0, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 0, 10));

        sb.insert(0, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 0, 0);
        assertThat(sb.toString()).isEqualTo("barbaz");

        sb.insert(0, new char[] {'a', 'b', 'c', 'f', 'o', 'o', 'd', 'e', 'f'}, 3, 3);
        assertThat(sb.toString()).isEqualTo("foobarbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, true));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, true));

        sb.insert(0, true);
        assertThat(sb.toString()).isEqualTo("truebarbaz");

        sb.insert(0, false);
        assertThat(sb.toString()).isEqualTo("falsetruebarbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, '!'));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, '!'));

        sb.insert(0, '!');
        assertThat(sb.toString()).isEqualTo("!barbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, 0));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, 0));

        sb.insert(0, '0');
        assertThat(sb.toString()).isEqualTo("0barbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, 1L));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, 1L));

        sb.insert(0, 1L);
        assertThat(sb.toString()).isEqualTo("1barbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, 2.3F));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, 2.3F));

        sb.insert(0, 2.3F);
        assertThat(sb.toString()).isEqualTo("2.3barbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, 4.5D));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, 4.5D));

        sb.insert(0, 4.5D);
        assertThat(sb.toString()).isEqualTo("4.5barbaz");
    }

    @Test
    public void testInsertWithNullText() {
        final TextStringBuilder sb = new TextStringBuilder();
        sb.setNullText("null");
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, FOO));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, FOO));

        sb.insert(0, (Object) null);
        assertThat(sb.toString()).isEqualTo("nullbarbaz");

        sb.insert(0, FOO);
        assertThat(sb.toString()).isEqualTo("foonullbarbaz");

        sb.clear();
        sb.append("barbaz");
        assertThat(sb.toString()).isEqualTo("barbaz");

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(-1, "foo"));

        assertThrows(IndexOutOfBoundsException.class, () -> sb.insert(7, "foo"));

        sb.insert(0, (String) null);
        assertThat(sb.toString()).isEqualTo("nullbarbaz");

        sb.insert(0, "foo");
        assertThat(sb.toString()).isEqualTo("foonullbarbaz");

        sb.insert(0, (char[]) null);
        assertThat(sb.toString()).isEqualTo("nullfoonullbarbaz");

        sb.insert(0, (char[]) null, 0, 0);
        assertThat(sb.toString()).isEqualTo("nullnullfoonullbarbaz");
    }

    /** See: https://issues.apache.org/jira/browse/LANG-299 */
    @Test
    public void testLang299() {
        final TextStringBuilder sb = new TextStringBuilder(1);
        sb.appendFixedWidthPadRight("foo", 1, '-');
        assertThat(sb.toString()).isEqualTo("f");
    }
}
