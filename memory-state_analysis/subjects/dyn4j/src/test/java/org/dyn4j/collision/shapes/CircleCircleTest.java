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
package org.dyn4j.collision.shapes;

import org.dyn4j.collision.manifold.ClippingManifoldSolver;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.manifold.ManifoldPoint;
import org.dyn4j.collision.narrowphase.Gjk;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.collision.narrowphase.Sat;
import org.dyn4j.collision.narrowphase.Separation;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Test case for {@link Circle} - {@link Circle} collision detection.
 * @author William Bittle
 * @version 4.2.1
 * @since 1.0.0
 */
public class CircleCircleTest extends AbstractNarrowphaseShapeTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** The first test {@link Circle} */
	private Circle c1;
	
	/** The second test {@link Circle} */
	private Circle c2;
	
	/**
	 * Sets up the test.
	 */
	@BeforeEach
	public void setup() {
		this.c1 = new Circle(1.0);
		this.c2 = new Circle(0.5);
	}
	
	/**
	 * Tests {@link Sat}.
	 */
	@Test
	public void detectSat() {
		Penetration p = new Penetration();
		Transform t1 = new Transform();
		Transform t2 = new Transform();
		Vector2 n = null;
		
		// test containment
		TestCase.assertTrue(this.sat.detect(c1, t1, c2, t2, p));
		TestCase.assertTrue(this.sat.detect(c1, t1, c2, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(1.5, p.getDepth());
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.sat.detect(c2, t2, c1, t1, p));
		TestCase.assertTrue(this.sat.detect(c2, t2, c1, t1));
		n = p.getNormal();
		TestCase.assertEquals(0.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(1.5, p.getDepth());
		
		// test overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.sat.detect(c1, t1, c2, t2, p));
		TestCase.assertTrue(this.sat.detect(c1, t1, c2, t2));
		n = p.getNormal();
		TestCase.assertEquals(1.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(0.5, p.getDepth());
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.sat.detect(c2, t2, c1, t1, p));
		TestCase.assertTrue(this.sat.detect(c2, t2, c1, t1));
		n = p.getNormal();
		TestCase.assertEquals(-1.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(0.5, p.getDepth());
		
		// test AABB overlap
		p.clear();
		t2.translate(0.0, 1.5);
		TestCase.assertFalse(this.sat.detect(c1, t1, c2, t2, p));
		TestCase.assertFalse(this.sat.detect(c1, t1, c2, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.sat.detect(c2, t2, c1, t1, p));
		TestCase.assertFalse(this.sat.detect(c2, t2, c1, t1));
		
		// test no overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertFalse(this.sat.detect(c1, t1, c2, t2, p));
		TestCase.assertFalse(this.sat.detect(c1, t1, c2, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.sat.detect(c2, t2, c1, t1, p));
		TestCase.assertFalse(this.sat.detect(c2, t2, c1, t1));
	}
	
	/**
	 * Tests {@link Gjk}.
	 */
	@Test
	public void detectGjk() {
		Penetration p = new Penetration();
		Transform t1 = new Transform();
		Transform t2 = new Transform();
		Vector2 n = null;
		
		// test containment
		TestCase.assertTrue(this.gjk.detect(c1, t1, c2, t2, p));
		TestCase.assertTrue(this.gjk.detect(c1, t1, c2, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(1.5, p.getDepth());
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1, p));
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1));
		n = p.getNormal();
		TestCase.assertEquals(0.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(1.5, p.getDepth());
		
		// test overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.gjk.detect(c1, t1, c2, t2, p));
		TestCase.assertTrue(this.gjk.detect(c1, t1, c2, t2));
		n = p.getNormal();
		TestCase.assertEquals(1.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(0.5, p.getDepth());
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1, p));
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1));
		n = p.getNormal();
		TestCase.assertEquals(-1.0, n.x);
		TestCase.assertEquals(0.0, n.y);
		TestCase.assertEquals(0.5, p.getDepth());
		
		// test AABB overlap
		p.clear();
		t2.translate(0.0, 1.5);
		TestCase.assertFalse(this.gjk.detect(c1, t1, c2, t2, p));
		TestCase.assertFalse(this.gjk.detect(c1, t1, c2, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.gjk.detect(c2, t2, c1, t1, p));
		TestCase.assertFalse(this.gjk.detect(c2, t2, c1, t1));
		
		// test no overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertFalse(this.gjk.detect(c1, t1, c2, t2, p));
		TestCase.assertFalse(this.gjk.detect(c1, t1, c2, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.gjk.detect(c2, t2, c1, t1, p));
		TestCase.assertFalse(this.gjk.detect(c2, t2, c1, t1));
	}
	
	/**
	 * Tests the {@link Gjk} distance method.
	 */
	@Test
	public void gjkDistance() {
		Separation s = new Separation();
		
		Transform t1 = new Transform();
		Transform t2 = new Transform();
		
		Vector2 n = null;
		Vector2 p1 = null;
		Vector2 p2 = null;
		
		// test containment
		TestCase.assertFalse(this.gjk.distance(c1, t1, c2, t2, s));
		
		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(c2, t2, c1, t1, s));
		
		// test overlap
		s.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertFalse(this.gjk.distance(c1, t1, c2, t2, s));
		
		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(c2, t2, c1, t1, s));
		
		// test AABB overlap
		s.clear();
		t2.translate(0.0, 1.5);
		TestCase.assertTrue(this.gjk.distance(c1, t1, c2, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.302, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.554, n.x, 1.0e-3);
		TestCase.assertEquals(0.832, n.y, 1.0e-3);
		TestCase.assertEquals(-0.445, p1.x, 1.0e-3);
		TestCase.assertEquals(0.832, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.277, p2.x, 1.0e-3);
		TestCase.assertEquals(1.083, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(c2, t2, c1, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.302, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.554, n.x, 1.0e-3);
		TestCase.assertEquals(-0.832, n.y, 1.0e-3);
		TestCase.assertEquals(-0.277, p1.x, 1.0e-3);
		TestCase.assertEquals(1.083, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.445, p2.x, 1.0e-3);
		TestCase.assertEquals(0.832, p2.y, 1.0e-3);
		
		// test no overlap
		s.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.gjk.distance(c1, t1, c2, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(1.000, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.800, n.x, 1.0e-3);
		TestCase.assertEquals(0.600, n.y, 1.0e-3);
		TestCase.assertEquals(-1.200, p1.x, 1.0e-3);
		TestCase.assertEquals(0.600, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.400, p2.x, 1.0e-3);
		TestCase.assertEquals(1.200, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(c2, t2, c1, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(1.000, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.800, n.x, 1.0e-3);
		TestCase.assertEquals(-0.600, n.y, 1.0e-3);
		TestCase.assertEquals(-0.400, p1.x, 1.0e-3);
		TestCase.assertEquals(1.200, p1.y, 1.0e-3);
		TestCase.assertEquals(-1.200, p2.x, 1.0e-3);
		TestCase.assertEquals(0.600, p2.y, 1.0e-3);
	}
	
	/**
	 * Test the {@link ClippingManifoldSolver}.
	 */
	@Test
	public void getClipManifold() {
		Manifold m = new Manifold();
		Penetration p = new Penetration();
		
		Transform t1 = new Transform();
		Transform t2 = new Transform();
		
		ManifoldPoint mp = null;
		Vector2 p1 = null;
		
		// test containment gjk
		this.gjk.detect(c1, t1, c2, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c1, t1, c2, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(c2, t2, c1, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c2, t2, c1, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		// test containment sat
		p.clear();
		m.clear();
		this.sat.detect(c1, t1, c2, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c1, t1, c2, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(c2, t2, c1, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c2, t2, c1, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		t1.translate(-1.0, 0.0);
		
		// test overlap gjk
		p.clear();
		m.clear();
		this.gjk.detect(c1, t1, c2, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c1, t1, c2, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(0.0, p1.x);
		TestCase.assertEquals(0.0, p1.y);
		TestCase.assertEquals(0.5, mp.getDepth());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(c2, t2, c1, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c2, t2, c1, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(-0.5, p1.x);
		TestCase.assertEquals(0.0, p1.y);
		TestCase.assertEquals(0.5, mp.getDepth());
		
		// test overlap sat
		p.clear();
		m.clear();
		this.sat.detect(c1, t1, c2, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c1, t1, c2, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(0.0, p1.x);
		TestCase.assertEquals(0.0, p1.y);
		TestCase.assertEquals(0.5, mp.getDepth());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(c2, t2, c1, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c2, t2, c1, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(-0.5, p1.x);
		TestCase.assertEquals(0.0, p1.y);
		TestCase.assertEquals(0.5, mp.getDepth());
	}
}
