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
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Test case for {@link Rectangle} - {@link Triangle} collision detection.
 * @author William Bittle
 * @version 4.2.1
 * @since 1.0.0
 */
public class RectangleTriangleTest extends AbstractNarrowphaseShapeTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** The test {@link Rectangle} */
	private Rectangle rect;
	
	/** The test {@link Triangle} */
	private Triangle tri;
	
	/**
	 * Sets up the test.
	 */
	@BeforeEach
	public void setup() {
		this.rect = new Rectangle(1.0, 1.0);
		this.tri = new Triangle(
				new Vector2(0.45, -0.12),
				new Vector2(-0.45, 0.38),
				new Vector2(-0.15, -0.22));
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
		TestCase.assertTrue(this.sat.detect(rect, t1, tri, t2, p));
		TestCase.assertTrue(this.sat.detect(rect, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.720, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(0.000, n.x, 1.0e-3);
		TestCase.assertEquals(1.000, n.y, 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.sat.detect(tri, t2, rect, t1, p));
		TestCase.assertTrue(this.sat.detect(tri, t2, rect, t1));
		n = p.getNormal();
		TestCase.assertEquals(0.720, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(0.000, n.x, 1.0e-3);
		TestCase.assertEquals(-1.000, n.y, 1.0e-3);
		
		// test overlap
		p.clear();
		t1.translate(-0.5, 0.0);
		TestCase.assertTrue(this.sat.detect(rect, t1, tri, t2, p));
		TestCase.assertTrue(this.sat.detect(rect, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.450, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(1.000, n.x, 1.0e-3);
		TestCase.assertEquals(0.000, n.y, 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.sat.detect(tri, t2, rect, t1, p));
		TestCase.assertTrue(this.sat.detect(tri, t2, rect, t1));
		n = p.getNormal();
		TestCase.assertEquals(0.450, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(-1.000, n.x, 1.0e-3);
		TestCase.assertEquals(0.000, n.y, 1.0e-3);
		
		// test AABB overlap
		p.clear();
		t2.translate(0.34, 0.42);
		TestCase.assertFalse(this.sat.detect(rect, t1, tri, t2, p));
		TestCase.assertFalse(this.sat.detect(rect, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.sat.detect(tri, t2, rect, t1, p));
		TestCase.assertFalse(this.sat.detect(tri, t2, rect, t1));
		
		// test no overlap
		p.clear();
		t2.translate(0.0, 0.58);
		TestCase.assertFalse(this.sat.detect(rect, t1, tri, t2, p));
		TestCase.assertFalse(this.sat.detect(rect, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.sat.detect(tri, t2, rect, t1, p));
		TestCase.assertFalse(this.sat.detect(tri, t2, rect, t1));
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
		TestCase.assertTrue(this.gjk.detect(rect, t1, tri, t2, p));
		TestCase.assertTrue(this.gjk.detect(rect, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.720, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(0.000, n.x, 1.0e-3);
		TestCase.assertEquals(1.000, n.y, 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(tri, t2, rect, t1, p));
		TestCase.assertTrue(this.gjk.detect(tri, t2, rect, t1));
		n = p.getNormal();
		TestCase.assertEquals(0.720, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(0.000, n.x, 1.0e-3);
		TestCase.assertEquals(-1.000, n.y, 1.0e-3);
		
		// test overlap
		p.clear();
		t1.translate(-0.5, 0.0);
		TestCase.assertTrue(this.gjk.detect(rect, t1, tri, t2, p));
		TestCase.assertTrue(this.gjk.detect(rect, t1, tri, t2));
		n = p.getNormal();
		TestCase.assertEquals(0.450, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(1.000, n.x, 1.0e-3);
		TestCase.assertEquals(0.000, n.y, 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		TestCase.assertTrue(this.gjk.detect(tri, t2, rect, t1, p));
		TestCase.assertTrue(this.gjk.detect(tri, t2, rect, t1));
		n = p.getNormal();
		TestCase.assertEquals(0.450, p.getDepth(), 1.0e-3);
		TestCase.assertEquals(-1.000, n.x, 1.0e-3);
		TestCase.assertEquals(0.000, n.y, 1.0e-3);
		
		// test AABB overlap
		p.clear();
		t2.translate(0.34, 0.42);
		TestCase.assertFalse(this.gjk.detect(rect, t1, tri, t2, p));
		TestCase.assertFalse(this.gjk.detect(rect, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.gjk.detect(tri, t2, rect, t1, p));
		TestCase.assertFalse(this.gjk.detect(tri, t2, rect, t1));
		
		// test no overlap
		p.clear();
		t2.translate(0.0, 0.58);
		TestCase.assertFalse(this.gjk.detect(rect, t1, tri, t2, p));
		TestCase.assertFalse(this.gjk.detect(rect, t1, tri, t2));
		
		// try reversing the shapes
		p.clear();
		TestCase.assertFalse(this.gjk.detect(tri, t2, rect, t1, p));
		TestCase.assertFalse(this.gjk.detect(tri, t2, rect, t1));
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
		TestCase.assertFalse(this.gjk.distance(rect, t1, tri, t2, s));
		
		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(tri, t2, rect, t1, s));
		
		// test overlap
		s.clear();
		t1.translate(-0.5, 0.0);
		TestCase.assertFalse(this.gjk.distance(rect, t1, tri, t2, s));
		
		// try reversing the shapes
		s.clear();
		TestCase.assertFalse(this.gjk.distance(tri, t2, rect, t1, s));
		
		// test AABB overlap
		s.clear();
		t2.translate(0.34, 0.42);
		TestCase.assertTrue(this.gjk.distance(rect, t1, tri, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.035, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.894, n.x, 1.0e-3);
		TestCase.assertEquals(0.447, n.y, 1.0e-3);
		TestCase.assertEquals(0.000, p1.x, 1.0e-3);
		TestCase.assertEquals(0.500, p1.y, 1.0e-3);
		TestCase.assertEquals(0.032, p2.x, 1.0e-3);
		TestCase.assertEquals(0.516, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(tri, t2, rect, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.035, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.894, n.x, 1.0e-3);
		TestCase.assertEquals(-0.447, n.y, 1.0e-3);
		TestCase.assertEquals(0.032, p1.x, 1.0e-3);
		TestCase.assertEquals(0.516, p1.y, 1.0e-3);
		TestCase.assertEquals(0.000, p2.x, 1.0e-3);
		TestCase.assertEquals(0.500, p2.y, 1.0e-3);
		
		// test no overlap
		s.clear();
		t2.translate(0.0, 0.58);
		TestCase.assertTrue(this.gjk.distance(rect, t1, tri, t2, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.338, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.561, n.x, 1.0e-3);
		TestCase.assertEquals(0.827, n.y, 1.0e-3);
		TestCase.assertEquals(0.000, p1.x, 1.0e-3);
		TestCase.assertEquals(0.500, p1.y, 1.0e-3);
		TestCase.assertEquals(0.190, p2.x, 1.0e-3);
		TestCase.assertEquals(0.780, p2.y, 1.0e-3);
		
		// try reversing the shapes
		s.clear();
		TestCase.assertTrue(this.gjk.distance(tri, t2, rect, t1, s));
		n = s.getNormal();
		p1 = s.getPoint1();
		p2 = s.getPoint2();
		TestCase.assertEquals(0.338, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(-0.561, n.x, 1.0e-3);
		TestCase.assertEquals(-0.827, n.y, 1.0e-3);
		TestCase.assertEquals(0.190, p1.x, 1.0e-3);
		TestCase.assertEquals(0.780, p1.y, 1.0e-3);
		TestCase.assertEquals(0.000, p2.x, 1.0e-3);
		TestCase.assertEquals(0.500, p2.y, 1.0e-3);
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
		
		ManifoldPoint mp1, mp2;
		Vector2 p1, p2;
		
		// test containment gjk
		this.gjk.detect(rect, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, rect, t1, tri, t2, m));
		TestCase.assertEquals(2, m.getPoints().size());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(tri, t2, rect, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, rect, t1, m));
		TestCase.assertEquals(2, m.getPoints().size());
		
		// test containment sat
		p.clear();
		m.clear();
		this.sat.detect(rect, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, rect, t1, tri, t2, m));
		TestCase.assertEquals(2, m.getPoints().size());
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.sat.detect(tri, t2, rect, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, rect, t1, m));
		TestCase.assertEquals(2, m.getPoints().size());
		
		t1.translate(-0.5, 0.0);
		
		// test overlap gjk
		p.clear();
		m.clear();
		this.gjk.detect(rect, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, rect, t1, tri, t2, m));
		TestCase.assertEquals(2, m.getPoints().size());
		mp1 = m.getPoints().get(0);
		mp2 = m.getPoints().get(1);
		p1 = mp1.getPoint();
		p2 = mp2.getPoint();
		TestCase.assertEquals(-0.450, p1.x, 1.0e-3);
		TestCase.assertEquals(0.380, p1.y, 1.0e-3);
		TestCase.assertEquals(0.450, mp1.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.150, p2.x, 1.0e-3);
		TestCase.assertEquals(-0.220, p2.y, 1.0e-3);
		TestCase.assertEquals(0.150, mp2.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.gjk.detect(tri, t2, rect, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, rect, t1, m));
		TestCase.assertEquals(2, m.getPoints().size());
		mp1 = m.getPoints().get(0);
		mp2 = m.getPoints().get(1);
		p1 = mp1.getPoint();
		p2 = mp2.getPoint();
		TestCase.assertEquals(-0.450, p1.x, 1.0e-3);
		TestCase.assertEquals(0.380, p1.y, 1.0e-3);
		TestCase.assertEquals(0.450, mp1.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.150, p2.x, 1.0e-3);
		TestCase.assertEquals(-0.220, p2.y, 1.0e-3);
		TestCase.assertEquals(0.150, mp2.getDepth(), 1.0e-3);
		
		// test overlap sat
		p.clear();
		m.clear();
		this.sat.detect(rect, t1, tri, t2, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, rect, t1, tri, t2, m));
		TestCase.assertEquals(2, m.getPoints().size());
		mp1 = m.getPoints().get(0);
		mp2 = m.getPoints().get(1);
		p1 = mp1.getPoint();
		p2 = mp2.getPoint();
		TestCase.assertEquals(-0.450, p1.x, 1.0e-3);
		TestCase.assertEquals(0.380, p1.y, 1.0e-3);
		TestCase.assertEquals(0.450, mp1.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.150, p2.x, 1.0e-3);
		TestCase.assertEquals(-0.220, p2.y, 1.0e-3);
		TestCase.assertEquals(0.150, mp2.getDepth(), 1.0e-3);
		
		// try reversing the shapes
		p.clear();
		m.clear();
		this.sat.detect(tri, t2, rect, t1, p);
		TestCase.assertTrue(this.cmfs.getManifold(p, tri, t2, rect, t1, m));
		TestCase.assertEquals(2, m.getPoints().size());
		mp1 = m.getPoints().get(0);
		mp2 = m.getPoints().get(1);
		p1 = mp1.getPoint();
		p2 = mp2.getPoint();
		TestCase.assertEquals(-0.450, p1.x, 1.0e-3);
		TestCase.assertEquals(0.380, p1.y, 1.0e-3);
		TestCase.assertEquals(0.450, mp1.getDepth(), 1.0e-3);
		TestCase.assertEquals(-0.150, p2.x, 1.0e-3);
		TestCase.assertEquals(-0.220, p2.y, 1.0e-3);
		TestCase.assertEquals(0.150, mp2.getDepth(), 1.0e-3);
	}
	
	/**
	 * This failure case is used to test the fix implemented.
	 * <p>
	 * The shapes, positions, and orientations in this test
	 * caused the GJK implementation to oscillate between 
	 * two different simplexes until the maximum iteration 
	 * count was hit returning an incorrect separation.
	 */
	@Test
	public void distanceFail1() {
		Rectangle r = new Rectangle(10.0, 0.2);
		Triangle t = new Triangle(
				new Vector2(0.0, 0.5),
				new Vector2(-1.0, 0.0),
				new Vector2(1.0, 0.0));
		
		Transform txr = new Transform();
		txr.rotate(0.10866660409637111939);
		txr.translate(-0.010447620194083662, 1.107709398935564);
		
		Transform txt = new Transform();
		txt.translate(0.0, 0.5);
		
		Separation s = new Separation();
		this.gjk.distance(r, txr, t, txt, s);
		
		Vector2 n = s.getNormal();
		Vector2 p1 = s.getPoint1();
		Vector2 p2 = s.getPoint2();
		TestCase.assertEquals(0.008, s.getDistance(), 1.0e-3);
		TestCase.assertEquals(0.108, n.x, 1.0e-3);
		TestCase.assertEquals(-0.994, n.y, 1.0e-3);
		TestCase.assertEquals(0.000, p1.x, 1.0e-3);
		TestCase.assertEquals(1.008, p1.y, 1.0e-3);
		TestCase.assertEquals(0.000, p2.x, 1.0e-3);
		TestCase.assertEquals(1.000, p2.y, 1.0e-3);
	}
}
