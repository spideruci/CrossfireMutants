/*
 *  Copyright 2009-present, Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.joda.money;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

/**
 * Test CurrencyUnit.
 */
public class TestCurrencyUnit {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    private static final Currency JDK_GBP = Currency.getInstance("GBP");

    //-----------------------------------------------------------------------
    // registeredCurrencies()
    //-----------------------------------------------------------------------
    @Test
    public void test_registeredCurrencies() {
        List<CurrencyUnit> curList = CurrencyUnit.registeredCurrencies();
        boolean found = false;
        for (CurrencyUnit currencyUnit : curList) {
            if (currencyUnit.getCode().equals("GBP")) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void test_registeredCurrencies_sorted() {
        List<CurrencyUnit> curList1 = CurrencyUnit.registeredCurrencies();
        List<CurrencyUnit> curList2 = CurrencyUnit.registeredCurrencies();
        Collections.sort(curList2);
        assertEquals(curList2, curList1);
        Collections.shuffle(curList2);
        Collections.sort(curList2);
        assertEquals(curList2, curList1);
    }

    @Test
    public void test_registeredCurrency_nullCode() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.registerCurrency(null, 991, 2, Arrays.asList("TS"));
        });

    }

    @Test
    public void test_registeredCurrency_invalidStringCode_empty() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("", 991, 2, Arrays.asList("TS"));

        });
    }

    @Test
    public void test_registeredCurrency_invalidStringCode_1letter() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("A", 991, 2, Arrays.asList("TS"));
        });
    }

    @Test
    public void test_registeredCurrency_invalidStringCode_2letters() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("AB", 991, 2, Arrays.asList("TS"));

        });
    }

    @Test
    public void test_registeredCurrency_invalidStringCode_4letters() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("ABCD", 991, 2, Arrays.asList("TS"));

        });
    }

    @Test
    public void test_registeredCurrency_invalidStringCode_lowerCase() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("xxA", 991, 2, Arrays.asList("xx"));

        });
    }

    @Test
    public void test_registeredCurrency_invalidStringCode_number() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("123", 991, 2, Arrays.asList("TS"));

        });
    }

    @Test
    public void test_registeredCurrency_invalidStringCode_dash() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("A-", 991, 2, Arrays.asList("TS"));

        });
   }

    @Test
    public void test_registeredCurrency_invalidNumericCode_small() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("TST", -2, 2, Arrays.asList("TS"));

        });
   }

    @Test
    public void test_registeredCurrency_invalidNumericCode_big() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("TST", 1000, 2, Arrays.asList("TS"));

        });
   }

    @Test
    public void test_registeredCurrency_invalidDP_small() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("TST", 991, -2, Arrays.asList("TS"));

        });
    }

    @Test
    public void test_registeredCurrency_invalidDP_big() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("TST", 991, 31, Arrays.asList("TS"));
        });
    }

    @Test
    public void test_registeredCurrency_validDP_big() {
        CurrencyUnit.registerCurrency("XLG", -1, 30, new ArrayList<String>());

        CurrencyUnit currency = CurrencyUnit.of("XLG");
        assertEquals(30, currency.getDecimalPlaces());
    }

    @Test
    public void test_registeredCurrency_nullCountry() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.registerCurrency("TST", 991, 2, Arrays.asList((String) null));

        });
    }

    @Test
    public void test_registeredCurrency_alreadyRegisteredCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("GBP", 991, 2, Arrays.asList("GB"));

        });
    }

    @Test
    public void test_registeredCurrency_alreadyRegisteredNumericCode() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("TST", 826, 2, Arrays.asList("TS"));

        });
    }

    @Test
    public void test_registeredCurrency_alreadyRegisteredCountry() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyUnit.registerCurrency("GBX", 991, 2, Arrays.asList("GB"));

        });
    }

    @Test
    public void test_registeredCurrencies_crossCheck() {
        List<CurrencyUnit> curList = CurrencyUnit.registeredCurrencies();
        for (CurrencyUnit currencyUnit : curList) {
            try {
                Currency curr = Currency.getInstance(currencyUnit.getCode());
                int dp = curr.getDefaultFractionDigits() < 0 ? 0 : curr.getDefaultFractionDigits();
                assertEquals(curr.getCurrencyCode(), dp, currencyUnit.getDecimalPlaces());
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    //-----------------------------------------------------------------------
    // registeredCountries()
    //-----------------------------------------------------------------------
    @Test
    public void test_registeredCountries() {
        List<String> countryList = CurrencyUnit.registeredCountries();
        assertTrue(countryList.contains("GB"));
        assertTrue(countryList.contains("EU"));
        assertTrue(countryList.contains("US"));
    }

    @Test
    public void test_registeredCountries_sorted() {
        List<String> curList1 = CurrencyUnit.registeredCountries();
        List<String> curList2 = CurrencyUnit.registeredCountries();
        Collections.sort(curList2);
        assertEquals(curList2, curList1);
        Collections.shuffle(curList2);
        Collections.sort(curList2);
        assertEquals(curList2, curList1);
    }

    //-----------------------------------------------------------------------
    // constants
    //-----------------------------------------------------------------------
    @Test
    public void test_constants() {
        assertEquals(CurrencyUnit.of("USD"), CurrencyUnit.USD);
        assertEquals(CurrencyUnit.of("EUR"), CurrencyUnit.EUR);
        assertEquals(CurrencyUnit.of("JPY"), CurrencyUnit.JPY);
        assertEquals(CurrencyUnit.of("GBP"), CurrencyUnit.GBP);
        assertEquals(CurrencyUnit.of("CHF"), CurrencyUnit.CHF);
        assertEquals(CurrencyUnit.of("AUD"), CurrencyUnit.AUD);
        assertEquals(CurrencyUnit.of("CAD"), CurrencyUnit.CAD);
    }

    //-----------------------------------------------------------------------
    // constructor assert
    //-----------------------------------------------------------------------
    @Test
    public void test_constructor_nullCode() {
        assertThrows(AssertionError.class, () -> {
            new CurrencyUnit(null, (short) 1, (short) 2);
        });

    }

    //-----------------------------------------------------------------------
    // of(Currency)
    //-----------------------------------------------------------------------
    @Test
    public void test_factory_of_Currency() {
        CurrencyUnit test = CurrencyUnit.of(JDK_GBP);
        assertEquals("GBP", test.getCode());
    }

    @Test
    public void test_factory_of_Currency_nullCurrency() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.of((Currency) null);
        });

    }

    //-----------------------------------------------------------------------
    // of(String)
    //-----------------------------------------------------------------------
    @Test
    public void test_factory_of_String() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertEquals("GBP", test.getCode());
    }

    @Test
    public void test_factory_of_String_nullString() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.of((String) null);
        });

    }

    @Test
    public void test_factory_of_String_unknownCurrency() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.of("ABC");
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency 'ABC'", ex.getMessage());
                throw ex;
            }
        });

    }

    @Test
    public void test_factory_of_String_empty() {
        assertThrows(IllegalCurrencyException.class, () -> {
            CurrencyUnit.of("");
        });

    }

    @Test
    public void test_factory_of_String_tooShort_unknown() {
        assertThrows(IllegalCurrencyException.class, () -> {
            CurrencyUnit.of("AB");
        });

    }

    @Test
    public void test_factory_of_String_tooLong_unknown() {
        assertThrows(IllegalCurrencyException.class, () -> {
            CurrencyUnit.of("ABCD");
        });

    }

    //-----------------------------------------------------------------------
    // ofNumericCode(String)
    //-----------------------------------------------------------------------
    @Test
    public void test_factory_ofNumericCode_String() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode("826");
        assertEquals("GBP", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_String_2char() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode("051");
        assertEquals("AMD", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_String_2charNoPad() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode("51");
        assertEquals("AMD", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_String_1char() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode("008");
        assertEquals("ALL", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_String_1charNoPad() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode("8");
        assertEquals("ALL", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_String_nullString() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.ofNumericCode((String) null);
        });

    }

    @Test
    public void test_factory_ofNumericCode_String_unknownCurrency() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofNumericCode("111");
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency '111'", ex.getMessage());
                throw ex;
            }
        });

    }

    @Test
    public void test_factory_ofNumericCode_String_negative() {
        assertThrows(IllegalCurrencyException.class, () -> {
            CurrencyUnit.ofNumericCode("-1");
        });

    }

    @Test
    public void test_factory_ofNumericCode_String_empty() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofNumericCode("");
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency ''", ex.getMessage());
                throw ex;
            }
        });

    }

    @Test
    public void test_factory_ofNumericCode_String_tooLong() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofNumericCode("1234");
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency '1234'", ex.getMessage());
                throw ex;
            }
        });

    }

    //-----------------------------------------------------------------------
    // ofNumericCode(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_factory_ofNumericCode_int() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode(826);
        assertEquals("GBP", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_int_2char() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode(51);
        assertEquals("AMD", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_int_1char() {
        CurrencyUnit test = CurrencyUnit.ofNumericCode(8);
        assertEquals("ALL", test.getCode());
    }

    @Test
    public void test_factory_ofNumericCode_int_unknownCurrency() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofNumericCode(111);
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency '111'", ex.getMessage());
                throw ex;
            }
        });

    }

    @Test
    public void test_factory_ofNumericCode_int_negative() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofNumericCode(-1);
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency '-1'", ex.getMessage());
                throw ex;
            }
        });

    }

    @Test
    public void test_factory_ofNumericCode_int_tooLong() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofNumericCode(1234);
            } catch (IllegalCurrencyException ex) {
                assertEquals("Unknown currency '1234'", ex.getMessage());
                throw ex;
            }
        });
    }

    //-----------------------------------------------------------------------
    // of(Locale)
    //-----------------------------------------------------------------------
    @Test
    public void test_factory_of_LocaleUK() {
        CurrencyUnit test = CurrencyUnit.of(Locale.UK);
        assertEquals("GBP", test.getCode());
    }

    @Test
    public void test_factory_of_LocaleUS() {
        CurrencyUnit test = CurrencyUnit.of(Locale.US);
        assertEquals("USD", test.getCode());
    }

    @Test
    public void test_factory_of_Locale_nullLocale() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.of((Locale) null);
        });

    }

    @Test
    public void test_factory_of_Locale_unknownCurrency() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.of(new Locale("en", "XY"));
            } catch (IllegalCurrencyException ex) {
                assertEquals("No currency found for locale 'en_XY'", ex.getMessage());
                throw ex;
            }
        });

    }

    //-----------------------------------------------------------------------
    // ofCountry(String)
    //-----------------------------------------------------------------------
    @Test
    public void test_factory_ofCountry_String() {
        CurrencyUnit test = CurrencyUnit.ofCountry("GB");
        assertEquals("GBP", test.getCode());
    }

    @Test
    public void test_factory_ofCountry_String_nullString() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.ofCountry((String) null);
        });
    }

    @Test
    public void test_factory_ofCountry_String_unknownCurrency() {
        assertThrows(IllegalCurrencyException.class, () -> {
            try {
                CurrencyUnit.ofCountry("gb");
            } catch (IllegalCurrencyException ex) {
                assertEquals("No currency found for country 'gb'", ex.getMessage());
                throw ex;
            }
        });

    }

    //-----------------------------------------------------------------------
    // Serialisation
    //-----------------------------------------------------------------------
//    @Test
//    public void test_serialization() throws Exception {
//        CurrencyUnit cu = CurrencyUnit.of("GBP");
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
//            oos.writeObject(cu);
//            oos.close();
//            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
//            CurrencyUnit input = (CurrencyUnit) ois.readObject();
//            assertEquals(cu, input);
//        }
//    }

//    @Test(expected = InvalidObjectException.class)
//    public void test_serialization_invalidNumericCode() throws Exception {
//        CurrencyUnit cu = new CurrencyUnit("GBP", (short) 234, (short) 2);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
//            oos.writeObject(cu);
//            oos.close();
//            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
//            try {
//                ois.readObject();
//            } catch (InvalidObjectException ex) {
//                // expected
//                assertTrue(ex.getMessage().contains("numeric code"));
//                assertTrue(ex.getMessage().contains("currency GBP"));
//                throw ex;
//            }
//        }
//    }

//    @Test(expected = InvalidObjectException.class)
//    public void test_serialization_invalidDecimalPlaces() throws Exception {
//        CurrencyUnit cu = new CurrencyUnit("GBP", (short) 826, (short) 1);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
//            oos.writeObject(cu);
//            oos.close();
//            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
//            try {
//                ois.readObject();
//            } catch (InvalidObjectException ex) {
//                // expected
//                assertTrue(ex.getMessage().contains("decimal places"));
//                assertTrue(ex.getMessage().contains("currency GBP"));
//                throw ex;
//            }
//        }
//    }

    //-----------------------------------------------------------------------
    // getNumeric3Code()
    //-----------------------------------------------------------------------
    @Test
    public void test_getNumeric3Code_GBP() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertEquals("826", test.getNumeric3Code());
    }

    @Test
    public void test_getNumeric3Code_ALL() {
        CurrencyUnit test = CurrencyUnit.of("ALL");
        assertEquals("008", test.getNumeric3Code());
    }

    @Test
    public void test_getNumeric3Code_AMD() {
        CurrencyUnit test = CurrencyUnit.of("AMD");
        assertEquals("051", test.getNumeric3Code());
    }

    @Test
    public void test_getNumeric3Code_XFU() {
        CurrencyUnit test = CurrencyUnit.of("XFU");
        assertEquals("", test.getNumeric3Code());
    }

    //-----------------------------------------------------------------------
    // getNumericCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_getNumericCode_GBP() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertEquals(826, test.getNumericCode());
    }

    //-----------------------------------------------------------------------
    // getCurrencyCodes()
    //-----------------------------------------------------------------------
    @Test
    public void test_getCurrencyCodes_GBP() {
        Set<String> test = CurrencyUnit.of("GBP").getCountryCodes();
        assertTrue(test.contains("GB"));
        assertTrue(test.contains("IM"));
        assertTrue(test.contains("JE"));
        assertTrue(test.contains("GG"));
    }

    //-----------------------------------------------------------------------
    // getDecimalPlaces()
    //-----------------------------------------------------------------------
    @Test
    public void test_getDecimalPlaces_GBP() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertEquals(2, test.getDecimalPlaces());
    }

    @Test
    public void test_getDecimalPlaces_JPY() {
        CurrencyUnit test = CurrencyUnit.of("JPY");
        assertEquals(0, test.getDecimalPlaces());
    }

    @Test
    public void test_getDecimalPlaces_XXX() {
        CurrencyUnit test = CurrencyUnit.of("XXX");
        assertEquals(0, test.getDecimalPlaces());
    }

    //-----------------------------------------------------------------------
    // isPseudoCurrency()
    //-----------------------------------------------------------------------
    @Test
    public void test_isPseudoCurrency_GBP() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertFalse(test.isPseudoCurrency());
    }

    @Test
    public void test_isPseudoCurrency_JPY() {
        CurrencyUnit test = CurrencyUnit.of("JPY");
        assertFalse(test.isPseudoCurrency());
    }

    @Test
    public void test_isPseudoCurrency_XXX() {
        CurrencyUnit test = CurrencyUnit.of("XXX");
        assertTrue(test.isPseudoCurrency());
    }

    //-----------------------------------------------------------------------
    // getSymbol()
    //-----------------------------------------------------------------------
    @Test
    public void test_getSymbol_GBP() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("GBP");
            assertEquals("\u00A3", test.getSymbol());
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_JPY() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("JPY");
            assertTrue(test.getSymbol().contains("JP"));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_TMT() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("TMT");
            assertEquals("TMT", test.getSymbol());
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_XXX() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("XXX");
            assertEquals("XXX", test.getSymbol());
        } finally {
            Locale.setDefault(loc);
        }
    }

    //-----------------------------------------------------------------------
    // getSymbol()
    //-----------------------------------------------------------------------
    @Test
    public void test_getSymbol_Locale_GBP_UK() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("GBP");
            assertEquals("\u00A3", test.getSymbol(Locale.UK));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_Locale_GBP_France() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("GBP");
            assertTrue(test.getSymbol(Locale.FRANCE).contains("GB"));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_Locale_USD_UK() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("USD");
            assertEquals("$", test.getSymbol(Locale.US));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_Locale_USD_France() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("USD");
            assertTrue(test.getSymbol(Locale.FRANCE).contains("US"));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_Locale_JPY_Japan() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("JPY");
            assertEquals("\uFFE5", test.getSymbol(Locale.JAPAN));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_TMT_UK() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("TMT");
            assertEquals("TMT", test.getSymbol(Locale.UK));
        } finally {
            Locale.setDefault(loc);
        }
    }

    @Test
    public void test_getSymbol_Locale_XXX() {
        Locale loc = Locale.getDefault();
        try {
            Locale.setDefault(Locale.UK);
            CurrencyUnit test = CurrencyUnit.of("XXX");
            assertEquals("XXX", test.getSymbol(Locale.FRANCE));
        } finally {
            Locale.setDefault(loc);
        }
    }

    //-----------------------------------------------------------------------
    // toCurrency()
    //-----------------------------------------------------------------------
    @Test
    public void test_toCurrency() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertEquals(JDK_GBP, test.toCurrency());
    }

    //-----------------------------------------------------------------------
    // compareTo()
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        CurrencyUnit a = CurrencyUnit.of("EUR");
        CurrencyUnit b = CurrencyUnit.of("GBP");
        CurrencyUnit c = CurrencyUnit.of("JPY");
        assertEquals(0, a.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(0, c.compareTo(c));

        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);

        assertTrue(a.compareTo(c) < 0);
        assertTrue(c.compareTo(a) > 0);

        assertTrue(b.compareTo(c) < 0);
        assertTrue(c.compareTo(b) > 0);
    }

    @Test
    public void test_compareTo_null() {
        assertThrows(NullPointerException.class, () -> {
            CurrencyUnit.of("EUR").compareTo(null);
        });

    }

    //-----------------------------------------------------------------------
    // equals() hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_hashCode() {
        CurrencyUnit a = CurrencyUnit.of("GBP");
        CurrencyUnit b = CurrencyUnit.of("GBP");
        CurrencyUnit c = CurrencyUnit.of("EUR");
        assertTrue(a.equals(a));
        assertTrue(b.equals(b));
        assertTrue(c.equals(c));

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertTrue(a.hashCode() == b.hashCode());

        assertFalse(a.equals(c));
        assertFalse(b.equals(c));
    }

    @Test
    public void test_equals_false() {
        CurrencyUnit a = CurrencyUnit.of("GBP");
        assertFalse(a.equals(null));
        Object obj = "String";  // avoid warning in Eclipse
        assertFalse(a.equals(obj));
        assertFalse(a.equals(new Object()));
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        CurrencyUnit test = CurrencyUnit.of("GBP");
        assertEquals("GBP", test.toString());
    }

}
