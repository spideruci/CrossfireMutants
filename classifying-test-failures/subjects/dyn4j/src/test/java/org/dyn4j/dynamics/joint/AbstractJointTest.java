/*
 * Copyright (c) 2010-2022 William Bittle  http://www.dyn4j.org/
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *     and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *     and the following disclaimer in the documentation and/or other materials provided with the 
 *     distribution.
 *   * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or 
 *     promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.dyn4j.dynamics.joint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dyn4j.dynamics.Body;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

/**
 * Tests the abstract joint's methods.
 * @author William Bittle
 * @version 5.0.0
 * @since 5.0.0
 */
public class AbstractJointTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** The first body used for testing */
	protected Body b1;
	
	/** The second body used for testing */
	protected Body b2;
	
	/** The abstract joint */
	protected AbstractJoint<Body> aj;
	
	/**
	 * Sets up the test.
	 */
	@BeforeEach
	public void setup() {
		this.b1 = new Body();
		this.b2 = new Body();
		this.aj = new TestAbstractJoint(Arrays.asList(b1, b2));
	}
	
	/**
	 * Tests the state of the joint at create time.
	 */
	@Test
	public void create() {
		TestCase.assertEquals(2, aj.getBodyCount());
		TestCase.assertNull(aj.owner);
		TestCase.assertNull(aj.getOwner());
		TestCase.assertNull(aj.userData);
		TestCase.assertNull(aj.getUserData());
		TestCase.assertFalse(aj.collisionAllowed);
		TestCase.assertFalse(aj.isCollisionAllowed());
		TestCase.assertEquals(b1, aj.getBody(0));
		TestCase.assertEquals(b2, aj.getBody(1));
		TestCase.assertNotNull(aj.getBodies());
		TestCase.assertNotNull(aj.getBodyIterator());
		TestCase.assertNotNull(aj.toString());
	}
	
	/**
	 * Tests receiving an NPE when passing a null list.
	 */
	@Test
	public void createNullList() {
		assertThrows(NullPointerException.class, () -> {
			new TestAbstractJoint(null);
		});

	}
	
	/**
	 * Tests receiving an NPE when passing a null list.
	 */
	@Test
	public void createListWithNullElement() {
		assertThrows(NullPointerException.class, () -> {
			ArrayList<Body> list = new ArrayList<>();
			list.add(new Body());
			list.add(null);
			new TestAbstractJoint(list);
		});

	}
	
	/**
	 * Tests receiving an NPE when passing a null list.
	 */
	@Test
	public void createEmptyList() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayList<Body> list = new ArrayList<>();
			new TestAbstractJoint(list);
		});

	}
	
	/**
	 * Tests the getBodies method.
	 */
	@Test
	public void getBodies() {
		List<Body> bodies = aj.getBodies();
		
		TestCase.assertNotNull(bodies);
		TestCase.assertEquals(2, bodies.size());
		TestCase.assertEquals(b1, bodies.get(0));
		TestCase.assertEquals(b2, bodies.get(1));
	}
	
	/**
	 * Makes sure the returned list is unmodifiable.
	 */
	@Test
	public void getBodiesAndAdd() {
		assertThrows(UnsupportedOperationException.class, () -> {
			AbstractJoint<Body> aj = new TestAbstractJoint(Arrays.asList(b1, b2));
			aj.getBodies().add(new Body());
		});

	}
	
	/**
	 * Makes sure the returned list is unmodifiable.
	 */
	@Test
	public void getBodiesAndRemove() {
		assertThrows(UnsupportedOperationException.class, () -> {
			AbstractJoint<Body> aj = new TestAbstractJoint(Arrays.asList(b1, b2));
			aj.getBodies().remove(0);
		});

	}
	
	/**
	 * Tests successfully getting the bodies by index.
	 */
	@Test
	public void getBodyAtValidIndex() {
		TestCase.assertEquals(b1, aj.getBody(0));
		TestCase.assertEquals(b2, aj.getBody(1));
	}
	
	/**
	 * Tests getting bodies at a negative index.
	 */
	@Test
	public void getBodyAtNegativeIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			aj.getBody(-1);
		});

	}
	
	/**
	 * Tests getting bodies at an index over the size.
	 */
	@Test
	public void getBodyAtTooHighIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			aj.getBody(2);
		});

	}
	
	/**
	 * Tests getting the body count.
	 */
	@Test
	public void getBodyCount() {
		TestCase.assertEquals(2, aj.getBodyCount());
	}
	
	/**
	 * Tests getting the body iterator and iterating through the bodies.
	 */
	@Test
	public void getBodyIterator() {
		Iterator<Body> it = aj.getBodyIterator();
		TestCase.assertNotNull(it);
		
		Body b1 = null;
		Body b2 = null;
		
		
		int n = 0;
		while (it.hasNext()) {
			if (n == 0) b1 = it.next();
			if (n == 1) b2 = it.next();
			n++;
		}
		
		TestCase.assertEquals(2, n);
		TestCase.assertEquals(this.b1, b1);
		TestCase.assertEquals(this.b2, b2);
	}
	
	/**
	 * Tests prevention of removal by the iterator.
	 */
	@Test
	public void getBodyIteratorRemove() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Iterator<Body> it = aj.getBodyIterator();
			while (it.hasNext()) {
				it.next();
				it.remove();
			}
		});

	}
	
	/**
	 * Tests the isMember method.
	 */
	@Test
	public void isMember() {
		TestCase.assertTrue(aj.isMember(b1));
		TestCase.assertTrue(aj.isMember(b2));
		TestCase.assertFalse(aj.isMember(null));
		TestCase.assertFalse(aj.isMember(new Body()));
	}
	
	/**
	 * Tests the isEnabled method.
	 */
	@Test
	public void isEnabled() {
		TestCase.assertTrue(aj.isEnabled());
		
		b1.setEnabled(false);
		
		TestCase.assertFalse(aj.isEnabled());
		
		b2.setEnabled(false);
		
		TestCase.assertFalse(aj.isEnabled());
		
		b1.setEnabled(true);
		b2.setEnabled(true);
		
		TestCase.assertTrue(aj.isEnabled());
	}
	
	/**
	 * Tests the get/set collision allowed methods.
	 */
	@Test
	public void getSetCollisionAllowed() {
		aj.setCollisionAllowed(false);
		TestCase.assertEquals(false, aj.isCollisionAllowed());
		
		aj.setCollisionAllowed(true);
		TestCase.assertEquals(true, aj.isCollisionAllowed());
		
		aj.setCollisionAllowed(false);
		TestCase.assertEquals(false, aj.isCollisionAllowed());
	}
	
	/**
	 * Tests the get/set user data methods.
	 */
	@Test
	public void getSetUserData() {
		aj.setUserData(null);
		TestCase.assertEquals(null, aj.getUserData());
		
		Object o = new Object();
		aj.setUserData(o);
		TestCase.assertEquals(o, aj.getUserData());
		
		aj.setUserData(null);
		TestCase.assertEquals(null, aj.getUserData());
	}

	/**
	 * Tests the get/set owner methods.
	 */
	@Test
	public void getSetOwner() {
		aj.setOwner(null);
		TestCase.assertEquals(null, aj.getOwner());
		
		Object o = new Object();
		aj.setOwner(o);
		TestCase.assertEquals(o, aj.getOwner());
		
		aj.setOwner(null);
		TestCase.assertEquals(null, aj.getOwner());
	}
	
	/**
	 * Tests the getConstraintImpulseMixing method.
	 */
	@Test
	public void getConstraintImpulseMixing() {
		double v = TestAbstractJoint.getConstraintImpulseMixing(2, 3, 4);
		TestCase.assertEquals(1.0 / 20.0, v);
	}
	
	/**
	 * Tests the getErrorReductionParameter method.
	 */
	@Test
	public void getErrorReductionParameter() {
		double v = TestAbstractJoint.getErrorReductionParameter(2, 3, 4);
		TestCase.assertEquals(0.3, v);
	}
	
	/**
	 * Tests the getFrequency method.
	 */
	@Test
	public void getFrequency() {
		double v = TestAbstractJoint.getFrequency(4);
		TestCase.assertEquals(2.0 / Math.PI, v);
	}
	
	/**
	 * Tests the getSpringStiffness method.
	 */
	@Test
	public void getSpringStiffness() {
		double v = TestAbstractJoint.getSpringStiffness(2, 3);
		TestCase.assertEquals(18.0, v);
	}
	
	/**
	 * Tests the getNaturalFrequency methods.
	 */
	@Test
	public void getNaturalFrequency() {
		double v = TestAbstractJoint.getNaturalFrequency(3);
		TestCase.assertEquals(6.0 * Math.PI, v);
		
		v = TestAbstractJoint.getNaturalFrequency(8, 2);
		TestCase.assertEquals(2.0, v);
	}
	
	/**
	 * Tests the getSpringDampingCoefficient method.
	 */
	@Test
	public void getSpringDampingCoefficient() {
		double v = TestAbstractJoint.getSpringDampingCoefficient(2, 3, 4);
		TestCase.assertEquals(48.0, v);
	}
}
