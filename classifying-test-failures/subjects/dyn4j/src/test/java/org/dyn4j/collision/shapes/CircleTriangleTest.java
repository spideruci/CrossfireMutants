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
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Test case for {@link Circle} - {@link Triangle} collision detection.
 * @author William Bittle
 * @version 4.2.1
 * @since 1.0.0
 */
public class CircleTriangleTest extends AbstractNarrowphaseShapeTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** The test {@link Circle} */
	private Circle circ;
	
	/** The test {@link Triangle} */
	private Triangle tri;
	
	/**
	 * Sets up the test.
	 */
	@BeforeEach
	public void setup() {
		this.circ = new Circle(1.0);
		this.tri = new Triangle(
				new Vector2(0.5, 0.5),
				new Vector2(-0.3, -0.5),
				new Vector2(1.0, -0.3));
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
		TestCase.assertTrue(this.sat.detect(circ, t1, tri, t2, p));
		TestCase.assertTrue(this.sat.detect(circ, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.780, n.x, 1.0e-3);
		TestCase.assertEquals(-0.624, n.y, 1.0e-3);
		TestCase.assertEquals(0.921, p.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.sat.detect(tri, t2, circ, t1, p));
		TestCase.assertTrue(this.sat.detect(tri, t2, circ, t1));
		n = p.getNormal();
		TestCase.assertEquals(-0.780, n.x, 1.0e-3);
		TestCase.assertEquals(0.624, n.y, 1.0e-3);
		TestCase.assertEquals(0.921, p.getDepth(), 1.0e-3);
		
		// test overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.sat.detect(circ, t1, tri, t2, p));
		TestCase.assertTrue(this.sat.detect(circ, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.813, n.x, 1.0e-3);
		TestCase.assertEquals(-0.581, n.y, 1.0e-3);
		TestCase.assertEquals(0.139, p.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.sat.detect(tri, t2, circ, t1, p));
		TestCase.assertTrue(this.sat.detect(tri, t2, circ, t1));
		n = p.getNormal();
		TestCase.assertEquals(-0.813, n.x, 1.0e-3);
		TestCase.assertEquals(0.581, n.y, 1.0e-3);
		TestCase.assertEquals(0.139, p.getDepth(), 1.0e-3);
		
		// test AABB overlap
		p.clear();
		t2.translate(0.0, 1.3);
		TestCase.assertFalse(this.sat.detect(circ, t1, tri, t2, p));
		TestCase.assertFalse(this.sat.detect(circ, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.sat.detect(tri, t2, circ, t1, p));
		TestCase.assertFalse(this.sat.detect(tri, t2, circ, t1));
		
		// test no overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertFalse(this.sat.detect(circ, t1, tri, t2, p));
		TestCase.assertFalse(this.sat.detect(circ, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.sat.detect(tri, t2, circ, t1, p));
		TestCase.assertFalse(this.sat.detect(tri, t2, circ, t1));
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
		TestCase.assertTrue(this.gjk.detect(circ, t1, tri, t2, p));
		TestCase.assertTrue(this.gjk.detect(circ, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.780, n.x, 1.0e-3);
		TestCase.assertEquals(-0.624, n.y, 1.0e-3);
		TestCase.assertEquals(0.921, p.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(tri, t2, circ, t1, p));
		TestCase.assertTrue(this.gjk.detect(tri, t2, circ, t1));
		n = p.getNormal();
		TestCase.assertEquals(-0.780, n.x, 1.0e-3);
		TestCase.assertEquals(0.624, n.y, 1.0e-3);
		TestCase.assertEquals(0.921, p.getDepth(), 1.0e-3);
		
		// test overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.gjk.detect(circ, t1, tri, t2, p));
		TestCase.assertTrue(this.gjk.detect(circ, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.813, n.x, 1.0e-3);
		TestCase.assertEquals(-0.581, n.y, 1.0e-3);
		TestCase.assertEquals(0.139, p.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(tri, t2, circ, t1, p));
		TestCase.assertTrue(this.gjk.detect(tri, t2, circ, t1));
		n = p.getNormal();
		TestCase.assertEquals(-0.813, n.x, 1.0e-3);
		TestCase.assertEquals(0.581, n.y, 1.0e-3);
		TestCase.assertEquals(0.139, p.getDepth(), 1.0e-3);
		
		// test AABB overlap
		p.clear();
		t2.translate(0.0, 1.3);
		TestCase.assertFalse(this.gjk.detect(circ, t1, tri, t2, p));
		TestCase.assertFalse(this.gjk.detect(circ, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.gjk.detect(tri, t2, circ, t1, p));
		TestCase.assertFalse(this.gjk.detect(tri, t2, circ, t1));
		
		// test no overlap
		p.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertFalse(this.gjk.detect(circ, t1, tri, t2, p));
		TestCase.assertFalse(this.gjk.detect(circ, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.gjk.detect(tri, t2, circ, t1, p));
		TestCase.assertFalse(this.gjk.detect(tri, t2, circ, t1));
	}
	
	/**
	 * Tests the {@link Gjk} distance method.
	 */
	@Test
	public void gjkDistance() {
		Separation s = new Separation();
		
		Transform t1 = new Transform();
		Transform t2 = new Transform();
		
		Vector2 n, p1, p2;
		
		// test containment
		TestCase.assertFalse(this.gjk.distance(circ, t1, tri, t2, s));
		
		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(tri, t2, circ, t1, s));
		
		// test overlap
		s.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertFalse(this.gjk.distance(circ, t1, tri, t2, s));
		
		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(tri, t2, circ, t1, s));
		
		// test AABB overlap
		s.clear();
		t2.translate(0.0, 1.3);
		TestCase.assertTrue(this.gjk.distance(circ, t1, tri, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.063, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.658, n.x, 1.0e-3);
		TestCase.assertEquals(0.752, n.y, 1.0e-3);
		TestCase.assertEquals(-0.341, p1.x, 1.0e-3);
		TestCase.assertEquals(0.752, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.3, p2.x, 1.0e-3);
		TestCase.assertEquals(0.8, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(tri, t2, circ, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.063, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.658, n.x, 1.0e-3);
		TestCase.assertEquals(-0.752, n.y, 1.0e-3);
		TestCase.assertEquals(-0.3, p1.x, 1.0e-3);
		TestCase.assertEquals(0.8, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.341, p2.x, 1.0e-3);
		TestCase.assertEquals(0.752, p2.y, 1.0e-3);
		
		// test no overlap
		s.clear();
		t1.translate(-1.0, 0.0);
		TestCase.assertTrue(this.gjk.distance(circ, t1, tri, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.878, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.904, n.x, 1.0e-3);
		TestCase.assertEquals(0.425, n.y, 1.0e-3);
		TestCase.assertEquals(-1.095, p1.x, 1.0e-3);
		TestCase.assertEquals(0.425, p1.y, 1.0e-3);
		TestCase.assertEquals(-0.3, p2.x, 1.0e-3);
		TestCase.assertEquals(0.8, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(tri, t2, circ, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.878, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.904, n.x, 1.0e-3);
		TestCase.assertEquals(-0.425, n.y, 1.0e-3);
		TestCase.assertEquals(-0.3, p1.x, 1.0e-3);
		TestCase.assertEquals(0.8, p1.y, 1.0e-3);
		TestCase.assertEquals(-1.095, p2.x, 1.0e-3);
		TestCase.assertEquals(0.425, p2.y, 1.0e-3);
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
		this.gjk.detect(circ, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, circ, t1, tri, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(tri, t2, circ, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, circ, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		// test containment sat
		p.clear();
		m.clear();
		this.sat.detect(circ, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, circ, t1, tri, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.sat.detect(tri, t2, circ, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, circ, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		
		t1.translate(-1.0, 0.0);
		
		// test overlap gjk
		p.clear();
		m.clear();
		this.gjk.detect(circ, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, circ, t1, tri, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(0.139, mp.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.186, p1.x, 1.0e-3);
		TestCase.assertEquals(-0.581, p1.y, 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(tri, t2, circ, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, circ, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(0.139, mp.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.186, p1.x, 1.0e-3);
		TestCase.assertEquals(-0.581, p1.y, 1.0e-3);
		
		// test overlap sat
		p.clear();
		m.clear();
		this.sat.detect(circ, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, circ, t1, tri, t2, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(0.139, mp.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.186, p1.x, 1.0e-3);
		TestCase.assertEquals(-0.581, p1.y, 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.sat.detect(tri, t2, circ, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, circ, t1, m));
		TestCase.assertEquals(1, m.getPoints().size());
		mp = m.getPoints().get(0);
		p1 = mp.getPoint();
		TestCase.assertEquals(0.139, mp.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.186, p1.x, 1.0e-3);
		TestCase.assertEquals(-0.581, p1.y, 1.0e-3);
	}
}
