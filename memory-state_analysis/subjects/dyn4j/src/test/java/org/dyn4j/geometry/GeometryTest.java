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
package org.dyn4j.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

/**
 * Test case for the {@link Geometry} class.
 * @author William Bittle
 * @version 4.2.2
 * @since 1.0.0
 */
public class GeometryTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/**
	 * Tests the getAverageCenter method.
	 * <p>
	 * This test also shows that the average method can produce an incorrect
	 * center of mass when vertices are more dense at any place along the perimeter.
	 */
	@Test
	public void getAverageCenterArray() {
		Vector2[] vertices = new Vector2[6];
		vertices[0] = new Vector2(-2.0, 1.0);
		vertices[1] = new Vector2(-1.0, 2.0);
		vertices[2] = new Vector2(1.2, 0.5);
		vertices[3] = new Vector2(1.3, 0.3);
		vertices[4] = new Vector2(1.4, 0.2);
		vertices[5] = new Vector2(0.0, -1.0);
		
		Vector2 c = Geometry.getAverageCenter(vertices);
		
		TestCase.assertEquals(0.150, c.x, 1.0e-3);
		TestCase.assertEquals(0.500, c.y, 1.0e-3);
		
		c = Geometry.getAverageCenter(vertices[0]);
		
		TestCase.assertEquals(-2.000, c.x, 1.0e-3);
		TestCase.assertEquals( 1.000, c.y, 1.0e-3);
	}
	
	/**
	 * Tests the getAverageCenter method.
	 * <p>
	 * This test also shows that the average method can produce an incorrect
	 * center of mass when vertices are more dense at any place along the perimeter.
	 * @since 3.1.0
	 */
	@Test
	public void getAverageCenterList() {
		List<Vector2> vertices = new ArrayList<Vector2>();
		vertices.add(new Vector2(-2.0, 1.0));
		vertices.add(new Vector2(-1.0, 2.0));
		vertices.add(new Vector2(1.2, 0.5));
		vertices.add(new Vector2(1.3, 0.3));
		vertices.add(new Vector2(1.4, 0.2));
		vertices.add(new Vector2(0.0, -1.0));
		
		Vector2 c = Geometry.getAverageCenter(vertices);
		
		TestCase.assertEquals(0.150, c.x, 1.0e-3);
		TestCase.assertEquals(0.500, c.y, 1.0e-3);
		
		c = Geometry.getAverageCenter(vertices.subList(0, 1));
		
		TestCase.assertEquals(-2.000, c.x, 1.0e-3);
		TestCase.assertEquals( 1.000, c.y, 1.0e-3);
	}
	
	/**
	 * Tests the getAverageCenter method passing a null array.
	 * @since 2.0.0
	 */
	@Test
	public void getAverageCenterNullArray() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAverageCenter((Vector2[]) null);
		});

	}
	
	/**
	 * Tests the getAverageCenter method passing an empty array.
	 * @since 3.1.0
	 */
	@Test
	public void getAverageCenterEmptyArray() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.getAverageCenter(new Vector2[] {});
		});

	}
	
	/**
	 * Tests the getAverageCenter method passing an array with null elements.
	 * @since 3.1.0
	 */
	@Test
	public void getAverageCenterArrayNullElements() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAverageCenter(new Vector2[] {
					new Vector2(1.0, 0.0),
					null,
					new Vector2(4.0, 3.0),
					new Vector2(-2.0, -1.0),
					null
			});
		});

	}
	
	/**
	 * Tests the getAverageCenter method passing an array with null elements.
	 * @since 4.1.0
	 */
	@Test
	public void getAverageCenterArrayNullOnlyElement() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAverageCenter(new Vector2[] {
					null
			});
		});

	}
	
	/**
	 * Tests the getAverageCenter method passing a null list.
	 * @since 2.0.0
	 */
	@Test
	public void getAverageCenterNullList() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAverageCenter((List<Vector2>) null);
		});


	}
	
	/**
	 * Tests the getAverageCenter method passing an empty list.
	 * @since 2.0.0
	 */
	@Test
	public void getAverageCenterEmptyList() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.getAverageCenter(new ArrayList<Vector2>());
		});

	}
	
	/**
	 * Tests the getAverageCenter method passing an array with null elements.
	 * @since 3.1.0
	 */
	@Test
	public void getAverageCenterListNullElements() {
		assertThrows(NullPointerException.class, () -> {
			List<Vector2> vertices = new ArrayList<Vector2>();
			vertices.add(new Vector2(0.0, -1.0));
			vertices.add(null);
			vertices.add(new Vector2(2.0, -1.0));
			vertices.add(new Vector2(-3.0, -1.0));
			vertices.add(null);
			Geometry.getAverageCenter(vertices);
		});

	}

	/**
	 * Tests the getAverageCenter method passing an array with null elements.
	 * @since 4.1.0
	 */
	@Test
	public void getAverageCenterListNullOnlyElement() {
		assertThrows(NullPointerException.class, () -> {
			List<Vector2> vertices = new ArrayList<Vector2>();
			vertices.add(null);
			Geometry.getAverageCenter(vertices);
		});

	}
	
	/**
	 * Tests the getAreaWeightedCenter method.
	 */
	@Test
	public void getAreaWeightedCenter() {
		Vector2[] vertices = new Vector2[6];
		vertices[0] = new Vector2(-2.0, 1.0);
		vertices[1] = new Vector2(-1.0, 2.0);
		// test dense area of points
		vertices[2] = new Vector2(1.2, 0.5);
		vertices[3] = new Vector2(1.3, 0.3);
		vertices[4] = new Vector2(1.4, 0.2);
		vertices[5] = new Vector2(0.0, -1.0);
		
		Vector2 c = Geometry.getAreaWeightedCenter(vertices);
		
		// note the x is closer to the "real" center of the object
		TestCase.assertEquals(-0.318, c.x, 1.0e-3);
		TestCase.assertEquals( 0.527, c.y, 1.0e-3);
		
		c = Geometry.getAreaWeightedCenter(Arrays.asList(vertices));
		
		// note the x is closer to the "real" center of the object
		TestCase.assertEquals(-0.318, c.x, 1.0e-3);
		TestCase.assertEquals( 0.527, c.y, 1.0e-3);
	}
	
	/**
	 * Tests the getAreaWeightedCenter method with a polygon that is not centered
	 * about the origin.
	 * @since 3.1.4
	 */
	@Test
	public void getAreaWeightedCenterOffset() {
		Vector2[] vertices = new Vector2[6];
		vertices[0] = new Vector2(-1.0, 2.0);
		vertices[1] = new Vector2(0.0, 3.0);
		// test dense area of points
		vertices[2] = new Vector2(2.2, 1.5);
		vertices[3] = new Vector2(2.3, 1.3);
		vertices[4] = new Vector2(2.4, 1.2);
		vertices[5] = new Vector2(1.0, 0.0);
		
		Vector2 c = Geometry.getAreaWeightedCenter(vertices);
		
		// note the x is closer to the "real" center of the object
		TestCase.assertEquals(0.682, c.x, 1.0e-3);
		TestCase.assertEquals(1.527, c.y, 1.0e-3);
		
		c = Geometry.getAreaWeightedCenter(Arrays.asList(vertices));
		
		// note the x is closer to the "real" center of the object
		TestCase.assertEquals(0.682, c.x, 1.0e-3);
		TestCase.assertEquals(1.527, c.y, 1.0e-3);
	}

	/**
	 * Tests the getAreaWeightedCenter method passing a null array.
	 * @since 2.0.0
	 */
	@Test
	public void getAreaWeightedCenterNullArray() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAreaWeightedCenter((Vector2[]) null);
		});

	}

	/**
	 * Tests the getAreaWeightedCenter method passing an empty array.
	 * @since 3.1.0
	 */
	@Test
	public void getAreaWeightedCenterEmptyArray() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.getAreaWeightedCenter(new Vector2[] {});
		});

	}

	/**
	 * Tests the getAreaWeightedCenter method passing an array with null elements.
	 * @since 3.1.0
	 */
	@Test
	public void getAreaWeightedCenterArrayNullElements() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAreaWeightedCenter(new Vector2[] {
					new Vector2(1.0, 0.0),
					null,
					new Vector2(4.0, 3.0),
					new Vector2(-2.0, -1.0),
					null
			});
		});

	}
	
	/**
	 * Tests the getAreaWeightedCenter method passing a null list.
	 * @since 2.0.0
	 */
	@Test
	public void getAreaWeightedCenterNullList() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getAreaWeightedCenter((List<Vector2>) null);
		});

	}
	
	/**
	 * Tests the getAreaWeightedCenter method passing an empty list.
	 * @since 2.0.0
	 */
	@Test
	public void getAreaWeightedCenterEmptyList() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.getAreaWeightedCenter(new ArrayList<Vector2>());
		});

	}
	
	/**
	 * Tests the getAreaWeightedCenter method passing an list with null elements.
	 * @since 3.1.0
	 */
	@Test
	public void getAreaWeightedCenterListNullElements() {
		assertThrows(NullPointerException.class, () -> {
			List<Vector2> vertices = new ArrayList<Vector2>();
			vertices.add(new Vector2(0.0, -1.0));
			vertices.add(null);
			vertices.add(new Vector2(2.0, -1.0));
			vertices.add(new Vector2(-3.0, -1.0));
			vertices.add(null);
			Geometry.getAreaWeightedCenter(vertices);
		});

	}
	
	/**
	 * Tests the getAreaWeightedCenter method passing a list of
	 * points who are all the same yielding zero area.
	 * @since 2.0.0
	 */
	@Test
	public void getAreaWeightedCenterZeroAreaList() {
		List<Vector2> points = new ArrayList<Vector2>();
		points.add(new Vector2(2.0, 1.0));
		points.add(new Vector2(2.0, 1.0));
		points.add(new Vector2(2.0, 1.0));
		points.add(new Vector2(2.0, 1.0));
		
		Vector2 c = Geometry.getAreaWeightedCenter(points);
		
		TestCase.assertEquals(2.000, c.x, 1.0e-3);
		TestCase.assertEquals(1.000, c.y, 1.0e-3);
	}
	
	/**
	 * Tests the getAreaWeightedCenter method passing a list of
	 * points who are all the same yielding zero area.
	 * @since 2.0.0
	 */
	@Test
	public void getAreaWeightedCenterZeroAreaArray() {
		Vector2[] points = new Vector2[4];
		points[0] = new Vector2(2.0, 1.0);
		points[1] = new Vector2(2.0, 1.0);
		points[2] = new Vector2(2.0, 1.0);
		points[3] = new Vector2(2.0, 1.0);
		
		Vector2 c = Geometry.getAreaWeightedCenter(points);
		
		TestCase.assertEquals(2.000, c.x, 1.0e-3);
		TestCase.assertEquals(1.000, c.y, 1.0e-3);
	}
	
	/**
	 * Test case for the unitCirclePolygon methods.
	 * @since 3.1.0
	 */
	@Test
	public void createUnitCirclePolygon() {
		Polygon p = Geometry.createUnitCirclePolygon(5, 0.5);
		// no exception indicates the generated polygon is valid
		// test that the correct vertices are created
		TestCase.assertEquals( 0.154, p.vertices[4].x, 1.0e-3);
		TestCase.assertEquals(-0.475, p.vertices[4].y, 1.0e-3);
		TestCase.assertEquals(-0.404, p.vertices[3].x, 1.0e-3);
		TestCase.assertEquals(-0.293, p.vertices[3].y, 1.0e-3);
		TestCase.assertEquals(-0.404, p.vertices[2].x, 1.0e-3);
		TestCase.assertEquals( 0.293, p.vertices[2].y, 1.0e-3);
		TestCase.assertEquals( 0.154, p.vertices[1].x, 1.0e-3);
		TestCase.assertEquals( 0.475, p.vertices[1].y, 1.0e-3);
		TestCase.assertEquals( 0.500, p.vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 0.000, p.vertices[0].y, 1.0e-3);
		
		Vector2 v11 = p.vertices[0];
		
		p = Geometry.createUnitCirclePolygon(5, 0.5, Math.PI / 2.0);
		// no exception indicates the generated polygon is valid
		// test that the correct vertices are created
		TestCase.assertEquals( 0.475, p.vertices[4].x, 1.0e-3);
		TestCase.assertEquals( 0.154, p.vertices[4].y, 1.0e-3);
		TestCase.assertEquals( 0.293, p.vertices[3].x, 1.0e-3);
		TestCase.assertEquals(-0.404, p.vertices[3].y, 1.0e-3);
		TestCase.assertEquals(-0.293, p.vertices[2].x, 1.0e-3);
		TestCase.assertEquals(-0.404, p.vertices[2].y, 1.0e-3);
		TestCase.assertEquals(-0.475, p.vertices[1].x, 1.0e-3);
		TestCase.assertEquals( 0.154, p.vertices[1].y, 1.0e-3);
		TestCase.assertEquals( 0.000, p.vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 0.500, p.vertices[0].y, 1.0e-3);
		
		Vector2 v21 = p.vertices[0];
		
		// the angle between any two vertices of the two polygons should be PI / 2
		double angle = v11.getAngleBetween(v21);
		TestCase.assertEquals(Math.PI / 2.0, angle, 1.0e-3);
	}
	
	/**
	 * Tests the failed creation of a negative radius unit circle polygon.
	 * @since 3.1.0
	 */
	@Test
	public void createNegativeRadiusUnitCirclePolygon() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createUnitCirclePolygon(5, -0.5);
		});

	}

	/**
	 * Tests the failed creation of a zero radius unit circle polygon.
	 * @since 3.1.0
	 */
	@Test
	public void createZeroRadiusUnitCirclePolygon() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createUnitCirclePolygon(5, 0.0);
		});

	}

	/**
	 * Tests the failed creation of a unit circle polygon with less than 3 points.
	 * @since 3.1.0
	 */
	@Test
	public void createLessThan3PointsUnitCirclePolygon() {
		assertThrows(IllegalArgumentException.class, () -> {

			Geometry.createUnitCirclePolygon(2, 0.5);
		});

	}
	
	/**
	 * Tests the successful creation of a circle.
	 */
	@Test
	public void createCircle() {
		Geometry.createCircle(1.0);
	}
	
	/**
	 * Tests the failed creation of a circle using a negative radius.
	 * @since 3.1.0
	 */
	@Test
	public void createNegativeRadiusCircle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createCircle(-1.0);
		});

	}

	/**
	 * Tests the failed creation of a circle using a zero radius.
	 * @since 3.1.0
	 */
	@Test
	public void createZeroRadiusCircle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createCircle(0.0);
		});

	}
	
	/**
	 * Tests the creation of a polygon with a null array.
	 */
	@Test
	public void createPolygonNullArray() {
		assertThrows(NullPointerException.class, () -> {
			Vector2[] vertices = null;
			// should fail since the vertices list contains null items
			Geometry.createPolygon(vertices);
		});

	}
	
	/**
	 * Tests the creation of a polygon with a null point.
	 */
	@Test
	public void createPolygonNullPoint() {
		assertThrows(NullPointerException.class, () -> {
			Vector2[] vertices = new Vector2[5];
			// should fail since the vertices list contains null items
			Geometry.createPolygon(vertices);
		});

	}
	
	/**
	 * Tests the successful creation of a polygon using vertices.
	 */
	@Test
	public void createPolygon() {
		Vector2[] vertices = new Vector2[5];
		vertices[0] = new Vector2(1.0, 0.0);
		vertices[1] = new Vector2(0.5, 1.0);
		vertices[2] = new Vector2(-0.5, 1.0);
		vertices[3] = new Vector2(-1.0, 0.0);
		vertices[4] = new Vector2(0.0, -1.0);
		// should fail since the vertices list contains null items
		Polygon p = Geometry.createPolygon(vertices);
		
		// the array should not be the same object
		TestCase.assertFalse(p.vertices == vertices);
		// the points should also be copies
		for (int i = 0; i < 5; i++) {
			TestCase.assertFalse(p.vertices[0] == vertices[0]);
		}
	}
	
	/**
	 * Tests the successful creation of a polygon using vertices.
	 */
	@Test
	public void createPolygonAtOrigin() {
		Vector2[] vertices = new Vector2[5];
		vertices[0] = new Vector2(1.0, 0.0);
		vertices[1] = new Vector2(0.5, 1.0);
		vertices[2] = new Vector2(-0.5, 1.0);
		vertices[3] = new Vector2(-1.0, 0.0);
		vertices[4] = new Vector2(0.0, -1.0);
		// should fail since the vertices list contains null items
		Polygon p = Geometry.createPolygonAtOrigin(vertices);
		
		// the array should not be the same object
		TestCase.assertFalse(p.vertices == vertices);
		// the points should also be copies
		for (int i = 0; i < 5; i++) {
			TestCase.assertFalse(p.vertices[0] == vertices[0]);
		}
		
		// make sure the center is at the origin
		Vector2 c = p.getCenter();
		TestCase.assertEquals(0.000, c.x, 1.0e-3);
		TestCase.assertEquals(0.000, c.y, 1.0e-3);
	}
	
	/**
	 * Tests the creation of a square with a zero size.
	 */
	@Test
	public void createZeroSizeSquare() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createSquare(0.0);
		});

	}
	
	/**
	 * Tests the creation of a square with a negative size.
	 */
	@Test
	public void createNegativeSizeSquare() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createSquare(-1.0);
		});

	}
	
	/**
	 * Tests the successful creation of a square.
	 */
	@Test
	public void createSquare() {
		Rectangle r = Geometry.createSquare(1.0);
		TestCase.assertEquals(1.000, r.getWidth(), 1.0e-3);
		TestCase.assertEquals(1.000, r.getHeight(), 1.0e-3);
	}
	
	/**
	 * Tests the successful creation of a rectangle.
	 */
	@Test
	public void createRectangle() {
		Geometry.createRectangle(1.0, 2.0);
	}
	
	/**
	 * Tests the failed creation of a rectangle with a negative width.
	 */
	@Test
	public void createNegativeWidthRectangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRectangle(-1.0, 2.0);
		});

	}
	
	/**
	 * Tests the failed creation of a rectangle with a negative height.
	 */
	@Test
	public void createNegativeHeightRectangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRectangle(1.0, -2.0);
		});

	}

	/**
	 * Tests the failed creation of a rectangle with a zero width.
	 */
	@Test
	public void createZeroWidthRectangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRectangle(0.0, 2.0);
		});

	}
	
	/**
	 * Tests the failed creation of a rectangle with a zero height.
	 */
	@Test
	public void createZeroHeightRectangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRectangle(1.0, 0.0);
		});

	}
	
	/**
	 * Tests the creation of a triangle using a null point.
	 */
	@Test
	public void createTriangleNullPoint() {
		assertThrows(NullPointerException.class, () -> {
			Vector2 p1 = new Vector2(1.0, 0.0);
			Vector2 p2 = new Vector2(0.5, 1.0);
			// should fail since the vertices list contains null items
			Geometry.createTriangle(p1, p2, null);
		});

	}
	
	/**
	 * Tests the successful creation of a triangle using points.
	 */
	@Test
	public void createTriangle() {
		Vector2 p1 = new Vector2(1.0, 0.0);
		Vector2 p2 = new Vector2(0.5, 1.0);
		Vector2 p3 = new Vector2(-0.5, 1.0);
		Triangle t = Geometry.createTriangle(p1, p2, p3);
		
		// the points should not be the same instances		
		TestCase.assertFalse(t.vertices[0] == p1);
		TestCase.assertFalse(t.vertices[1] == p2);
		TestCase.assertFalse(t.vertices[2] == p3);
	}
	
	/**
	 * Tests the successful creation of a triangle using points.
	 */
	@Test
	public void createTriangleAtOrigin() {
		Vector2 p1 = new Vector2(1.0, 0.0);
		Vector2 p2 = new Vector2(0.5, 1.0);
		Vector2 p3 = new Vector2(-0.5, 1.0);
		Triangle t = Geometry.createTriangleAtOrigin(p1, p2, p3);
		
		// the points should not be the same instances
		TestCase.assertFalse(t.vertices[0] == p1);
		TestCase.assertFalse(t.vertices[1] == p2);
		TestCase.assertFalse(t.vertices[2] == p3);
		
		// make sure the center is at the origin
		Vector2 c = t.getCenter();
		TestCase.assertEquals(0.000, c.x, 1.0e-3);
		TestCase.assertEquals(0.000, c.y, 1.0e-3);
	}
	
	/**
	 * Tests the create right triangle method with a zero width.
	 */
	@Test
	public void createZeroWidthRightTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRightTriangle(0.0, 2.0);
		});

	}
	
	/**
	 * Tests the create right triangle method with a zero height.
	 */
	@Test
	public void createZeroHeightRightTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRightTriangle(1.0, 0.0);
		});

	}
	
	/**
	 * Tests the create right triangle method with a negative width.
	 */
	@Test
	public void createNegativeWidthRightTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRightTriangle(-1.0, 2.0);
		});

	}
	
	/**
	 * Tests the create right triangle method with a negative height.
	 */
	@Test
	public void createNegativeHeightRightTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createRightTriangle(2.0, -2.0);
		});

	}
	
	/**
	 * Tests the successful creation of a right angle triangle.
	 */
	@Test
	public void createRightTriangle() {
		Triangle t = Geometry.createRightTriangle(1.0, 2.0);
		
		// test that the center is the origin
		Vector2 center = t.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
		
		// get the vertices
		Vector2 v1 = t.vertices[0];
		Vector2 v2 = t.vertices[1];
		Vector2 v3 = t.vertices[2];
		
		// create the edges
		Vector2 e1 = v1.to(v2);
		Vector2 e2 = v2.to(v3);
		Vector2 e3 = v3.to(v1);
		
		// one of the follow dot products must be zero
		// indicating a 90 degree angle
		if (e1.dot(e2) < 0.00001 && e1.dot(e2) > -0.00001) {
			TestCase.assertTrue(true);
			return;
		}
		
		if (e2.dot(e3) < 0.00001 && e2.dot(e3) > -0.00001) {
			TestCase.assertTrue(true);
			return;
		}
		
		if (e3.dot(e1) < 0.00001 && e3.dot(e1) > -0.00001) {
			TestCase.assertTrue(true);
			return;
		}
		
		// if we get here we didn't find a 90 degree angle
		TestCase.assertFalse(true);
	}
	
	/**
	 * Tests the successful creation of a right angle triangle.
	 */
	@Test
	public void createRightTriangleMirror() {
		Triangle t = Geometry.createRightTriangle(1.0, 2.0, true);
		
		// test that the center is the origin
		Vector2 center = t.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
		
		// get the vertices
		Vector2 v1 = t.vertices[0];
		Vector2 v2 = t.vertices[1];
		Vector2 v3 = t.vertices[2];
		
		// create the edges
		Vector2 e1 = v1.to(v2);
		Vector2 e2 = v2.to(v3);
		Vector2 e3 = v3.to(v1);
		
		// one of the follow dot products must be zero
		// indicating a 90 degree angle
		if (e1.dot(e2) < 0.00001 && e1.dot(e2) > -0.00001) {
			TestCase.assertTrue(true);
			return;
		}
		
		if (e2.dot(e3) < 0.00001 && e2.dot(e3) > -0.00001) {
			TestCase.assertTrue(true);
			return;
		}
		
		if (e3.dot(e1) < 0.00001 && e3.dot(e1) > -0.00001) {
			TestCase.assertTrue(true);
			return;
		}
		
		// if we get here we didn't find a 90 degree angle
		TestCase.assertFalse(true);
	}
	
	/**
	 * Tests the create equilateral triangle method with a zero height.
	 */
	@Test
	public void createZeroHeightEquilateralTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createEquilateralTriangle(0.0);
		});

	}
	
	/**
	 * Tests the create equilateral triangle method with a negative height.
	 */
	@Test
	public void createNegativeHeightEquilateralTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createEquilateralTriangle(-1.0);
		});

	}
	
	/**
	 * Tests the successful creation of an equilateral angle triangle.
	 */
	@Test
	public void createEquilateralTriangle() {
		Triangle t = Geometry.createEquilateralTriangle(2.0);
		
		// test that the center is the origin
		Vector2 center = t.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
		
		// compute the first angle
		double previousA = t.vertices[0].getAngleBetween(t.vertices[1]);
		// put the angle between 0 and 180
		previousA = Math.abs(Math.PI - Math.abs(previousA));
		// compute the first distance
		double previousD = t.vertices[0].distance(t.vertices[1]);
		// make sure all the angles are the same
		for (int i = 1; i < 3; i++) {
			Vector2 v1 = t.vertices[i];
			Vector2 v2 = t.vertices[i + 1 == 3 ? 0 : i + 1];
			// test the angle between the vectors
			double angle = v1.getAngleBetween(v2);
			// put the angle between 0 and 180
			angle = Math.abs(Math.PI - Math.abs(angle)); 
			if (angle < previousA * 0.9999 || angle > previousA * 1.0001) {
				// its not the same as the last so we fail
				TestCase.assertFalse(true);
			}
			// test the distance between the points
			double distance = v1.distance(v2);
			if (distance < previousD * 0.9999 || distance > previousD * 1.0001) {
				// its not the same as the last so we fail
				TestCase.assertFalse(true);
			}
		}
		// if we get here we didn't find a 90 degree angle
		TestCase.assertTrue(true);
	}

	/**
	 * Tests the create right triangle method with a zero width.
	 */
	@Test
	public void createZeroWidthIsoscelesTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createIsoscelesTriangle(0.0, 1.0);
		});

	}
	
	/**
	 * Tests the create right triangle method with a zero height.
	 */
	@Test
	public void createZeroHeightIsoscelesTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createIsoscelesTriangle(1.0, 0.0);
		});

	}
	
	/**
	 * Tests the create right triangle method with a negative width.
	 */
	@Test
	public void createNegativeWidthIsoscelesTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createIsoscelesTriangle(-1.0, 2.0);
		});

	}
	
	/**
	 * Tests the create right triangle method with a negative height.
	 */
	@Test
	public void createNegativeHeightIsoscelesTriangle() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createIsoscelesTriangle(2.0, -2.0);
		});

	}
	
	/**
	 * Tests the successful creation of an isosceles triangle.
	 */
	@Test
	public void createIsoscelesTriangle() {
		Triangle t = Geometry.createIsoscelesTriangle(2.0, 1.0);
		
		// test that the center is the origin
		Vector2 center = t.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
		
		// get the vertices
		Vector2 v1 = t.vertices[0];
		Vector2 v2 = t.vertices[1];
		Vector2 v3 = t.vertices[2];
		
		// create the edges
		Vector2 e1 = v1.to(v2);
		Vector2 e2 = v2.to(v3);
		Vector2 e3 = v3.to(v1);
		
		// the length of e1 and e3 should be identical
		TestCase.assertEquals(e1.getMagnitude(), e3.getMagnitude(), 1.0e-3);
		
		// then angles between e1 and e2 and e2 and e3 should be identical
		TestCase.assertEquals(e1.getAngleBetween(e2), e2.getAngleBetween(e3), 1.0e-3);
	}
	
	/**
	 * Tests the creation of a segment passing a null point.
	 */
	@Test
	public void createSegmentNullPoint1() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.createSegment(null, new Vector2());
		});

	}

	/**
	 * Tests the creation of a segment passing a null point.
	 * @since 3.1.0
	 */
	@Test
	public void createSegmentNullPoint2() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.createSegment(new Vector2(), null);
		});

	}
	
	/**
	 * Tests the successful creation of a segment given two points.
	 */
	@Test
	public void createSegment() {
		Geometry.createSegment(new Vector2(1.0, 1.0), new Vector2(2.0, -1.0));
	}
	
	/**
	 * Tests the successful creation of a segment given two points at the origin.
	 */
	@Test
	public void createSegmentAtOrigin() {
		Segment s = Geometry.createSegmentAtOrigin(new Vector2(1.0, 1.0), new Vector2(2.0, -1.0));
		
		// test that the center is the origin
		Vector2 center = s.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
	}

	/**
	 * Tests the successful creation of a segment given an end point.
	 */
	@Test
	public void createSegmentEnd() {
		Geometry.createSegment(new Vector2(1.0, 1.0));
	}
	
	/**
	 * Tests the creation of a segment passing a zero length.
	 */
	@Test
	public void createZeroLengthHorizontalSegment() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createHorizontalSegment(0.0);
		});

	}

	/**
	 * Tests the creation of a segment passing a negative length.
	 */
	@Test
	public void createNegativeLengthHorizontalSegment() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createHorizontalSegment(-1.0);
		});

	}
	
	/**
	 * Tests the successful creation of a segment given a length.
	 */
	@Test
	public void createHorizontalSegment() {
		Segment s = Geometry.createHorizontalSegment(5.0);
		
		// test that the center is the origin
		Vector2 center = s.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
	}
	
	/**
	 * Tests the creation of a segment passing a zero length.
	 * @since 2.2.3
	 */
	@Test
	public void createZeroLengthVerticalSegment() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createVerticalSegment(0.0);
		});

	}

	/**
	 * Tests the creation of a segment passing a negative length.
	 * @since 2.2.3
	 */
	@Test
	public void createNegativeLengthVerticalSegment() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createVerticalSegment(-1.0);
		});

	}
	
	/**
	 * Tests the successful creation of a segment given a length.
	 * @since 2.2.3
	 */
	@Test
	public void createVerticalSegment() {
		Segment s = Geometry.createVerticalSegment(5.0);
		
		// test that the center is the origin
		Vector2 center = s.getCenter();
		TestCase.assertEquals(0.000, center.x, 1.0e-3);
		TestCase.assertEquals(0.000, center.y, 1.0e-3);
	}
	
	/**
	 * Tests the getWinding method passing a list.
	 */
	@Test
	public void getWindingList() {
		List<Vector2> points = new ArrayList<Vector2>();
		points.add(new Vector2(-1.0, -1.0));
		points.add(new Vector2(1.0, -1.0));
		points.add(new Vector2(1.0, 1.0));
		points.add(new Vector2(-1.0, 1.0));
		TestCase.assertTrue(Geometry.getWinding(points) > 0);
		
		Collections.reverse(points);
		TestCase.assertTrue(Geometry.getWinding(points) < 0);
	}
	
	/**
	 * Tests the getWinding method passing a null list.
	 */
	@Test
	public void getWindingNullList() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getWinding((List<Vector2>)null);
		});

	}
	
	/**
	 * Tests the getWinding method passing a list with 1 point.
	 */
	@Test
	public void getWindingListLessThan2Points() {
		assertThrows(IllegalArgumentException.class, () -> {
			List<Vector2> points = new ArrayList<Vector2>();
			points.add(new Vector2());
			Geometry.getWinding(points);
		});

	}
	
	/**
	 * Tests the getWinding method passing a list that contains a null point.
	 */
	@Test
	public void getWindingListNullPoint() {
		assertThrows(NullPointerException.class, () -> {
			List<Vector2> points = new ArrayList<Vector2>();
			points.add(new Vector2());
			points.add(null);
			points.add(null);
			Geometry.getWinding(points);
		});

	}
	
	/**
	 * Tests the getWinding method passing a valid array.
	 */
	@Test
	public void getWindingArray() {
		Vector2[] points = new Vector2[4];
		points[0] = new Vector2(-1.0, -1.0);
		points[1] = new Vector2(1.0, -1.0);
		points[2] = new Vector2(1.0, 1.0);
		points[3] = new Vector2(-1.0, 1.0);
		TestCase.assertTrue(Geometry.getWinding(points) > 0);
		
		// reverse the array
		Vector2 p = points[0];
		points[0] = points[3];
		points[3] = p;
		p = points[1];
		points[1] = points[2];
		points[2] = p;
		
		TestCase.assertTrue(Geometry.getWinding(points) < 0);
	}
	
	/**
	 * Tests the getWinding method passing a null array.
	 */
	@Test
	public void getWindingNullArray() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getWinding((Vector2[])null);
		});

	}
	
	/**
	 * Tests the getWinding method passing an array with less than two points.
	 */
	@Test
	public void getWindingArrayLessThan2Points() {
		assertThrows(IllegalArgumentException.class, () -> {
			Vector2[] points = new Vector2[1];
			points[0] = new Vector2(-1.0, -1.0);
			Geometry.getWinding(points);
		});

	}
	
	/**
	 * Tests the getWinding method passing an array containing null points.
	 */
	@Test
	public void getWindingArrayNullPoint() {
		assertThrows(NullPointerException.class, () -> {
			Vector2[] points = new Vector2[4];
			points[0] = new Vector2(-1.0, -1.0);
			points[1] = null;
			points[2] = null;
			points[3] = null;
			Geometry.getWinding(points);
		});

	}
	
	/**
	 * Tests the reverse winding method passing a null list.
	 */
	@Test
	public void reverseWindingNullList() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.reverseWinding((List<Vector2>) null);
		});

	}
	
	/**
	 * Tests the reverse winding method passing a null array.
	 */
	@Test
	public void reverseWindingNullArray() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.reverseWinding((Vector2[]) null);
		});

	}

	/**
	 * Tests the reverse winding method passing an empty array or one element array.
	 */
	@Test
	public void reverseWindingEmptyOrOneElement() {
		Vector2[] array = new Vector2[] {};
		Geometry.reverseWinding(array);
		
		array = new Vector2[] {
			new Vector2(1.0, 1.0)
		};
		Geometry.reverseWinding(array);
		
		List<Vector2> list = new ArrayList<Vector2>();
		Geometry.reverseWinding(list);
		
		list.add(new Vector2(1.0, 1.0));
		Geometry.reverseWinding(list);
	}
	
	/**
	 * Tests the cleanse method passing a null list.
	 * @since 2.2.3
	 */
	@Test
	public void cleanseNullList() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.cleanse((List<Vector2>)null);
		});

	}
	
	/**
	 * Tests the cleanse method passing a null array.
	 * @since 2.2.3
	 */
	@Test
	public void cleanseNullArray() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.cleanse((Vector2[])null);
		});

	}
	
	/**
	 * Tests the cleanse method passing a null list.
	 * @since 2.2.3
	 */
	@Test
	public void cleanseListWithNullElements() {
		assertThrows(NullPointerException.class, () -> {
			List<Vector2> list = new ArrayList<Vector2>();
			list.add(new Vector2());
			list.add(null);
			list.add(new Vector2());
			list.add(new Vector2());
			Geometry.cleanse(list);
		});

	}
	
	/**
	 * Tests the cleanse method passing a null array.
	 * @since 2.2.3
	 */
	@Test
	public void cleanseArrayWithNullElements() {
		assertThrows(NullPointerException.class, () -> {
			Vector2[] array = new Vector2[5];
			array[0] = new Vector2();
			array[3] = new Vector2();
			array[4] = new Vector2();
			Geometry.cleanse(array);
		});

	}
	
	/**
	 * Tests the cleanse empty.
	 */
	@Test
	public void cleanseEmpty() {
		List<Vector2> points = new ArrayList<Vector2>();
		List<Vector2> result1 = Geometry.cleanse(points);
		
		Vector2[] result2 = Geometry.cleanse(new Vector2[] {});
		
		TestCase.assertEquals(0, result1.size());
		TestCase.assertEquals(0, result2.length);
	}
	
	/**
	 * Tests the cleanse list method.
	 */
	@Test
	public void cleanseList() {
		List<Vector2> points = new ArrayList<Vector2>();
		points.add(new Vector2(1.0, 0.0));
		points.add(new Vector2(1.0, 0.0));
		points.add(new Vector2(0.5, -0.5));
		points.add(new Vector2(0.0, -0.5));
		points.add(new Vector2(-0.5, -0.5));
		points.add(new Vector2(-2.0, -0.5));
		points.add(new Vector2(2.1, 0.5));
		points.add(new Vector2(1.0, 0.0));
		
		List<Vector2> result = Geometry.cleanse(points);
		
		TestCase.assertTrue(Geometry.getWinding(result) > 0.0);
		TestCase.assertEquals(4, result.size());
		
		// test the reverse winding
	    Collections.reverse(points);
		
		result = Geometry.cleanse(points);
		
		TestCase.assertTrue(Geometry.getWinding(result) > 0.0);
		TestCase.assertEquals(4, result.size());
	}
	
	/**
	 * Tests the cleanse array method.
	 */
	@Test
	public void cleanseArray() {
		Vector2[] points = new Vector2[8];
		points[0] = new Vector2(1.0, 0.0);
		points[1] = new Vector2(1.0, 0.0);
		points[2] = new Vector2(0.5, -0.5);
		points[3] = new Vector2(0.0, -0.5);
		points[4] = new Vector2(-0.5, -0.5);
		points[5] = new Vector2(-2.0, -0.5);
		points[6] = new Vector2(2.1, 0.5);
		points[7] = new Vector2(1.0, 0.0);
		
		Vector2[] result = Geometry.cleanse(points);
		
		TestCase.assertTrue(Geometry.getWinding(result) > 0.0);
		TestCase.assertEquals(4, result.length);
		
		List<Vector2> pts = Arrays.asList(points);
		Collections.reverse(pts);
		points = pts.toArray(new Vector2[] {});
		
		result = Geometry.cleanse(points);
		
		TestCase.assertTrue(Geometry.getWinding(result) > 0.0);
		TestCase.assertEquals(4, result.length);
	}
	
	/**
	 * Tests the createEllipse method(s)
	 * @since 4.1.0
	 */
	@Test
	public void createEllipse() {
		Ellipse e = Geometry.createEllipse(1.0, 0.5);
		
		TestCase.assertEquals(1.0, e.getWidth());
		TestCase.assertEquals(0.5, e.getHeight());
		TestCase.assertEquals(0.000, e.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, e.getCenter().y, 1.0e-3);
	}

	/**
	 * Tests the createEllipse method(s)
	 * @since 4.1.0
	 */
	@Test
	public void createHalfEllipse() {
		HalfEllipse e = Geometry.createHalfEllipse(1.0, 0.5);
		
		TestCase.assertEquals(1.0, e.getWidth());
		TestCase.assertEquals(0.5, e.getHeight());
		TestCase.assertEquals(0.000, e.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.212, e.getCenter().y, 1.0e-3);
	}

	/**
	 * Tests the createEllipse method(s)
	 * @since 4.1.0
	 */
	@Test
	public void createHalfEllipseAtOrigin() {
		HalfEllipse e = Geometry.createHalfEllipseAtOrigin(1.0, 0.5);
		
		TestCase.assertEquals(1.0, e.getWidth());
		TestCase.assertEquals(0.5, e.getHeight());
		TestCase.assertEquals(0.000, e.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, e.getCenter().y, 1.0e-3);
	}
	
	/**
	 * Tests the createPolygonalEllipse method.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipse() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalEllipse(10, 2, 1);
		// and the center should be the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
	}

	/**
	 * Tests the createPolygonalEllipse method with an odd count.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipseOddCount() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalEllipse(5, 2, 1);
		// and the center should be the origin
		TestCase.assertEquals(4, p.getVertices().length);
		
		// this method should succeed
		p = Geometry.createPolygonalEllipse(11, 2, 1);
		// and the center should be the origin
		TestCase.assertEquals(10, p.getVertices().length);
	}
	
	/**
	 * Tests the createPolygonalEllipse method with less than 4 vertices.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipseLessCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalEllipse(3, 2, 1);
		});

	}
	
	/**
	 * Tests the createPolygonalEllipse method with a zero width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipseZeroWidth() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalEllipse(10, 0, 1);
		});

	}
	
	/**
	 * Tests the createPolygonalEllipse method with a zero height.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipseZeroHeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalEllipse(10, 2, 0);
		});

	}
	
	/**
	 * Tests the createPolygonalEllipse method with a negative width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipseNegativeWidth() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalEllipse(10, -1, 1);
		});

	}
	
	/**
	 * Tests the createPolygonalEllipse method with a negative height.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalEllipseNegativeHeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalEllipse(10, 2, -1);
		});

	}
	
	/**
	 * Tests the createPolygonalSlice method.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSlice() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalSlice(5, 1.0, Math.toRadians(30));
		// the center should not be at the origin
		TestCase.assertEquals(0.658, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
	}
	
	/**
	 * Tests the createPolygonalSliceAtOrigin method.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceAtOrigin() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalSliceAtOrigin(5, 1.0, Math.toRadians(30));
		// and the center should be the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
	}
	
	/**
	 * Tests the createPolygonalSlice method with an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceInvalidCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalSlice(0, 1.0, Math.toRadians(30));
		});

	}
	
	/**
	 * Tests the createPolygonalSlice method with a negative radius.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceNegativeRadius() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalSlice(5, -1, Math.toRadians(30));
		});

	}

	/**
	 * Tests the createPolygonalSlice method with a zero radius.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceZeroRadius() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalSlice(5, 0, Math.toRadians(30));
		});

	}
	
	/**
	 * Tests the createPolygonalSlice method with a negative theta.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceThetaLessThanZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalSlice(5, 1.0, -Math.toRadians(30));
		});

	}
	
	/**
	 * Tests the createPolygonalSlice method with theta equal to zero.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceThetaLessZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalSlice(5, 1.0, 0);
		});

	}
	
	/**
	 * Tests the createPolygonalSlice method with theta greater than 180 degrees.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalSliceThetaGreaterThan180() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalSlice(5, 1.0, Math.toRadians(190));
		});

	}

	/**
	 * Tests the createPolygonalHalfEllipse method.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipse() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalHalfEllipse(5, 1.0, 0.5);
		// the center should not be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.103, p.getCenter().y, 1.0e-3);
	}
	
	/**
	 * Tests the createPolygonalHalfEllipseAtOrigin method.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipseAtOrigin() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalHalfEllipseAtOrigin(5, 1.0, 0.5);
		// the center should be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
	}
	
	/**
	 * Tests the createPolygonalHalfEllipse method with an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipseInvalidCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalHalfEllipse(0, 1.0, 0.5);
		});

	}

	/**
	 * Tests the createPolygonalHalfEllipse method with a negative width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipseZeroWidth() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalHalfEllipse(5, 0, 0.5);
		});

	}

	/**
	 * Tests the createPolygonalHalfEllipse method with zero width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipseNegativeWidth() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalHalfEllipse(5, -1, 0.5);
		});

	}

	/**
	 * Tests the createPolygonalHalfEllipse method with a negative height.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipseNegativeHeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalHalfEllipse(5, 1.0, -0.5);
		});

	}

	/**
	 * Tests the createPolygonalHalfEllipse method with zero height.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalHalfEllipseZeroHeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalHalfEllipse(5, 1.0, 0);
		});

	}

	/**
	 * Tests the createPolygonalCircle method.
	 * @since 4.1.0
	 */
	@Test
	public void createPolygonalCircle() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalCircle(5, 1.0);
		// the center should be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
		TestCase.assertEquals(1.000, p.getVertices()[0].x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getVertices()[0].y, 1.0e-3);
		TestCase.assertEquals(5, p.getVertices().length);
		
		p = Geometry.createPolygonalCircle(5, 1.0, Math.toRadians(30));
		// the center should be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
		TestCase.assertEquals(Math.cos(Math.toRadians(30)), p.getVertices()[0].x, 1.0e-3);
		TestCase.assertEquals(Math.sin(Math.toRadians(30)), p.getVertices()[0].y, 1.0e-3);
		TestCase.assertEquals(5, p.getVertices().length);
	}
	
	/**
	 * Tests the createPolygonalCircle method.
	 * @since 4.1.0
	 */
	@Test
	public void createPolygonalCircleInvalidCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCircle(2, 1.0);
		});

	}
	
	/**
	 * Tests the createPolygonalCircle method.
	 * @since 4.1.0
	 */
	@Test
	public void createPolygonalCircleZeroCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCircle(0, 1.0);
		});

	}
	
	/**
	 * Tests the createPolygonalCircle method.
	 * @since 4.1.0
	 */
	@Test
	public void createPolygonalCircleNegativeCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCircle(-1, 1.0);
		});

	}

	/**
	 * Tests the createPolygonalCircle method.
	 * @since 4.1.0
	 */
	@Test
	public void createPolygonalCircleZeroRadius() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCircle(6, 0);
		});

	}
	
	/**
	 * Tests the createPolygonalCircle method.
	 * @since 4.1.0
	 */
	@Test
	public void createPolygonalCircleNegativeRadius() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCircle(6, -1.0);
		});

	}
	
	/**
	 * Tests the createPolygonalCapsule method.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalCapsule() {
		// this method should succeed
		Polygon p = Geometry.createPolygonalCapsule(5, 1.0, 0.5);
		// the center should be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
		
		p = Geometry.createPolygonalCapsule(5, 1.0, 1.0);
		// the center should be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
		
		p = Geometry.createPolygonalCapsule(5, 0.5, 1.0);
		// the center should be at the origin
		TestCase.assertEquals(0.000, p.getCenter().x, 1.0e-3);
		TestCase.assertEquals(0.000, p.getCenter().y, 1.0e-3);
	}
	
	/**
	 * Tests the createPolygonalCapsule method with an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalCapsuleInvalidCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCapsule(0, 1.0, 0.5);
		});

	}
	
	/**
	 * Tests the createPolygonalCapsule method with zero width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalCapsuleZeroWidth() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCapsule(5, 0, 0.5);
		});

	}

	/**
	 * Tests the createPolygonalCapsule method with a negative width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalCapsuleNegativeWidth() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCapsule(5, -1, 0.5);
		});

	}

	/**
	 * Tests the createPolygonalCapsule method with zero height.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalCapsuleZeroHeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCapsule(5, 1.0, 0);
		});

	}

	/**
	 * Tests the createPolygonalCapsule method with zero width.
	 * @since 3.1.5
	 */
	@Test
	public void createPolygonalCapsuleNegativeHeight() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.createPolygonalCapsule(5, 1.0, -0.5);
		});

	}
	
	/**
	 * Tests the flip polygon method.
	 * @since 3.1.4
	 */
	@Test
	public void flip() {
		Polygon p = Geometry.createUnitCirclePolygon(5, 1.0);
		
		// flip about an arbitrary vector and point (line)
		Polygon flipped = Geometry.flip(p, new Vector2(1.0, 1.0),  new Vector2(0.0, 2.0));
		
		Vector2[] vertices = flipped.getVertices();
		TestCase.assertEquals(-2.951, vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 2.309, vertices[0].y, 1.0e-3);
		TestCase.assertEquals(-2.587, vertices[1].x, 1.0e-3);
		TestCase.assertEquals( 1.190, vertices[1].y, 1.0e-3);
		TestCase.assertEquals(-1.412, vertices[2].x, 1.0e-3);
		TestCase.assertEquals( 1.190, vertices[2].y, 1.0e-3);
		TestCase.assertEquals(-1.048, vertices[3].x, 1.0e-3);
		TestCase.assertEquals( 2.309, vertices[3].y, 1.0e-3);
		TestCase.assertEquals(-2.000, vertices[4].x, 1.0e-3);
		TestCase.assertEquals( 3.000, vertices[4].y, 1.0e-3);
		
		// flip about X
		flipped = Geometry.flipAlongTheXAxis(p);
		vertices = flipped.getVertices();
		TestCase.assertEquals( 0.309, vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 0.951, vertices[0].y, 1.0e-3);
		TestCase.assertEquals(-0.809, vertices[1].x, 1.0e-3);
		TestCase.assertEquals( 0.587, vertices[1].y, 1.0e-3);
		TestCase.assertEquals(-0.809, vertices[2].x, 1.0e-3);
		TestCase.assertEquals(-0.587, vertices[2].y, 1.0e-3);
		TestCase.assertEquals( 0.309, vertices[3].x, 1.0e-3);
		TestCase.assertEquals(-0.951, vertices[3].y, 1.0e-3);
		TestCase.assertEquals( 1.000, vertices[4].x, 1.0e-3);
		TestCase.assertEquals( 0.000, vertices[4].y, 1.0e-3);
		
		// flip about X at point
		flipped = Geometry.flipAlongTheXAxis(p, new Vector2(0.0, 1.0));
		vertices = flipped.getVertices();
		TestCase.assertEquals( 0.309, vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 2.951, vertices[0].y, 1.0e-3);
		TestCase.assertEquals(-0.809, vertices[1].x, 1.0e-3);
		TestCase.assertEquals( 2.587, vertices[1].y, 1.0e-3);
		TestCase.assertEquals(-0.809, vertices[2].x, 1.0e-3);
		TestCase.assertEquals( 1.412, vertices[2].y, 1.0e-3);
		TestCase.assertEquals( 0.309, vertices[3].x, 1.0e-3);
		TestCase.assertEquals( 1.048, vertices[3].y, 1.0e-3);
		TestCase.assertEquals( 1.000, vertices[4].x, 1.0e-3);
		TestCase.assertEquals( 2.000, vertices[4].y, 1.0e-3);
		
		// flip about Y
		flipped = Geometry.flipAlongTheYAxis(p);
		vertices = flipped.getVertices();
		TestCase.assertEquals(-0.309, vertices[0].x, 1.0e-3);
		TestCase.assertEquals(-0.951, vertices[0].y, 1.0e-3);
		TestCase.assertEquals( 0.809, vertices[1].x, 1.0e-3);
		TestCase.assertEquals(-0.587, vertices[1].y, 1.0e-3);
		TestCase.assertEquals( 0.809, vertices[2].x, 1.0e-3);
		TestCase.assertEquals( 0.587, vertices[2].y, 1.0e-3);
		TestCase.assertEquals(-0.309, vertices[3].x, 1.0e-3);
		TestCase.assertEquals( 0.951, vertices[3].y, 1.0e-3);
		TestCase.assertEquals(-1.000, vertices[4].x, 1.0e-3);
		TestCase.assertEquals(-0.000, vertices[4].y, 1.0e-3);
		
		// flip about Y at point
		flipped = Geometry.flipAlongTheYAxis(p, new Vector2(1.0, 0.0));
		vertices = flipped.getVertices();
		TestCase.assertEquals( 1.690, vertices[0].x, 1.0e-3);
		TestCase.assertEquals(-0.951, vertices[0].y, 1.0e-3);
		TestCase.assertEquals( 2.809, vertices[1].x, 1.0e-3);
		TestCase.assertEquals(-0.587, vertices[1].y, 1.0e-3);
		TestCase.assertEquals( 2.809, vertices[2].x, 1.0e-3);
		TestCase.assertEquals( 0.587, vertices[2].y, 1.0e-3);
		TestCase.assertEquals( 1.690, vertices[3].x, 1.0e-3);
		TestCase.assertEquals( 0.951, vertices[3].y, 1.0e-3);
		TestCase.assertEquals( 1.000, vertices[4].x, 1.0e-3);
		TestCase.assertEquals(-0.000, vertices[4].y, 1.0e-3);
		
		// flip about a vector originating from the origin
		flipped = Geometry.flip(p, new Vector2(1.0, 0.0));
		vertices = flipped.getVertices();
		TestCase.assertEquals( 0.309, vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 0.951, vertices[0].y, 1.0e-3);
		TestCase.assertEquals(-0.809, vertices[1].x, 1.0e-3);
		TestCase.assertEquals( 0.587, vertices[1].y, 1.0e-3);
		TestCase.assertEquals(-0.809, vertices[2].x, 1.0e-3);
		TestCase.assertEquals(-0.587, vertices[2].y, 1.0e-3);
		TestCase.assertEquals( 0.309, vertices[3].x, 1.0e-3);
		TestCase.assertEquals(-0.951, vertices[3].y, 1.0e-3);
		TestCase.assertEquals( 1.000, vertices[4].x, 1.0e-3);
		TestCase.assertEquals( 0.000, vertices[4].y, 1.0e-3);
	}
	
	/**
	 * Tests the flip polygon method with a null polygon.
	 * @since 3.1.4
	 */
	@Test
	public void flipNullPolygon() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.flip(null, new Vector2(1.0, 1.0),  null);
		});

	}
	
	/**
	 * Tests the flip polygon method with a null axis.
	 * @since 3.1.4
	 */
	@Test
	public void flipNullAxis() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.flip(Geometry.createSquare(1.0), null,  null);
		});

	}

	/**
	 * Tests the flip polygon method with a zero vector axis.
	 * @since 3.1.4
	 */
	@Test
	public void flipZeroAxis() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.flip(Geometry.createSquare(1.0), new Vector2(),  null);
		});

	}
	
	/**
	 * Tests the flip polygon method with a null point.
	 * @since 3.1.4
	 */
	@Test
	public void flipNullPoint() {
		// it should use the center
		Geometry.flip(Geometry.createSquare(1.0), new Vector2(1.0, 1.0),  null);
	}
	
	/**
	 * Test the minkowski sum method.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSum() {
		// verify the generation of the polygon works
		Polygon p = Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), Geometry.createCircle(0.2), 3);
		// verify the new vertex count
		TestCase.assertEquals(25, p.vertices.length);
		
		// verify the generation of the polygon works
		p = Geometry.minkowskiSum(Geometry.createCircle(0.2), Geometry.createUnitCirclePolygon(5, 0.5), 3);
		// verify the new vertex count
		TestCase.assertEquals(25, p.vertices.length);
		
		// verify the generation of the polygon works
		p = Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), 0.2, 3);
		// verify the new vertex count
		TestCase.assertEquals(25, p.vertices.length);
		
		// verify the generation of the polygon works
		p = Geometry.minkowskiSum(Geometry.createSquare(1.0), Geometry.createUnitCirclePolygon(5, 0.2));
		TestCase.assertEquals(8, p.vertices.length);
		
		// verify the generation of the polygon works
		p = Geometry.minkowskiSum(Geometry.createSegment(new Vector2(1.0, 0.0)), Geometry.createUnitCirclePolygon(5, 0.2));
		TestCase.assertEquals(7, p.vertices.length);
		
		// verify the generation of the polygon works
		p = Geometry.minkowskiSum(Geometry.createSegment(new Vector2(1.0, 0.0)), Geometry.createSegment(new Vector2(0.5, 0.5)));
		TestCase.assertEquals(4, p.vertices.length);
		
		p = Geometry.minkowskiSum(Geometry.createSquare(1.0), Geometry.createSquare(0.5));
		TestCase.assertEquals(4, p.vertices.length);
		
		Polygon s = Geometry.createSquare(1.0);
		s.translate(1.0, 0.5);
		p = Geometry.minkowskiSum(s, Geometry.createSquare(0.5));
		TestCase.assertEquals(4, p.vertices.length);
	}
	
	/**
	 * Test the minkowski sum method with invalid segments.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidSegments() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createSegment(new Vector2(1.0, 0.0)), Geometry.createSegment(new Vector2(-0.5, 0.0)));

		});
	}
	
	/**
	 * Test the minkowski sum method given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumNullWound1() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.minkowskiSum(null, Geometry.createUnitCirclePolygon(5, 0.5));
		});

	}
	
	/**
	 * Test the minkowski sum method given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumNullWound2() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), null);
		});

	}
	
	/**
	 * Test the minkowski sum method given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumNullShape1() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.minkowskiSum(null, Geometry.createCircle(0.2), 3);
		});

	}
	
	/**
	 * Test the minkowski sum method given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumNullShape2() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), null, 3);
		});

	}
	
	/**
	 * Test the minkowski sum method given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumNullShape3() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.minkowskiSum(null, 0.2, 3);
		});

	}
	
	/**
	 * Test the minkowski sum method given an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidCount1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), 0.2, 0);
		});

	}
	
	/**
	 * Test the minkowski sum method given an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidCount2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), 0.2, -2);
		});

	}
	
	/**
	 * Test the minkowski sum method given an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidCount3() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), Geometry.createCircle(0.5), 0);

		});
	}
	
	/**
	 * Test the minkowski sum method given an invalid count.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidCount4() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), Geometry.createCircle(0.5), -2);

		});
	}
	
	/**
	 * Test the minkowski sum method given an invalid radius.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidRadius1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), 0, 3);

		});
	}
	
	/**
	 * Test the minkowski sum method given an invalid radius.
	 * @since 3.1.5
	 */
	@Test
	public void minkowskiSumInvalidRadius2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.minkowskiSum(Geometry.createUnitCirclePolygon(5, 0.5), -2.0, 3);
		});

	}
	
	/**
	 * Tests that the scale methods work as expected.
	 * @since 3.1.5
	 */
	@Test
	public void scale() {
		Circle s1 = Geometry.scale(Geometry.createCircle(0.5), 2);
		Capsule s2 = Geometry.scale(Geometry.createCapsule(1.0, 0.5), 2);
		Ellipse s3 = Geometry.scale(Geometry.createEllipse(1.0, 0.5), 2);
		HalfEllipse s4 = Geometry.scale(Geometry.createHalfEllipse(1.0, 0.25), 2);
		Slice s5 = Geometry.scale(Geometry.createSlice(0.5, Math.toRadians(30)), 2);
		Polygon s6 = Geometry.scale(Geometry.createUnitCirclePolygon(5, 0.5), 2);
		Segment s7 = Geometry.scale(Geometry.createSegment(new Vector2(1.0, 0.0)), 2);
		
		TestCase.assertEquals(1.000, s1.radius, 1.0e-3);
		TestCase.assertEquals(2.000, s2.length, 1.0e-3);
		TestCase.assertEquals(1.000, s2.capRadius * 2.0, 1.0e-3);
		TestCase.assertEquals(2.000, s3.getWidth(), 1.0e-3);
		TestCase.assertEquals(1.000, s3.getHeight(), 1.0e-3);
		TestCase.assertEquals(2.000, s4.getWidth(), 1.0e-3);
		TestCase.assertEquals(0.500, s4.height, 1.0e-3);
		TestCase.assertEquals(1.000, s5.sliceRadius, 1.0e-3);
		TestCase.assertEquals(1.000, s6.radius, 1.0e-3);
		TestCase.assertEquals(2.000, s7.length, 1.0e-3);
		
		s1 = Geometry.scale(Geometry.createCircle(0.5), 0.5);
		s2 = Geometry.scale(Geometry.createCapsule(1.0, 0.5), 0.5);
		s3 = Geometry.scale(Geometry.createEllipse(1.0, 0.5), 0.5);
		s4 = Geometry.scale(Geometry.createHalfEllipse(1.0, 0.25), 0.5);
		s5 = Geometry.scale(Geometry.createSlice(0.5, Math.toRadians(30)), 0.5);
		s6 = Geometry.scale(Geometry.createUnitCirclePolygon(5, 0.5), 0.5);
		s7 = Geometry.scale(Geometry.createSegment(new Vector2(1.0, 0.0)), 0.5);
		
		TestCase.assertEquals(0.250, s1.radius, 1.0e-3);
		TestCase.assertEquals(0.500, s2.length, 1.0e-3);
		TestCase.assertEquals(0.250, s2.capRadius * 2.0, 1.0e-3);
		TestCase.assertEquals(0.500, s3.getWidth(), 1.0e-3);
		TestCase.assertEquals(0.250, s3.getHeight(), 1.0e-3);
		TestCase.assertEquals(0.500, s4.getWidth(), 1.0e-3);
		TestCase.assertEquals(0.125, s4.height, 1.0e-3);
		TestCase.assertEquals(0.250, s5.sliceRadius, 1.0e-3);
		TestCase.assertEquals(0.250, s6.radius, 1.0e-3);
		TestCase.assertEquals(0.500, s7.length, 1.0e-3);
	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullCircle() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((Circle)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullCapsule() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((Capsule)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullEllipse() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((Ellipse)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullHalfEllipse() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((HalfEllipse)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullSlice() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((Slice)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullPolygon() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((Polygon)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given a null shape.
	 * @since 3.1.5
	 */
	@Test
	public void scaleNullSegment() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.scale((Segment)null, 1.2);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scaleCircleInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createCircle(0.5), 0);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scaleCapsuleInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createCapsule(1.0, 0.5), 0);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scaleEllipseInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createEllipse(1.0, 0.5), 0);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scaleHalfEllipseInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createHalfEllipse(1.0, 0.25), 0);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scaleSliceInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createSlice(0.5, Math.toRadians(30)), 0);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scalePolygonInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createUnitCirclePolygon(5, 0.5), 0);
		});

	}
	
	/**
	 * Tests that the scale method fails if given an invalid scale factor.
	 * @since 3.1.5
	 */
	@Test
	public void scaleSegmentInvalid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Geometry.scale(Geometry.createSegment(new Vector2(1.0, 1.0)), 0);
		});

	}

	/**
	 * Tests the createLinks method.
	 * @since 4.0.1
	 */
	@Test
	public void createLinksWrap() {
		Vector2 a = new Vector2(0.0, 0.0);
		Vector2 b = new Vector2(2.4, 0.0);
		Vector2 c = new Vector2(2.4, 1.6);
		Vector2 d = new Vector2(0.0, 1.6);
		List<Link> links = Geometry.createLinks(Arrays.asList(a, b, c, d), true);
		
		TestCase.assertEquals(4, links.size());
		TestCase.assertEquals(links.get(0).getPoint1(), a);
		TestCase.assertEquals(links.get(0).getPoint2(), b);
		TestCase.assertEquals(links.get(1).getPoint1(), b);
		TestCase.assertEquals(links.get(1).getPoint2(), c);
		TestCase.assertEquals(links.get(2).getPoint1(), c);
		TestCase.assertEquals(links.get(2).getPoint2(), d);
		TestCase.assertEquals(links.get(3).getPoint1(), d);
		TestCase.assertEquals(links.get(3).getPoint2(), a);
	}
	
	/**
	 * Tests the creation of a link chain.
	 */
	@Test
	public void createLinks() {
		List<Vector2> verts = new ArrayList<Vector2>();
		verts.add(new Vector2(2.0, 1.5));
		verts.add(new Vector2(1.0, 1.0));
		verts.add(new Vector2(1.0, 0.0));
		verts.add(new Vector2(0.0, 0.0));
		
		// test closed loop
		List<Link> links = Geometry.createLinks(verts, true);
		TestCase.assertEquals(4, links.size());
		
		// test link1
		TestCase.assertEquals(links.get(3), links.get(0).getPrevious());
		TestCase.assertEquals(links.get(1), links.get(0).getNext());
		TestCase.assertEquals(verts.get(3).x, links.get(0).getPoint0().x);
		TestCase.assertEquals(verts.get(3).y, links.get(0).getPoint0().y);
		TestCase.assertEquals(verts.get(0).x, links.get(0).getPoint1().x);
		TestCase.assertEquals(verts.get(0).y, links.get(0).getPoint1().y);
		TestCase.assertEquals(verts.get(1).x, links.get(0).getPoint2().x);
		TestCase.assertEquals(verts.get(1).y, links.get(0).getPoint2().y);
		TestCase.assertEquals(verts.get(2).x, links.get(0).getPoint3().x);
		TestCase.assertEquals(verts.get(2).y, links.get(0).getPoint3().y);
		
		// test link2
		TestCase.assertEquals(links.get(0), links.get(1).getPrevious());
		TestCase.assertEquals(links.get(2), links.get(1).getNext());
		TestCase.assertEquals(verts.get(0).x, links.get(1).getPoint0().x);
		TestCase.assertEquals(verts.get(0).y, links.get(1).getPoint0().y);
		TestCase.assertEquals(verts.get(1).x, links.get(1).getPoint1().x);
		TestCase.assertEquals(verts.get(1).y, links.get(1).getPoint1().y);
		TestCase.assertEquals(verts.get(2).x, links.get(1).getPoint2().x);
		TestCase.assertEquals(verts.get(2).y, links.get(1).getPoint2().y);
		TestCase.assertEquals(verts.get(3).x, links.get(1).getPoint3().x);
		TestCase.assertEquals(verts.get(3).y, links.get(1).getPoint3().y);
		
		// test link3
		TestCase.assertEquals(links.get(1), links.get(2).getPrevious());
		TestCase.assertEquals(links.get(3), links.get(2).getNext());
		TestCase.assertEquals(verts.get(1).x, links.get(2).getPoint0().x);
		TestCase.assertEquals(verts.get(1).y, links.get(2).getPoint0().y);
		TestCase.assertEquals(verts.get(2).x, links.get(2).getPoint1().x);
		TestCase.assertEquals(verts.get(2).y, links.get(2).getPoint1().y);
		TestCase.assertEquals(verts.get(3).x, links.get(2).getPoint2().x);
		TestCase.assertEquals(verts.get(3).y, links.get(2).getPoint2().y);
		TestCase.assertEquals(verts.get(0).x, links.get(2).getPoint3().x);
		TestCase.assertEquals(verts.get(0).y, links.get(2).getPoint3().y);
		
		// test link4
		TestCase.assertEquals(links.get(2), links.get(3).getPrevious());
		TestCase.assertEquals(links.get(0), links.get(3).getNext());
		TestCase.assertEquals(verts.get(2).x, links.get(3).getPoint0().x);
		TestCase.assertEquals(verts.get(2).y, links.get(3).getPoint0().y);
		TestCase.assertEquals(verts.get(3).x, links.get(3).getPoint1().x);
		TestCase.assertEquals(verts.get(3).y, links.get(3).getPoint1().y);
		TestCase.assertEquals(verts.get(0).x, links.get(3).getPoint2().x);
		TestCase.assertEquals(verts.get(0).y, links.get(3).getPoint2().y);
		TestCase.assertEquals(verts.get(1).x, links.get(3).getPoint3().x);
		TestCase.assertEquals(verts.get(1).y, links.get(3).getPoint3().y);
		
		// test non-closed loop
		links = Geometry.createLinks(verts, false);
		TestCase.assertEquals(3, links.size());
		
		// test link1
		TestCase.assertEquals(null, links.get(0).getPrevious());
		TestCase.assertEquals(links.get(1), links.get(0).getNext());
		TestCase.assertEquals(null, links.get(0).getPoint0());
		TestCase.assertEquals(verts.get(0).x, links.get(0).getPoint1().x);
		TestCase.assertEquals(verts.get(0).y, links.get(0).getPoint1().y);
		TestCase.assertEquals(verts.get(1).x, links.get(0).getPoint2().x);
		TestCase.assertEquals(verts.get(1).y, links.get(0).getPoint2().y);
		TestCase.assertEquals(verts.get(2).x, links.get(0).getPoint3().x);
		TestCase.assertEquals(verts.get(2).y, links.get(0).getPoint3().y);
		
		// test link2
		TestCase.assertEquals(links.get(0), links.get(1).getPrevious());
		TestCase.assertEquals(links.get(2), links.get(1).getNext());
		TestCase.assertEquals(verts.get(0).x, links.get(1).getPoint0().x);
		TestCase.assertEquals(verts.get(0).y, links.get(1).getPoint0().y);
		TestCase.assertEquals(verts.get(1).x, links.get(1).getPoint1().x);
		TestCase.assertEquals(verts.get(1).y, links.get(1).getPoint1().y);
		TestCase.assertEquals(verts.get(2).x, links.get(1).getPoint2().x);
		TestCase.assertEquals(verts.get(2).y, links.get(1).getPoint2().y);
		TestCase.assertEquals(verts.get(3).x, links.get(1).getPoint3().x);
		TestCase.assertEquals(verts.get(3).y, links.get(1).getPoint3().y);
		
		// test link3
		TestCase.assertEquals(links.get(1), links.get(2).getPrevious());
		TestCase.assertEquals(null, links.get(2).getNext());
		TestCase.assertEquals(verts.get(1).x, links.get(2).getPoint0().x);
		TestCase.assertEquals(verts.get(1).y, links.get(2).getPoint0().y);
		TestCase.assertEquals(verts.get(2).x, links.get(2).getPoint1().x);
		TestCase.assertEquals(verts.get(2).y, links.get(2).getPoint1().y);
		TestCase.assertEquals(verts.get(3).x, links.get(2).getPoint2().x);
		TestCase.assertEquals(verts.get(3).y, links.get(2).getPoint2().y);
		TestCase.assertEquals(null, links.get(2).getPoint3());
	}
	
	/**
	 * Tests the creation of a link chain.
	 */
	@Test
	public void createLinksNull() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.createLinks((Vector2[])null, false);
		});

	}

	/**
	 * Tests the creation of a link chain.
	 */
	@Test
	public void createLinksOneVertex() {
		assertThrows(IllegalArgumentException.class, () -> {
			List<Vector2> verts = new ArrayList<Vector2>();
			verts.add(new Vector2(2.0, 1.5));
			Geometry.createLinks(verts, false);
		});

	}

	/**
	 * Tests the creation of a link chain.
	 */
	@Test
	public void createLinksNullVertex() {
		assertThrows(NullPointerException.class, () -> {
			List<Vector2> verts = new ArrayList<Vector2>();
			verts.add(new Vector2(2.0, 1.5));
			verts.add(new Vector2(1.0, 1.0));
			verts.add(null);
			verts.add(new Vector2(3.0, 1.5));
			Geometry.createLinks(verts, false);
		});

	}

	/**
	 * Test the creation of a slice.
	 * @since 4.1.0
	 */
	@Test
	public void createSlice() {
		Slice s = Geometry.createSlice(1.0, Math.toRadians(30));
		
		TestCase.assertEquals(1.0, s.getSliceRadius());
		TestCase.assertEquals(Math.toRadians(30), s.getTheta());
		TestCase.assertEquals(0.000, s.getCircleCenter().x);
		TestCase.assertEquals(0.000, s.getCircleCenter().y);
	}

	/**
	 * Test the creation of a slice at the origin.
	 * @since 4.1.0
	 */
	@Test
	public void createSliceAtOrigin() {
		Slice s = Geometry.createSliceAtOrigin(1.0, Math.toRadians(30));
		
		TestCase.assertEquals(1.0, s.getSliceRadius());
		TestCase.assertEquals(Math.toRadians(30), s.getTheta());
		TestCase.assertEquals(0.000, s.getCenter().x);
		TestCase.assertEquals(0.000, s.getCenter().y);
	}
	
	/**
	 * Tests polygon intersection under a non-degenerate case.
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersection() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(1.0, 0.0),
				new Vector2(3.0, 1.0),
				new Vector2(3.25, 3.75),
				new Vector2(1.5, 4.75),
				new Vector2(0.0, 5.25),
				new Vector2(-2.5, 4.5),
				new Vector2(-2.75, 3.25),
				new Vector2(-2.5, 1.5));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertEquals(11, result.vertices.length);
		TestCase.assertEquals(-0.842, result.vertices[0].x, 1e-3);
		TestCase.assertEquals( 0.789, result.vertices[0].y, 1e-3);
		TestCase.assertEquals( 0.461, result.vertices[1].x, 1e-3);
		TestCase.assertEquals( 0.230, result.vertices[1].y, 1e-3);
		TestCase.assertEquals( 2.000, result.vertices[2].x, 1e-3);
		TestCase.assertEquals( 1.000, result.vertices[2].y, 1e-3);
		TestCase.assertEquals( 3.100, result.vertices[3].x, 1e-3);
		TestCase.assertEquals( 2.100, result.vertices[3].y, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[4].x, 1e-3);
		TestCase.assertEquals( 3.750, result.vertices[4].y, 1e-3);
		TestCase.assertEquals( 1.500, result.vertices[5].x, 1e-3);
		TestCase.assertEquals( 4.750, result.vertices[5].y, 1e-3);
		TestCase.assertEquals( 0.750, result.vertices[6].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[6].y, 1e-3);
		TestCase.assertEquals(-0.833, result.vertices[7].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[7].y, 1e-3);
		TestCase.assertEquals(-2.500, result.vertices[8].x, 1e-3);
		TestCase.assertEquals( 4.500, result.vertices[8].y, 1e-3);
		TestCase.assertEquals(-2.750, result.vertices[9].x, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[9].y, 1e-3);
		TestCase.assertEquals(-2.639, result.vertices[10].x, 1e-3);
		TestCase.assertEquals( 2.474, result.vertices[10].y, 1e-3);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where a vertex
	 * of one of the polygons (A) is on an edge of the other (B).
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionVertexOnEdgeA() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-2.6, 4.0));
		Polygon p2 = new Polygon(
				new Vector2(1.0, 0.0),
				new Vector2(3.0, 1.0),
				new Vector2(3.25, 3.75),
				new Vector2(1.5, 4.75),
				new Vector2(0.0, 5.25),
				new Vector2(-2.5, 4.5),
				new Vector2(-2.75, 3.25),
				new Vector2(-2.5, 1.5));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertEquals(10, result.vertices.length);
		TestCase.assertEquals(-0.386, result.vertices[0].x, 1e-3);
		TestCase.assertEquals( 0.594, result.vertices[0].y, 1e-3);
		TestCase.assertEquals( 0.461, result.vertices[1].x, 1e-3);
		TestCase.assertEquals( 0.230, result.vertices[1].y, 1e-3);
		TestCase.assertEquals( 2.000, result.vertices[2].x, 1e-3);
		TestCase.assertEquals( 1.000, result.vertices[2].y, 1e-3);
		TestCase.assertEquals( 3.100, result.vertices[3].x, 1e-3);
		TestCase.assertEquals( 2.100, result.vertices[3].y, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[4].x, 1e-3);
		TestCase.assertEquals( 3.750, result.vertices[4].y, 1e-3);
		TestCase.assertEquals( 1.500, result.vertices[5].x, 1e-3);
		TestCase.assertEquals( 4.750, result.vertices[5].y, 1e-3);
		TestCase.assertEquals( 0.750, result.vertices[6].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[6].y, 1e-3);
		TestCase.assertEquals(-0.833, result.vertices[7].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[7].y, 1e-3);
		TestCase.assertEquals(-2.256, result.vertices[8].x, 1e-3);
		TestCase.assertEquals( 4.573, result.vertices[8].y, 1e-3);
		TestCase.assertEquals(-2.600, result.vertices[9].x, 1e-3);
		TestCase.assertEquals( 4.000, result.vertices[9].y, 1e-3);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where a vertex
	 * of one of the polygons (B) is on an edge of the other (A).
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionVertexOnEdgeB() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(1.0, 0.0),
				new Vector2(3.0, 1.0),
				new Vector2(3.25, 3.75),
				new Vector2(1.5, 4.75),
				new Vector2(0.0, 5.0),
				new Vector2(-2.5, 4.5),
				new Vector2(-2.75, 3.25),
				new Vector2(-2.5, 1.5));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertEquals(10, result.vertices.length);
		TestCase.assertEquals(-0.842, result.vertices[0].x, 1e-3);
		TestCase.assertEquals( 0.789, result.vertices[0].y, 1e-3);
		TestCase.assertEquals( 0.461, result.vertices[1].x, 1e-3);
		TestCase.assertEquals( 0.230, result.vertices[1].y, 1e-3);
		TestCase.assertEquals( 2.000, result.vertices[2].x, 1e-3);
		TestCase.assertEquals( 1.000, result.vertices[2].y, 1e-3);
		TestCase.assertEquals( 3.100, result.vertices[3].x, 1e-3);
		TestCase.assertEquals( 2.100, result.vertices[3].y, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[4].x, 1e-3);
		TestCase.assertEquals( 3.750, result.vertices[4].y, 1e-3);
		TestCase.assertEquals( 1.500, result.vertices[5].x, 1e-3);
		TestCase.assertEquals( 4.750, result.vertices[5].y, 1e-3);
		TestCase.assertEquals( 0.000, result.vertices[6].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[6].y, 1e-3);
		TestCase.assertEquals(-2.500, result.vertices[7].x, 1e-3);
		TestCase.assertEquals( 4.500, result.vertices[7].y, 1e-3);
		TestCase.assertEquals(-2.750, result.vertices[8].x, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[8].y, 1e-3);
		TestCase.assertEquals(-2.639, result.vertices[9].x, 1e-3);
		TestCase.assertEquals( 2.474, result.vertices[9].y, 1e-3);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where a vertex
	 * of one of the polygons (A) is coincident with a vertex of the other (B).
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionCoincidentVertexA() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-2.75, 3.25));
		Polygon p2 = new Polygon(
				new Vector2(1.0, 0.0),
				new Vector2(3.0, 1.0),
				new Vector2(3.25, 3.75),
				new Vector2(1.5, 4.75),
				new Vector2(0.0, 5.25),
				new Vector2(-2.5, 4.5),
				new Vector2(-2.75, 3.25),
				new Vector2(-2.5, 1.5));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertEquals(10, result.vertices.length);
		TestCase.assertEquals(-0.568, result.vertices[0].x, 1e-3);
		TestCase.assertEquals( 0.672, result.vertices[0].y, 1e-3);
		TestCase.assertEquals( 0.461, result.vertices[1].x, 1e-3);
		TestCase.assertEquals( 0.230, result.vertices[1].y, 1e-3);
		TestCase.assertEquals( 2.000, result.vertices[2].x, 1e-3);
		TestCase.assertEquals( 1.000, result.vertices[2].y, 1e-3);
		TestCase.assertEquals( 3.100, result.vertices[3].x, 1e-3);
		TestCase.assertEquals( 2.100, result.vertices[3].y, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[4].x, 1e-3);
		TestCase.assertEquals( 3.750, result.vertices[4].y, 1e-3);
		TestCase.assertEquals( 1.500, result.vertices[5].x, 1e-3);
		TestCase.assertEquals( 4.750, result.vertices[5].y, 1e-3);
		TestCase.assertEquals( 0.750, result.vertices[6].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[6].y, 1e-3);
		TestCase.assertEquals(-0.833, result.vertices[7].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[7].y, 1e-3);
		TestCase.assertEquals(-2.172, result.vertices[8].x, 1e-3);
		TestCase.assertEquals( 4.598, result.vertices[8].y, 1e-3);
		TestCase.assertEquals(-2.750, result.vertices[9].x, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[9].y, 1e-3);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where a vertex
	 * of one of the polygons (B) is coincident with a vertex of the other (A).
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionCoincidentVertexB() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(1.0, 0.0),
				new Vector2(3.0, 1.0),
				new Vector2(3.25, 3.75),
				new Vector2(1.5, 4.75),
				new Vector2(-2.0, 5.0),
				new Vector2(-2.5, 4.5),
				new Vector2(-2.75, 3.25),
				new Vector2(-2.5, 1.5));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertEquals(10, result.vertices.length);
		TestCase.assertEquals(-0.842, result.vertices[0].x, 1e-3);
		TestCase.assertEquals( 0.789, result.vertices[0].y, 1e-3);
		TestCase.assertEquals( 0.461, result.vertices[1].x, 1e-3);
		TestCase.assertEquals( 0.230, result.vertices[1].y, 1e-3);
		TestCase.assertEquals( 2.000, result.vertices[2].x, 1e-3);
		TestCase.assertEquals( 1.000, result.vertices[2].y, 1e-3);
		TestCase.assertEquals( 3.100, result.vertices[3].x, 1e-3);
		TestCase.assertEquals( 2.100, result.vertices[3].y, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[4].x, 1e-3);
		TestCase.assertEquals( 3.750, result.vertices[4].y, 1e-3);
		TestCase.assertEquals( 1.500, result.vertices[5].x, 1e-3);
		TestCase.assertEquals( 4.750, result.vertices[5].y, 1e-3);
		TestCase.assertEquals(-2.000, result.vertices[6].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[6].y, 1e-3);
		TestCase.assertEquals(-2.500, result.vertices[7].x, 1e-3);
		TestCase.assertEquals( 4.500, result.vertices[7].y, 1e-3);
		TestCase.assertEquals(-2.750, result.vertices[8].x, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[8].y, 1e-3);
		TestCase.assertEquals(-2.639, result.vertices[9].x, 1e-3);
		TestCase.assertEquals( 2.474, result.vertices[9].y, 1e-3);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where an edge
	 * of one of the polygons (A) is colinear with an edge of the other (B).
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionColinearEdgeB() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(1.0, 0.0),
				new Vector2(3.0, 1.0),
				new Vector2(3.25, 3.75),
				new Vector2(1.5, 4.75),
				new Vector2(0.5, 5.0),
				new Vector2(-0.5, 5.0),
				new Vector2(-2.5, 4.5),
				new Vector2(-2.75, 3.25),
				new Vector2(-2.5, 1.5));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertEquals(11, result.vertices.length);
		TestCase.assertEquals(-0.842, result.vertices[0].x, 1e-3);
		TestCase.assertEquals( 0.789, result.vertices[0].y, 1e-3);
		TestCase.assertEquals( 0.461, result.vertices[1].x, 1e-3);
		TestCase.assertEquals( 0.230, result.vertices[1].y, 1e-3);
		TestCase.assertEquals( 2.000, result.vertices[2].x, 1e-3);
		TestCase.assertEquals( 1.000, result.vertices[2].y, 1e-3);
		TestCase.assertEquals( 3.100, result.vertices[3].x, 1e-3);
		TestCase.assertEquals( 2.100, result.vertices[3].y, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[4].x, 1e-3);
		TestCase.assertEquals( 3.750, result.vertices[4].y, 1e-3);
		TestCase.assertEquals( 1.500, result.vertices[5].x, 1e-3);
		TestCase.assertEquals( 4.750, result.vertices[5].y, 1e-3);
		TestCase.assertEquals( 0.500, result.vertices[6].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[6].y, 1e-3);
		TestCase.assertEquals(-0.500, result.vertices[7].x, 1e-3);
		TestCase.assertEquals( 5.000, result.vertices[7].y, 1e-3);
		TestCase.assertEquals(-2.500, result.vertices[8].x, 1e-3);
		TestCase.assertEquals( 4.500, result.vertices[8].y, 1e-3);
		TestCase.assertEquals(-2.750, result.vertices[9].x, 1e-3);
		TestCase.assertEquals( 3.250, result.vertices[9].y, 1e-3);
		TestCase.assertEquals(-2.639, result.vertices[10].x, 1e-3);
		TestCase.assertEquals( 2.474, result.vertices[10].y, 1e-3);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where only the
	 * edges of the polygons are touching.
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionEdgeTouching() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(-1.0, 5.0),
				new Vector2(1.0, 5.0),
				new Vector2(1.0, 6.0),
				new Vector2(-1.0, 6.0));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNull(result);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where only a
	 * single vertex of the polygons are touching.
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionVertexTouching() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(2.0, 5.0),
				new Vector2(4.0, 5.0),
				new Vector2(4.0, 6.0),
				new Vector2(2.0, 6.0));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNull(result);
	}
	
	/**
	 * Tests polygon intersection under the degenerate case where only a
	 * vertex of the polygons are touching along edges that are coincident.
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionVertexTouchingCoincident() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(2.0, 5.0),
				new Vector2(4.0, 5.0),
				new Vector2(1.0, 6.0),
				new Vector2(-1.0, 6.0));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNull(result);
	}

	/**
	 * Tests polygon intersection in the case of separation (no intersection)
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionNoIntersection() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(3.0, 5.0),
				new Vector2(5.0, 5.0),
				new Vector2(5.0, 6.0),
				new Vector2(3.0, 6.0));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNull(result);
	}

	/**
	 * Tests polygon intersection when one shape is completely contained in the other.
	 * @since 4.2.1
	 */
	@Test
	public void polygonIntersectionContainment() {
		Polygon p1 = new Polygon(
				new Vector2(0.0, 0.0),
				new Vector2(2.0, 1.0),
				new Vector2(4.0, 3.0),
				new Vector2(4.25, 4.0),
				new Vector2(2.0, 5.0),
				new Vector2(-2.0, 5.0),
				new Vector2(-4.0, 3.75));
		Polygon p2 = new Polygon(
				new Vector2(-1.0, 2.0),
				new Vector2( 1.0, 2.0),
				new Vector2( 1.0, 3.0),
				new Vector2(-1.0, 3.0));
		Transform tx1 = new Transform();
		Transform tx2 = new Transform();
		
		Polygon result = Geometry.getIntersection(p1, tx1, p2, tx2);
		
		TestCase.assertNotNull(result);
		TestCase.assertSame(result, p2);
	}

	/**
	 * Tests the getRotationRadius methods.
	 * @since 4.2.2
	 */
	@Test
	public void getRotationRadius() {
		Polygon p1 = Geometry.createUnitCirclePolygon(5, 0.5);
		
		double r = Geometry.getRotationRadius(p1.vertices);
		TestCase.assertEquals(0.500, r, 1e-3);
		
		r = Geometry.getRotationRadius(new Vector2(1.0, 0.0), new Vector2[] { new Vector2(-0.5, 0.0) });
		TestCase.assertEquals(1.500, r, 1e-3);
		
		r = Geometry.getRotationRadius(new Vector2(-1.0, 0.0), p1.vertices);
		TestCase.assertEquals(1.500, r, 1e-3);
		
		r = Geometry.getRotationRadius((Vector2[])null);
		TestCase.assertEquals(0.000, r, 1e-3);
		
		r = Geometry.getRotationRadius(new Vector2[0]);
		TestCase.assertEquals(0.000, r, 1e-3);
		
		r = Geometry.getRotationRadius(null, new Vector2[0]);
		TestCase.assertEquals(0.000, r, 1e-3);
		
		r = Geometry.getRotationRadius(new Vector2(1.0, 0.0), new Vector2[] { new Vector2(), null, new Vector2() });
		TestCase.assertEquals(1.000, r, 1e-3);
	}
	
	/**
	 * Tests the getCounterClockwiseEdgeNormals method.
	 * @since 4.2.2
	 */
	@Test
	public void getCounterClockwiseEdgeNormals() {
		Polygon p1 = Geometry.createUnitCirclePolygon(4, 0.5);
		
		Vector2[] normals = Geometry.getCounterClockwiseEdgeNormals(p1.vertices);
		
		TestCase.assertEquals(0.707, normals[0].x, 1e-3);
		TestCase.assertEquals(0.707, normals[0].y, 1e-3);
		TestCase.assertEquals(-0.707, normals[1].x, 1e-3);
		TestCase.assertEquals(0.707, normals[1].y, 1e-3);
		TestCase.assertEquals(-0.707, normals[2].x, 1e-3);
		TestCase.assertEquals(-0.707, normals[2].y, 1e-3);
		TestCase.assertEquals(0.707, normals[3].x, 1e-3);
		TestCase.assertEquals(-0.707, normals[3].y, 1e-3);
		
		normals = Geometry.getCounterClockwiseEdgeNormals((Vector2[])null);
		TestCase.assertEquals(null, normals);
		
		normals = Geometry.getCounterClockwiseEdgeNormals(new Vector2[0]);
		TestCase.assertEquals(null, normals);
	}
	
	/**
	 * Tests the getCounterClockwiseEdgeNormals method with a null element.
	 * @since 4.2.2
	 */
	@Test
	public void getCounterClockwiseEdgeNormalsWithNullVertex() {
		assertThrows(NullPointerException.class, () -> {
			Geometry.getCounterClockwiseEdgeNormals(new Vector2[] {
					new Vector2(1.0, 1.0),
					new Vector2(2.0, 3.0),
					new Vector2(),
					null,
					new Vector2(3.0, -1.0)
			});
		});

	}
}
