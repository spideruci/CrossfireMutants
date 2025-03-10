/* Copyright (C) 2002-2007  Egon Willighagen <egonw@users.sf.net>
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
//import org.openscience.cdk.test.interfaces.AbstractFragmentAtomTest;
import org.openscience.cdk.interfaces.IFragmentAtom;
import test.interfaces.AbstractFragmentAtomTest;

/**
 * Checks the functionality of the FragmentAtom.
 *
 * @cdk.module test-data
 */
class FragmentAtomTest extends AbstractFragmentAtomTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    @BeforeEach
    void setUp() {
        setTestObjectBuilder(FragmentAtom::new);
    }

    @Test
    void testFragmentAtom() {
        IFragmentAtom a = new FragmentAtom();
        Assertions.assertNotNull(a);
    }

}
