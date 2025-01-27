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

import java.util.ArrayList;
import java.util.List;

import org.dyn4j.geometry.Capsule;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Ellipse;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.HalfEllipse;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Triangle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

/**
 * Test case for the {@link FallbackNarrowphaseDetector} class.
 * @author William Bittle
 * @version 4.1.0
 * @since 3.1.5
 */
public class FallbackNarrowphaseDetectorTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** A listing of shape types */
	private static final Convex[] TYPES = new Convex[] {
		// Capsule
		Geometry.createCapsule(1.0, 0.5),
		// Circle
		Geometry.createCircle(0.5),
		// Ellipse
		Geometry.createEllipse(1.0, 0.5),
		// HalfEllipse
		Geometry.createHalfEllipse(1.0, 0.25),
		// Segment
		Geometry.createHorizontalSegment(1.0),
		// Rectangle
		Geometry.createRectangle(1.0, 2.0),
		// Slice
		Geometry.createSlice(0.5, Math.toRadians(50)),
		// Polygon
		Geometry.createUnitCirclePolygon(5, 0.5),
		// Triangle
		Geometry.createEquilateralTriangle(1.0)
	};
	
	/**
	 * Tests the successful creation of the detector.
	 */
	@Test
	public void createSuccess() {
		NarrowphaseDetector np1 = new Sat();
		NarrowphaseDetector np2 = new Gjk();
		List<FallbackCondition> conditions = new ArrayList<FallbackCondition>();
		FallbackNarrowphaseDetector nd = new FallbackNarrowphaseDetector(np1, np2, conditions);
		
		TestCase.assertEquals(np1, nd.getPrimaryNarrowphaseDetector());
		TestCase.assertEquals(np2, nd.getFallbackNarrowphaseDetector());
		TestCase.assertSame(conditions, nd.fallbackConditions);
	}
	
	/**
	 * Tests the creation of the detector with a null primary.
	 */
	@Test
	public void createNullPrimary() {
		assertThrows(NullPointerException.class, () -> {
			new FallbackNarrowphaseDetector(null, new Gjk());
		});

	}
	
	/**
	 * Tests the creation of the detector with a null secondary.
	 */
	@Test
	public void createNullSecondary() {
		assertThrows(NullPointerException.class, () -> {
			new FallbackNarrowphaseDetector(new Sat(), null);
		});

	}
	
	/**
	 * Test that no fallback is done if no conditions are added.
	 */
	@Test
	public void noConditions() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				TestCase.assertFalse(detector.isFallbackRequired(TYPES[i], TYPES[j]));
			}
		}
		
		TestCase.assertEquals(0, detector.getConditionCount());
	}
	
	/**
	 * Tests adding and removing a fallback condition.
	 */
	@Test
	public void addRemoveCondition() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		FallbackCondition condition = new SingleTypedFallbackCondition(Capsule.class);
		detector.addCondition(condition);
		TestCase.assertTrue(detector.containsCondition(condition));
		TestCase.assertEquals(1, detector.getConditionCount());
		TestCase.assertSame(condition, detector.getCondition(0));
		
		// verify the equals is working as expected
		TestCase.assertFalse(condition.equals(new SingleTypedFallbackCondition(Capsule.class)));
		TestCase.assertFalse(condition.equals(new SingleTypedFallbackCondition(Capsule.class, false)));
		TestCase.assertFalse(condition.equals(new SingleTypedFallbackCondition(Capsule.class, 1)));
		
		TestCase.assertTrue(detector.removeCondition(condition));
		TestCase.assertEquals(0, detector.getConditionCount());
		TestCase.assertFalse(detector.containsCondition(condition));
	}
	
	/**
	 * Test that fallback occurs for a type.
	 */
	@Test
	public void singleTypedCondition() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		detector.addCondition(new SingleTypedFallbackCondition(Ellipse.class));
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				boolean fallback = detector.isFallbackRequired(TYPES[i], TYPES[j]);
				if (TYPES[i] instanceof Ellipse || TYPES[j] instanceof Ellipse) {
					// any combo with an ellipse should fallback
					TestCase.assertTrue(fallback);
				} else {
					// all other combos shouldn't
					TestCase.assertFalse(fallback);
				}
			}
		}
	}
	
	/**
	 * Test that fallback occurs for a type using strict mode.
	 */
	@Test
	public void singleTypedConditionStrict() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		// try strict mode
		detector.addCondition(new SingleTypedFallbackCondition(Polygon.class, true));
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				boolean fallback = detector.isFallbackRequired(TYPES[i], TYPES[j]);
				if (TYPES[i].getClass() == Polygon.class || TYPES[j].getClass() == Polygon.class) {
					// any combo with specifically a Polygon (so not Rectangle, Triangle, etc. since
					// we are using strict mode) should fallback
					TestCase.assertTrue(fallback);
				} else {
					// all other combos shouldn't
					TestCase.assertFalse(fallback);
				}
			}
		}
	}
	
	/**
	 * Test that fallback checking based on order.
	 */
	@Test
	public void singleTypedConditionOrder() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		// try strict mode
		FallbackCondition c1 = new SingleTypedFallbackCondition(Polygon.class, 1, true);
		FallbackCondition c2 = new SingleTypedFallbackCondition(Rectangle.class, 0, true);
		detector.addCondition(c1);
		detector.addCondition(c2);
		
		// make sure the sorting works
		TestCase.assertSame(detector.getCondition(0), c2);
		
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				boolean fallback = detector.isFallbackRequired(TYPES[i], TYPES[j]);
				if (TYPES[i].getClass() == Polygon.class || TYPES[j].getClass() == Polygon.class ||
					TYPES[i].getClass() == Rectangle.class || TYPES[j].getClass() == Rectangle.class) {
					// any combo with specifically a Polygon (so not Rectangle, Triangle, etc. since
					// we are using strict mode) should fallback
					TestCase.assertTrue(fallback);
				} else {
					// all other combos shouldn't
					TestCase.assertFalse(fallback);
				}
			}
		}
	}

	/**
	 * Test that fallback occurs for a type pair.
	 */
	@Test
	public void pairwiseTypedCondition() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		detector.addCondition(new PairwiseTypedFallbackCondition(Ellipse.class, Ellipse.class));
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				boolean fallback = detector.isFallbackRequired(TYPES[i], TYPES[j]);
				if (TYPES[i] instanceof Ellipse && TYPES[j] instanceof Ellipse) {
					// if both are ellipses then they should fallback
					TestCase.assertTrue(fallback);
				} else {
					// all other combos shouldn't
					TestCase.assertFalse(fallback);
				}
			}
		}
	}
	
	/**
	 * Test that fallback occurs for a type pair.
	 */
	@Test
	public void pairwiseTypedConditionStrict() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		detector.addCondition(new PairwiseTypedFallbackCondition(Polygon.class, Ellipse.class, true));
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				boolean fallback = detector.isFallbackRequired(TYPES[i], TYPES[j]);
				if ((TYPES[i].getClass() == Polygon.class && TYPES[j] instanceof Ellipse) ||
					(TYPES[i] instanceof Ellipse && TYPES[j].getClass() == Polygon.class)) {
					// order doesn't matter
					TestCase.assertTrue(fallback);
				} else {
					// all other combos shouldn't
					TestCase.assertFalse(fallback);
				}
			}
		}
	}
	
	/**
	 * Test that fallback occurs for a type pair with one type strict and the other not.
	 */
	@Test
	public void pairwiseTypedConditionStrictOne() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		// must be Polygon vs Polygon or subclass or Polygon or subclass vs Polygon
		detector.addCondition(new PairwiseTypedFallbackCondition(Polygon.class, true, Polygon.class, false));
		// try all combos
		for (int i = 0; i < TYPES.length; i++) {
			for (int j = i; j < TYPES.length; j++) {
				boolean fallback = detector.isFallbackRequired(TYPES[i], TYPES[j]);
				if ((TYPES[i].getClass() == Polygon.class && TYPES[j] instanceof Polygon) ||
					(TYPES[i] instanceof Polygon && TYPES[j].getClass() == Polygon.class)) {
					// order doesn't matter
					TestCase.assertTrue(fallback);
				} else {
					// all other combos shouldn't
					TestCase.assertFalse(fallback);
				}
			}
		}
	}
	
	/**
	 * Test the detect method with conditions.
	 */
	@Test
	public void detect() {
		FallbackNarrowphaseDetector detector = new FallbackNarrowphaseDetector(new Sat(), new Gjk());
		detector.addCondition(new SingleTypedFallbackCondition(Ellipse.class));
		detector.addCondition(new SingleTypedFallbackCondition(HalfEllipse.class));
		
		Ellipse e = Geometry.createEllipse(1, 0.4);
		HalfEllipse he = Geometry.createHalfEllipse(1, 0.5);
		Rectangle r = Geometry.createSquare(1);
		Transform tx = new Transform();
		
		// the detect method should throw an exception if the fallback condition doesn't
		// execute appropriately because SAT doesn't support Ellipse or HalfEllipse
		TestCase.assertTrue(detector.detect(e, tx, r, tx));
		TestCase.assertTrue(detector.detect(r, tx, e, tx));
		TestCase.assertTrue(detector.detect(he, tx, r, tx));
		TestCase.assertTrue(detector.detect(r, tx, he, tx));
		
		Penetration p = new Penetration();
		TestCase.assertTrue(detector.detect(e, tx, r, tx, p));
		TestCase.assertTrue(detector.detect(r, tx, e, tx, p));
		TestCase.assertTrue(detector.detect(he, tx, r, tx, p));
		TestCase.assertTrue(detector.detect(r, tx, he, tx, p));
		
		// test no fallback needed
		Triangle t = Geometry.createEquilateralTriangle(0.5);
		
		TestCase.assertTrue(detector.detect(t, tx, r, tx));
		TestCase.assertTrue(detector.detect(r, tx, t, tx));
		
		TestCase.assertTrue(detector.detect(t, tx, r, tx, p));
		TestCase.assertTrue(detector.detect(r, tx, t, tx, p));
	}
}
