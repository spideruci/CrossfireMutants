/* Copyright (C) 2010  Egon Willighagen <egonw@users.sf.net>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk;

import org.junit.jupiter.api.*;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import test.AbstractChemObjectBuilderTest;
//import org.openscience.cdk.test.AbstractChemObjectBuilderTest;

/**
 * Checks the functionality of the {@link IChemObjectBuilder} {@link DefaultChemObjectBuilder} implementation.
 *
 * @cdk.module test-data
 */
class DefaultChemObjectBuilderTest extends AbstractChemObjectBuilderTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    @BeforeEach
    void setUp() {
        setRootObject(new ChemObject());
    }

    @Test
    void testGetInstance() {
        Object builder = DefaultChemObjectBuilder.getInstance();
        Assertions.assertNotNull(builder);
        Assertions.assertTrue(builder instanceof IChemObjectBuilder);
        Assertions.assertTrue(builder.getClass().getName().contains("DefaultChemObjectBuilder"));
    }

}
