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

import org.dyn4j.geometry.Ray;
import org.dyn4j.geometry.Segment;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Test case for the {@link SegmentDetector} class
 * @author William Bittle
 * @version 4.1.0
 * @since 3.4.0
 */
public class SegmentDetectorTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/**
	 * Tests the non-intersection case of raycasting a hoizontal segment.
	 */
	@Test
	public void raycastHorizontalSegmentNoIntersection() {
		Ray ray = new Ray(new Vector2(-0.85, 0.48), Math.PI * 0.25);
		Segment c = new Segment(new Vector2(-0.59, 0.68), new Vector2(-0.40, 0.68));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		
		// should not intersect
		TestCase.assertFalse(collision);
		TestCase.assertEquals(raycast.getNormal().x, 0.0);
		TestCase.assertEquals(raycast.getNormal().y, 0.0);
		TestCase.assertEquals(raycast.getPoint().x, 0.0);
		TestCase.assertEquals(raycast.getPoint().y, 0.0);
		TestCase.assertEquals(0.0, raycast.getDistance());
	}
	
	/**
	 * Tests the intersection case of raycasting a hoizontal segment.
	 */
	@Test
	public void raycastHorizontalSegmentWithIntersection() {
		Ray ray = new Ray(new Vector2(-0.85, 0.48), Math.PI * 0.25);
		Segment c = new Segment(new Vector2(-0.68, 0.68), new Vector2(-0.53, 0.68));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		
		// should intersect
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(-0.649, point.x, 1.0e-3);
		TestCase.assertEquals(0.680, point.y, 1.0e-3);
		TestCase.assertEquals(0.000, normal.x, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.y, 1.0e-3);
		TestCase.assertEquals(0.282, raycast.getDistance(), 1.0e-3);
	}
	
	/**
	 * Tests the non-intersection case of raycasting a vertical segment.
	 */
	@Test
	public void raycastVerticalSegmentNoIntersection() {
		Ray ray = new Ray(new Vector2(-0.85, 0.48), Math.PI * 0.25);
		Segment c = new Segment(new Vector2(-0.58, 0.68), new Vector2(-0.58, 0.41));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		
		// should not intersect
		TestCase.assertFalse(collision);
		TestCase.assertEquals(raycast.getNormal().x, 0.0);
		TestCase.assertEquals(raycast.getNormal().y, 0.0);
		TestCase.assertEquals(raycast.getPoint().x, 0.0);
		TestCase.assertEquals(raycast.getPoint().y, 0.0);
		TestCase.assertEquals(0.0, raycast.getDistance());
	}
	
	/**
	 * Tests the intersection case of raycasting a vertical segment.
	 */
	@Test
	public void raycastVerticalSegmentWithIntersection() {
		Ray ray = new Ray(new Vector2(-0.85, 0.48), Math.PI * 0.25);
		Segment c = new Segment(new Vector2(-0.58, 1.2), new Vector2(-0.58, 0.41));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		// successful test
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		
		// should intersect
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		
		TestCase.assertEquals(-0.58, point.x, 1.0e-3);
		TestCase.assertEquals(0.75, point.y, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(0.381, raycast.getDistance(), 1.0e-3);
	}
	
	/**
	 * Tests the non-intersection case where ray is in front of the segment.
	 */
	@Test
	public void raycastParallelSegmentBehindRay() {
		Ray ray = new Ray(new Vector2(3.0, 0.0), new Vector2(1.0, 0.0));
		Segment c = new Segment(new Vector2(0.0, 0.0), new Vector2(2.0, 0.0));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// try the other way
		ray = new Ray(new Vector2(-3.0, 0.0), new Vector2(-1.0, 0.0));
		c = new Segment(new Vector2(0.0, 0.0), new Vector2(-2.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// try reversed winding
		c = new Segment(new Vector2(-2.0, 0.0), new Vector2(0.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
	}

	/**
	 * Tests the intersection case where ray is in front of (and parallel to) the segment.
	 */
	@Test
	public void raycastParallelSegmentInFrontOfRay() {
		Ray ray = new Ray(new Vector2(-1.0, 0.0), new Vector2(1.0, 0.0));
		Segment c = new Segment(new Vector2(0.0, 0.0), new Vector2(2.0, 0.0));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertTrue(collision);
		
		Vector2 point = raycast.getPoint();
		Vector2 normal = raycast.getNormal();
		TestCase.assertEquals(0.0, point.x, 1.0e-3);
		TestCase.assertEquals(0.0, point.y, 1.0e-3);
		TestCase.assertEquals(-1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(1.000, raycast.getDistance(), 1.0e-3);
		
		// try the other way
		ray = new Ray(new Vector2(3.0, 0.0), new Vector2(-1.0, 0.0));
		c = new Segment(new Vector2(0.0, 0.0), new Vector2(2.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertTrue(collision);
		
		point = raycast.getPoint();
		normal = raycast.getNormal();
		TestCase.assertEquals(2.0, point.x, 1.0e-3);
		TestCase.assertEquals(0.0, point.y, 1.0e-3);
		TestCase.assertEquals(1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(1.000, raycast.getDistance(), 1.0e-3);
		
		// try reversed winding
		c = new Segment(new Vector2(2.0, 0.0), new Vector2(0.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertTrue(collision);
		
		point = raycast.getPoint();
		normal = raycast.getNormal();
		TestCase.assertEquals(2.0, point.x, 1.0e-3);
		TestCase.assertEquals(0.0, point.y, 1.0e-3);
		TestCase.assertEquals(1.000, normal.x, 1.0e-3);
		TestCase.assertEquals(0.000, normal.y, 1.0e-3);
		TestCase.assertEquals(1.000, raycast.getDistance(), 1.0e-3);
	}
	
	/**
	 * Tests the non-intersection case where ray is starting in the middle of the segment.
	 */
	@Test
	public void raycastWithRayStartingInsideParallelSegment() {
		Ray ray = new Ray(new Vector2(1.0, 0.0), new Vector2(1.0, 0.0));
		Segment c = new Segment(new Vector2(0.0, 0.0), new Vector2(2.0, 0.0));
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// reverse the ray
		ray = new Ray(new Vector2(1.0, 0.0), new Vector2(-1.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// try the other way
		ray = new Ray(new Vector2(-1.0, 0.0), new Vector2(-1.0, 0.0));
		c = new Segment(new Vector2(0.0, 0.0), new Vector2(-2.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// reverse the ray
		ray = new Ray(new Vector2(-1.0, 0.0), new Vector2(1.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// try reversed winding
		c = new Segment(new Vector2(-2.0, 0.0), new Vector2(0.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
		// reverse the ray
		ray = new Ray(new Vector2(-1.0, 0.0), new Vector2(-1.0, 0.0));
		
		collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
		
	}
	
	/**
	 * Tests a degenerate line segment.
	 */
	@Test
	public void raycastDegenerateSegment() {
		Ray ray = new Ray(new Vector2(1.0, 0.0), new Vector2(1.0, 0.0));
		Segment c = new Segment(new Vector2(1.0, 0.0), new Vector2(0.0, 0.0));
		// NOTE: shouldn't be possible to create a degenerate line segement
		c.getPoint1().set(0.0, 0.0);
		
		Transform t = new Transform();
		Raycast raycast = new Raycast();
		
		boolean collision = SegmentDetector.raycast(ray, 0.0, c, t, raycast);
		TestCase.assertFalse(collision);
	}
}
