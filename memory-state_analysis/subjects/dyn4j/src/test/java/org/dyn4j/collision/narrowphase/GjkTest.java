/*
 * Copyright (c) 2010-2021 William Bittle  http://www.dyn4j.org/
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
package org.dyn4j.collision.narrowphase;

import org.dyn4j.geometry.Capsule;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Ellipse;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.HalfEllipse;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Ray;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Segment;
import org.dyn4j.geometry.Slice;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

/**
 * Test cases for the {@link Gjk} class (primarily raycast and getter/setters).
 * <p>
 * NOTE: Testing of the distance and detect methods are in the shape vs. shape
 * test case classes.
 * @author William Bittle
 * @version 4.1.0
 * @since 2.0.0
 */
public class GjkTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/**
	 * Tests creating the class and setting/getting values.
	 */
	@Test
	public void createGetSet() {
		Gjk gjk = new Gjk();
		
		TestCase.assertEquals(Epa.class, gjk.getMinkowskiPenetrationSolver().getClass());
		TestCase.assertEquals(Gjk.DEFAULT_DETECT_EPSILON, gjk.getDetectEpsilon());
		TestCase.assertEquals(Gjk.DEFAULT_DISTANCE_EPSILON, gjk.getDistanceEpsilon());
		TestCase.assertEquals(Gjk.DEFAULT_RAYCAST_EPSILON, gjk.getRaycastEpsilon());
		TestCase.assertEquals(Gjk.DEFAULT_MAX_ITERATIONS, gjk.getMaxDetectIterations());
		TestCase.assertEquals(Gjk.DEFAULT_MAX_ITERATIONS, gjk.getMaxDistanceIterations());
		TestCase.assertEquals(Gjk.DEFAULT_MAX_ITERATIONS, gjk.getMaxRaycastIterations());
		
		Epa epa = new Epa();
		gjk.setMinkowskiPenetrationSolver(epa);
		gjk.setDetectEpsilon(0.1);
		gjk.setDistanceEpsilon(0.2);
		gjk.setRaycastEpsilon(0.3);
		gjk.setMaxDetectIterations(6);
		gjk.setMaxDistanceIterations(7);
		gjk.setMaxRaycastIterations(8);
		
		TestCase.assertEquals(epa, gjk.getMinkowskiPenetrationSolver());
		TestCase.assertEquals(0.1, gjk.getDetectEpsilon());
		TestCase.assertEquals(0.2, gjk.getDistanceEpsilon());
		TestCase.assertEquals(0.3, gjk.getRaycastEpsilon());
		TestCase.assertEquals(6, gjk.getMaxDetectIterations());
		TestCase.assertEquals(7, gjk.getMaxDistanceIterations());
		TestCase.assertEquals(8, gjk.getMaxRaycastIterations());
		
		// test creation with epa
		gjk = new Gjk(epa);
		
		TestCase.assertEquals(epa, gjk.getMinkowskiPenetrationSolver());
	}
	
	/**
	 * Tests the creation of Gjk with a null penetration solver.
	 */
	@Test
	public void createWithNullMinkowskiPenetrationSolver() {
		assertThrows(NullPointerException.class, () -> {
			new Gjk(null);
		});

	}
	
	/**
	 * Tests the creation of Gjk and setting a null penetration solver.
	 */
	@Test
	public void nullMinkowskiPenetrationSolver() {
		assertThrows(NullPointerException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMinkowskiPenetrationSolver(null);
		});

	}
	
	/**
	 * Tests setting the detect epsilon to zero.
	 */
	@Test
	public void setZeroDetectEpsilon() {
		Gjk gjk = new Gjk();
		gjk.setDetectEpsilon(0.0);
	}
	
	/**
	 * Tests setting the detect epsilon to a negative value.
	 */
	@Test
	public void setNegativeDetectEpsilon() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setDetectEpsilon(-0.1);
		});

	}
	
	/**
	 * Tests setting the distance epsilon to zero.
	 */
	@Test
	public void setZeroDistanceEpsilon() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setDistanceEpsilon(0.0);
		});

	}
	
	/**
	 * Tests setting the distance epsilon to a negative value.
	 */
	@Test
	public void setNegativeDistanceEpsilon() {
		assertThrows(IllegalArgumentException.class, () -> {

			Gjk gjk = new Gjk();
			gjk.setDistanceEpsilon(-0.1);
		});

	}
	
	/**
	 * Tests setting the raycast epsilon to zero.
	 */
	@Test
	public void setZeroRaycastEpsilon() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setRaycastEpsilon(0.0);
		});

	}
	
	/**
	 * Tests setting the raycast epsilon to a negative value.
	 */
	@Test
	public void setNegativeRaycastEpsilon() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setRaycastEpsilon(-0.1);
		});

	}
	
	/**
	 * Tests setting the detect max iterations to a too low value.
	 */
	@Test
	public void setTooLowDetectMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxDetectIterations(4);
		});

	}
	
	/**
	 * Tests setting the detect max iterations to zero.
	 */
	@Test
	public void setZeroDetectMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxDetectIterations(0);
		});

	}
	
	/**
	 * Tests setting the detect max iterations to a negative value.
	 */
	@Test
	public void setNegativeDetectMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxDetectIterations(-5);
		});

	}
	
	/**
	 * Tests setting the distance max iterations to a too low value.
	 */
	@Test
	public void setTooLowDistanceMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxDistanceIterations(4);
		});

	}
	
	/**
	 * Tests setting the distance max iterations to zero.
	 */
	@Test
	public void setZeroDistanceMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxDistanceIterations(0);
		});

	}
	
	/**
	 * Tests setting the distance max iterations to a negative value.
	 */
	@Test
	public void setNegativeDistanceMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxDistanceIterations(-5);
		});

	}
	
	/**
	 * Tests setting the raycast max iterations to a too low value.
	 */
	@Test
	public void setTooLowRaycastMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxRaycastIterations(4);
		});

	}
	
	/**
	 * Tests setting the raycast max iterations to zero.
	 */
	@Test
	public void setZeroRaycastMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxRaycastIterations(0);
		});

	}
	
	/**
	 * Tests setting the raycast max iterations to a negative value.
	 */
	@Test
	public void setNegativeRaycastMaxIterations() {
		assertThrows(IllegalArgumentException.class, () -> {
			Gjk gjk = new Gjk();
			gjk.setMaxRaycastIterations(-5);
		});

	}
	
	/**
	 * Tests a successful raycast against a {@link Rectangle}.
	 */
	@Test
	public void raycastRectangle() {
		Ray ray = new Ray(new Vector2(), new Vector2(1.0, 0.0));
		Gjk gjk = new Gjk();
		Rectangle r = Geometry.createRectangle(1.0, 1.0);
		Transform t = new Transform();
		t.translate(2.0, 0.0);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, r, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(1.500, point.x, 1.0e-3);
		TestCase.assertEquals(0.000, point.y, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(1.500, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.4, r, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.6, r, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, r, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(1.0, 1.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, r, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(r.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, r, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Polygon}.
	 */
	@Test
	public void raycastPolygon() {
		Vector2 d = new Vector2(1.0, 1.0);
		d.normalize();
		Ray ray = new Ray(d);
		Gjk gjk = new Gjk();
		Convex c = Geometry.createUnitCirclePolygon(5, 1.0);
		Transform t = new Transform();
		t.translate(2.0, 1.0);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(1.190, point.x, 1.0e-3);
		TestCase.assertEquals(1.190, point.y, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(1.684, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.4, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.7, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(1.0, 2.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Triangle}.
	 */
	@Test
	public void raycastTriangle() {
		Vector2 d = new Vector2(2.0, 1.0);
		d.normalize();
		Ray ray = new Ray(d);
		Gjk gjk = new Gjk();
		Convex c = Geometry.createEquilateralTriangle(1.0);
		Transform t = new Transform();
		t.translate(2.0, 1.0);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(1.458, point.x, 1.0e-3);
		TestCase.assertEquals(0.729, point.y, 1.0e-3);
		TestCase.assertEquals(-0.866, normal.x, 1.0e-3);
		TestCase.assertEquals(0.5, normal.y, 1.0e-3);
		TestCase.assertEquals(1.631, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.4, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.7, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(1.0, 2.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Circle}.
	 */
	@Test
	public void raycastCircle() {
		Vector2 d = new Vector2(2.0, 1.0);
		Ray ray = new Ray(d);
		Gjk gjk = new Gjk();
		Convex c = Geometry.createCircle(0.5);
		Transform t = new Transform();
		t.translate(2.0, 0.5);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(1.599, point.x, 1.0e-3);
		TestCase.assertEquals(0.799, point.y, 1.0e-3);
		TestCase.assertEquals(-0.800, normal.x, 1.0e-3);
		TestCase.assertEquals(0.599, normal.y, 1.0e-3);
		TestCase.assertEquals(1.788, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.7, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.8, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.setDirection(new Vector2(1.0, 2.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.setStart(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// towards the ray, but missing the circle
		ray.setStart(new Vector2(0.0, 0.0));
		ray.setDirection(new Vector2(1.0, 1.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// towards the ray, but missing the circle because of length
		ray.setStart(new Vector2(0.0, 0.0));
		ray.setDirection(new Vector2(1.0, 0.5));
		TestCase.assertFalse(gjk.raycast(ray, 1.0, c, t, raycast));
		
		// towards the ray, but missing the circle because of length
		ray.setStart(new Vector2(0.0, 0.0));
		ray.setDirection(new Vector2(1.0, 0.5));
		TestCase.assertTrue(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// vertical ray
		ray.setStart(new Vector2(2.0, -1.0));
		ray.setDirection(new Vector2(0.0, 1.0));
		TestCase.assertTrue(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Ellipse}.
	 * @since 3.1.5
	 */
	@Test
	public void raycastEllipse() {
		Vector2 d = new Vector2(2.0, 1.0);
		d.normalize();
		Ray ray = new Ray(d);
		Gjk gjk = new Gjk();
		Convex c = new Ellipse(1.0, 0.5);
		Transform t = new Transform();
		t.translate(2.1, 1.0);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals( 1.699, point.x, 1.0e-3);
		TestCase.assertEquals( 0.849, point.y, 1.0e-3);
		TestCase.assertEquals(-0.548, normal.x, 1.0e-3);
		TestCase.assertEquals(-0.836, normal.y, 1.0e-3);
		TestCase.assertEquals( 1.900, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.7, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.95, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(1.0, 2.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link HalfEllipse}.
	 * @since 3.1.5
	 */
	@Test
	public void raycastHalfEllipse() {
		Ray ray = new Ray(new Vector2(Math.toRadians(130)));
		Gjk gjk = new Gjk();
		Convex c = new HalfEllipse(0.8, 0.25);
		Transform t = new Transform();
		t.translate(-1.0, 0.8);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(-0.671, point.x, 1.0e-3);
		TestCase.assertEquals( 0.800, point.y, 1.0e-3);
		TestCase.assertEquals( 0.000, normal.x, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.y, 1.0e-3);
		TestCase.assertEquals( 1.044, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 0.7, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.2, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(Math.toRadians(120)));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Slice}.
	 * @since 3.1.5
	 */
	@Test
	public void raycastSlice() {
		Ray ray = new Ray(new Vector2(Math.toRadians(-30)));
		Gjk gjk = new Gjk();
		Convex c = new Slice(0.5, Math.toRadians(60));
		Transform t = new Transform();
		t.translate(1.0, -0.8);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals( 1.192, point.x, 1.0e-3);
		TestCase.assertEquals(-0.688, point.y, 1.0e-3);
		TestCase.assertEquals(-0.500, normal.x, 1.0e-3);
		TestCase.assertEquals( 0.866, normal.y, 1.0e-3);
		TestCase.assertEquals( 1.377, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.3, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 1.5, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(Math.toRadians(-45)));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Capsule}.
	 * @since 3.1.5
	 */
	@Test
	public void raycastCapsule() {
		Ray ray = new Ray(new Vector2(Math.toRadians(-130)));
		Gjk gjk = new Gjk();
		Convex c = new Capsule(1.0, 0.5);
		Transform t = new Transform();
		t.translate(-1.0, -0.8);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(-0.547, point.x, 1.0e-3);
		TestCase.assertEquals(-0.652, point.y, 1.0e-3);
		TestCase.assertEquals( 0.816, normal.x, 1.0e-3);
		TestCase.assertEquals( 0.577, normal.y, 1.0e-3);
		TestCase.assertEquals( 0.852, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 0.7, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 0.9, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(Math.toRadians(-115)));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests a successful raycast against a {@link Segment}.
	 */
	@Test
	public void raycastSegment() {
		Vector2 d = new Vector2(2.0, 1.0);
		d.normalize();
		Ray ray = new Ray(d);
		Gjk gjk = new Gjk();
		Convex c = Geometry.createHorizontalSegment(1.0);
		Transform t = new Transform();
		t.translate(2.0, 1.0);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(2.000, point.x, 1.0e-3);
		TestCase.assertEquals(1.000, point.y, 1.0e-3);
		TestCase.assertEquals(0.000, normal.x, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.y, 1.0e-3);
		TestCase.assertEquals(2.236, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 2.2, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 2.4, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getDirectionVector().set(new Vector2(1.0, 2.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
	
	/**
	 * Tests the raycast method using a parallel segment.
	 */
	@Test
	public void raycastParallelSegment() {
		Vector2 d = new Vector2(1.0, 0.0); d.normalize();
		Ray ray = new Ray(d);
		Gjk gjk = new Gjk();
		Convex c = Geometry.createHorizontalSegment(1.0);
		Transform t = new Transform();
		t.translate(2.0, 0.0);
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = gjk.raycast(ray, 0.0, c, t, raycast);
		
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(1.500, point.x, 1.0e-3);
		TestCase.assertEquals(0.000, point.y, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(1.500, raycast.getDistance(), 1.0e-3);
		raycast.clear();
		
		// length test
		TestCase.assertFalse(gjk.raycast(ray, 1.4, c, t, raycast));
		TestCase.assertTrue(gjk.raycast(ray, 2.0, c, t, raycast));
		
		// opposite direction test
		ray.getDirectionVector().negate();
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// non-intersection case
		ray.getStart().set(new Vector2(0.0, 1.0));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
		
		// start at center case (or any point within the convex shape)
		ray.getStart().set(t.getTransformed(c.getCenter()));
		TestCase.assertFalse(gjk.raycast(ray, 0.0, c, t, raycast));
	}
}
