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
import org.dyn4j.geometry.HalfEllipse;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

/**
 * Test case for {@link HalfEllipse} - {@link HalfEllipse} collision detection.
 * @author William Bittle
 * @version 4.2.1
 * @since 3.1.5
 */
public class HalfEllipseHalfEllipseTest extends AbstractNarrowphaseShapeTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** The first test {@link HalfEllipse} */
	private HalfEllipse c1;
	
	/** The second test {@link HalfEllipse} */
	private HalfEllipse c2;
	
	/**
	 * Sets up the test.
	 */
	@BeforeEach
	public void setup() {
		this.c1 = new HalfEllipse(1.0, 0.5);
		this.c2 = new HalfEllipse(0.5, 1.0);
	}
	
	/**
	 * Tests {@link Sat}.
	 */
	@Test
	public void detectSat() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Penetration p = new Penetration();
			Transform t1 = new Transform();
			Transform t2 = new Transform();
			this.sat.detect(c1, t1, c2, t2, p);
		});

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
		TestCase.assertEquals( 0.000, n.x, 1.0e-3);
		TestCase.assertEquals( 1.000, n.y, 1.0e-3);
		TestCase.assertEquals( 0.500, p.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1, p));
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1));
		n = p.getNormal();
		TestCase.assertEquals( 0.000, n.x, 1.0e-3);
		TestCase.assertEquals(-1.000, n.y, 1.0e-3);
		TestCase.assertEquals( 0.500, p.getDepth(), 1.0e-3);
		
		// test overlap
		p.clear();
		t1.translate(-0.5, 0.0);
		TestCase.assertTrue(this.gjk.detect(c1, t1, c2, t2, p));
		TestCase.assertTrue(this.gjk.detect(c1, t1, c2, t2));
		n = p.getNormal();
		TestCase.assertEquals( 0.999, n.x, 1.0e-3);
		TestCase.assertEquals( 0.001, n.y, 1.0e-3);
		TestCase.assertEquals( 0.250, p.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1, p));
		TestCase.assertTrue(this.gjk.detect(c2, t2, c1, t1));
		n = p.getNormal();
		TestCase.assertEquals(-0.999, n.x, 1.0e-3);
		TestCase.assertEquals(-0.001, n.y, 1.0e-3);
		TestCase.assertEquals( 0.250, p.getDepth(), 1.0e-3);
		
		// test AABB overlap
		p.clear();
		t2.translate(0.2, -0.65);
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
		t1.translate(-0.5, 0.0);
		TestCase.assertFalse(this.gjk.distance(c1, t1, c2, t2, s));

		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(c2, t2, c1, t1, s));
		
		// test AABB overlap
		s.clear();
		t2.translate(0.2, -0.65);
		TestCase.assertTrue(this.gjk.distance(c1, t1, c2, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals( 0.009, s.getDistance(), 1.0e-3);
		TestCase.assertEquals( 0.978, n.x, 1.0e-3);
		TestCase.assertEquals(-0.207, n.y, 1.0e-3);
		TestCase.assertEquals( 0.000, p1.x, 1.0e-3);
		TestCase.assertEquals( 0.000, p1.y, 1.0e-3);
		TestCase.assertEquals( 0.009, p2.x, 1.0e-3);
		TestCase.assertEquals(-0.002, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(c2, t2, c1, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals( 0.009, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.978, n.x, 1.0e-3);
		TestCase.assertEquals( 0.207, n.y, 1.0e-3);
		TestCase.assertEquals( 0.009, p1.x, 1.0e-3);
		TestCase.assertEquals(-0.002, p1.y, 1.0e-3);
		TestCase.assertEquals( 0.000, p2.x, 1.0e-3);
		TestCase.assertEquals( 0.000, p2.y, 1.0e-3);
		
		// test no overlap
		s.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.gjk.distance(c1, t1, c2, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals( 0.994, s.getDistance(), 1.0e-3);
		TestCase.assertEquals( 0.989, n.x, 1.0e-3);
		TestCase.assertEquals(-0.144, n.y, 1.0e-3);
		TestCase.assertEquals(-1.000, p1.x, 1.0e-3);
		TestCase.assertEquals( 0.000, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.015, p2.x, 1.0e-3);
		TestCase.assertEquals(-0.144, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(c2, t2, c1, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals( 0.994, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.989, n.x, 1.0e-3);
		TestCase.assertEquals( 0.144, n.y, 1.0e-3);
		TestCase.assertEquals(-0.015, p1.x, 1.0e-3);
		TestCase.assertEquals(-0.144, p1.y, 1.0e-3);
		TestCase.assertEquals(-1.000, p2.x, 1.0e-3);
		TestCase.assertEquals( 0.000, p2.y, 1.0e-3);
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
		m.clear();
		TestCase.assertTrue(this.cmfs.getManifold(p, c2, t2, c1, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		t1.translate(-0.5, 0.0);
		
		// test overlap gjk
		p.clear();
		m.clear();
		this.gjk.detect(c1, t1, c2, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c1, t1, c2, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals( 0.000, p1.x, 1.0e-3);
		TestCase.assertEquals( 0.000, p1.y, 1.0e-3);
		TestCase.assertEquals( 0.250, mp.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(c2, t2, c1, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, c2, t2, c1, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals( 0.000, p1.x, 1.0e-3);
		TestCase.assertEquals( 0.000, p1.y, 1.0e-3);
		TestCase.assertEquals( 0.250, mp.getDepth(), 1.0e-3);
	}
}
