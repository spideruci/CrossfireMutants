/*
 * Copyright (c) 2017 John Mayfield <jwmay@users.sf.net>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version. All we ask is that proper credit is given
 * for our work, which includes - but is not limited to - adding the above
 * copyright notice to the beginning of your source code files, and to any
 * copyright notice that you may distribute with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.openscience.cdk;

import org.junit.jupiter.api.*;
//import org.openscience.cdk.test.interfaces.AbstractAtomContainerTest;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.ILonePair;
import test.interfaces.AbstractAtomContainerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static test.interfaces.AbstractCDKObjectTest.setTestObjectBuilder;

/**
 * Checks the functionality of the AtomContainer.
 *
 * @cdk.module test-data
 */
class AtomContainer2Test extends AbstractAtomContainerTest {
    @BeforeAll
    public static void myBeforeAll() {

    }

    @AfterAll
    public static void myAfterAll() {

    }

    @BeforeEach
    void setUp() {
        setTestObjectBuilder(AtomContainer2::new);
    }

    @Test
    void testAtomContainer_int_int_int_int() {
        // create an empty container with predefined
        // array lengths
        IAtomContainer ac = new AtomContainer2(5, 6, 1, 2);

        Assertions.assertEquals(0, ac.getAtomCount());
        Assertions.assertEquals(0, ac.getElectronContainerCount());

        // test whether the ElectronContainer is correctly initialized
        IAtom a1 = ac.getBuilder().newInstance(IAtom.class, "C");
        IAtom a2 = ac.getBuilder().newInstance(IAtom.class, "C");
        ac.addAtom(a1);
        ac.addAtom(a2);
        ac.addBond(ac.getBuilder().newInstance(IBond.class, a1, a2, IBond.Order.DOUBLE));
        ac.addLonePair(ac.getBuilder().newInstance(ILonePair.class, ac.getBuilder().newInstance(IAtom.class, "N")));
    }

    @Test
    void testAtomContainer() {
        // create an empty container with in the constructor defined array lengths
        IAtomContainer container = new AtomContainer2();

        Assertions.assertEquals(0, container.getAtomCount());
        Assertions.assertEquals(0, container.getBondCount());

        // test whether the ElectronContainer is correctly initialized
        IAtom a1 = container.getBuilder().newInstance(IAtom.class, "C");
        IAtom a2 = container.getBuilder().newInstance(IAtom.class, "C");
        container.addAtom(a1);
        container.addAtom(a2);
        container.addBond(container.getBuilder().newInstance(IBond.class,
                                                             a1, a2,
                                                             IBond.Order.DOUBLE));
        container.addLonePair(container.getBuilder().newInstance(ILonePair.class,
                container.getBuilder().newInstance(IAtom.class, "N")));
    }

    @Test
    void testAtomContainer_IAtomContainer() {
        IAtomContainer acetone = newChemObject().getBuilder().newInstance(IAtomContainer.class);
        IAtom c1 = acetone.getBuilder().newInstance(IAtom.class, "C");
        IAtom c2 = acetone.getBuilder().newInstance(IAtom.class, "C");
        IAtom o = acetone.getBuilder().newInstance(IAtom.class, "O");
        IAtom c3 = acetone.getBuilder().newInstance(IAtom.class, "C");
        acetone.addAtom(c1);
        acetone.addAtom(c2);
        acetone.addAtom(c3);
        acetone.addAtom(o);
        IBond b1 = acetone.getBuilder().newInstance(IBond.class, c1, c2, IBond.Order.SINGLE);
        IBond b2 = acetone.getBuilder().newInstance(IBond.class, c1, o, IBond.Order.DOUBLE);
        IBond b3 = acetone.getBuilder().newInstance(IBond.class, c1, c3, IBond.Order.SINGLE);
        acetone.addBond(b1);
        acetone.addBond(b2);
        acetone.addBond(b3);

        IAtomContainer container = new AtomContainer(acetone);
        Assertions.assertEquals(4, container.getAtomCount());
        Assertions.assertEquals(3, container.getBondCount());
    }

    @Test
    void testAtomGetBond() {
        IAtomContainer mol = (IAtomContainer) newChemObject();
        IAtom          a1 = mol.getBuilder().newAtom();
        IAtom          a2 = mol.getBuilder().newAtom();
        IAtom          a3 = mol.getBuilder().newAtom();
        a1.setSymbol("CH3");
        a2.setSymbol("CH2");
        a3.setSymbol("OH");
        mol.addAtom(a1);
        mol.addAtom(a2);
        mol.addAtom(a3);
        mol.addBond(0, 1, IBond.Order.SINGLE);
        mol.addBond(1, 2, IBond.Order.SINGLE);
        assertThat(mol.getBond(0),
                   is(mol.getAtom(0).getBond(mol.getAtom(1))));
        assertThat(mol.getBond(1),
                   is(mol.getAtom(1).getBond(mol.getAtom(2))));
        Assertions.assertNull(mol.getAtom(0).getBond(mol.getAtom(2)));
    }
}
