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

import junit.framework.TestCase;

import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;

/**
 * Test case for the {@link Polygon} class.
 * @author William Bittle
 * @version 4.2.2
 * @since 1.0.0
 */
public class PolygonTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/** Identity Transform instance */
	private static Transform IDENTITY = new Transform();


	@BeforeEach
	public void setUp() {
		IDENTITY = new Transform();
	}
	/**
	 * Tests not enough points.
	 */
	@Test
	public void createNotEnoughPoints() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Polygon(new Vector2[] {
					new Vector2(),
					new Vector2()
			});
		});

	}
	
	/**
	 * Tests not CCW.
	 */
	@Test
	public void createNotCCW() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Polygon(new Vector2[] {
					new Vector2(),
					new Vector2(2.0, 2.0),
					new Vector2(1.0, 0.0)
			});
		});

	}
	
	/**
	 * Tests that the triangle is CCW.
	 */
	@Test
	public void createCCW() {
		new Polygon(new Vector2[] {
			new Vector2(0.5, 0.5),
			new Vector2(-0.3, -0.5),
			new Vector2(1.0, -0.3)
		});
	}
	
	/**
	 * Tests coincident points
	 */
	@Test
	public void createCoincident() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Polygon(new Vector2[] {
					new Vector2(),
					new Vector2(2.0, 2.0),
					new Vector2(2.0, 2.0),
					new Vector2(1.0, 0.0)
			});
		});

	}
	
	/**
	 * Tests non convex.
	 */
	@Test
	public void createNonConvex() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Polygon(new Vector2[] {
					new Vector2(1.0, 1.0),
					new Vector2(-1.0, 1.0),
					new Vector2(-0.5, 0.0),
					new Vector2(-1.0, -1.0),
					new Vector2(1.0, -1.0)
			});
		});

	}

	/**
	 * Tests degenerate poly.
	 */
	@Test
	public void createDegenerate() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Polygon(new Vector2[] {
					new Vector2(1.0, 0.0),
					new Vector2(2.0, 0.0),
					new Vector2(1.0, 0.0)
			});
		});

	}
	
	/**
	 * Tests null point array.
	 */
	@Test
	public void createNullPoints() {
		assertThrows(NullPointerException.class, () -> {
			new Polygon((Vector2[])null);
		});

	}
	
	/**
	 * Tests an array with null points
	 */
	@Test
	public void createNullPoint() {
		assertThrows(NullPointerException.class, () -> {
			new Polygon(new Vector2[] {
					new Vector2(),
					null,
					new Vector2(0, 2)
			});
		});

	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void createSuccess() {
		new Polygon(new Vector2[] {
			new Vector2(0.0, 1.0),
			new Vector2(-2.0, -2.0),
			new Vector2(1.0, -2.0)
		});
	}
	
	/**
	 * Tests the contains method.
	 */
	@Test
	public void contains() {
		Vector2[] vertices = new Vector2[] {
			new Vector2(0.0, 1.0),
			new Vector2(-1.0, 0.0),
			new Vector2(1.0, 0.0)
		};
		Polygon p = new Polygon(vertices);
		
		Transform t = new Transform();
		Vector2 pt = new Vector2(2.0, 4.0);
		
		// shouldn't be in the polygon
		TestCase.assertTrue(!p.contains(pt, t));
		TestCase.assertTrue(!p.contains(pt, t, false));
		
		// move the polygon a bit
		t.translate(2.0, 3.5);
		
		// should be in the polygon
		TestCase.assertTrue(p.contains(pt, t));
		TestCase.assertTrue(p.contains(pt, t, false));
		
		t.translate(0.0, -0.5);
		
		// should be on a vertex
		TestCase.assertTrue(p.contains(pt, t));
		TestCase.assertFalse(p.contains(pt, t, false));

		t.translate(0.5, 0.5);
		
		// should be on an edge
		TestCase.assertTrue(p.contains(pt, t));
		TestCase.assertFalse(p.contains(pt, t, false));
		
		t.translate(-1.0, -1.0);
		
		// should be outside, but colinear
		TestCase.assertFalse(p.contains(pt, t));
		TestCase.assertFalse(p.contains(pt, t, false));
	}
	
	/**
	 * Tests the project method.
	 */
	@Test
	public void project() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, 0.0),
				new Vector2(1.0, 0.0)
			};
		Polygon p = new Polygon(vertices);
		Transform t = new Transform();
		Vector2 x = new Vector2(1.0, 0.0);
		Vector2 y = new Vector2(0.0, 1.0);
		
		t.translate(1.0, 0.5);
		
		Interval i = p.project(x, t);
		
		TestCase.assertEquals(0.000, i.min, 1.0e-3);
		TestCase.assertEquals(2.000, i.max, 1.0e-3);
		
		// rotating about the center
		t.rotate(Math.toRadians(90), 1.0, 0.5);
		
		i = p.project(y, t);
		
		TestCase.assertEquals(-0.500, i.min, 1.0e-3);
		TestCase.assertEquals(1.500, i.max, 1.0e-3);
	}
	
	/**
	 * Tests the farthest methods.
	 */
	@Test
	public void getFarthest() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, -1.0),
				new Vector2(1.0, -1.0)
			};
		Polygon p = new Polygon(vertices);
		Transform t = new Transform();
		Vector2 y = new Vector2(0.0, -1.0);
		
		EdgeFeature f = p.getFarthestFeature(y, t);
		// should always get an edge
		TestCase.assertEquals(-1.000, f.max.point.x, 1.0e-3);
		TestCase.assertEquals(-1.000, f.max.point.y, 1.0e-3);
		TestCase.assertEquals(-1.000, f.vertex1.point.x, 1.0e-3);
		TestCase.assertEquals(-1.000, f.vertex1.point.y, 1.0e-3);
		TestCase.assertEquals( 1.000, f.vertex2.point.x, 1.0e-3);
		TestCase.assertEquals(-1.000, f.vertex2.point.y, 1.0e-3);
		
		Vector2 pt = p.getFarthestPoint(y, t);
		
		TestCase.assertEquals(-1.000, pt.x, 1.0e-3);
		TestCase.assertEquals(-1.000, pt.y, 1.0e-3);
		
		// rotating about the origin
		t.rotate(Math.toRadians(90), 0, 0);
		
		pt = p.getFarthestPoint(y, t);
		
		TestCase.assertEquals( 1.000, pt.x, 1.0e-3);
		TestCase.assertEquals(-1.000, pt.y, 1.0e-3);
	}
	
	/**
	 * Tests the getAxes method.
	 */
	@Test
	public void getAxes() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, -1.0),
				new Vector2(1.0, -1.0)
			};
		Polygon p = new Polygon(vertices);
		Transform t = new Transform();
		
		Vector2[] axes = p.getAxes(null, t);
		TestCase.assertNotNull(axes);
		TestCase.assertEquals(3, axes.length);
		
		// test passing some focal points
		Vector2 pt = new Vector2(-3.0, 2.0);
		axes = p.getAxes(new Vector2[] {pt}, t);
		TestCase.assertEquals(4, axes.length);
		
		// make sure the axes are perpendicular to the edges
		Vector2 ab = p.vertices[0].to(p.vertices[1]);
		Vector2 bc = p.vertices[1].to(p.vertices[2]);
		Vector2 ca = p.vertices[2].to(p.vertices[0]);
		
		TestCase.assertEquals(0.000, ab.dot(axes[0]), 1.0e-3);
		TestCase.assertEquals(0.000, bc.dot(axes[1]), 1.0e-3);
		TestCase.assertEquals(0.000, ca.dot(axes[2]), 1.0e-3);
		
		// make sure that the focal axes are correct
		TestCase.assertEquals(0.000, p.vertices[0].to(pt).cross(axes[3]), 1.0e-3);
	}
	
	/**
	 * Tests the getFoci method.
	 */
	@Test
	public void getFoci() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, -1.0),
				new Vector2(1.0, -1.0)
			};
		Polygon p = new Polygon(vertices);
		Transform t = new Transform();
		// should return none
		Vector2[] foci = p.getFoci(t);
		TestCase.assertNull(foci);
	}
	
	/**
	 * Tests the rotate methods.
	 */
	@Test
	public void rotate() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, -1.0),
				new Vector2(1.0, -1.0)
			};
		Polygon p = new Polygon(vertices);
		
		// should move the points
		p.rotate(Math.toRadians(90), 0, 0);
		
		TestCase.assertEquals(-1.000, p.vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 0.000, p.vertices[0].y, 1.0e-3);
		
		TestCase.assertEquals( 1.000, p.vertices[1].x, 1.0e-3);
		TestCase.assertEquals(-1.000, p.vertices[1].y, 1.0e-3);
		
		TestCase.assertEquals( 1.000, p.vertices[2].x, 1.0e-3);
		TestCase.assertEquals( 1.000, p.vertices[2].y, 1.0e-3);
	}
	
	/**
	 * Tests the translate methods.
	 */
	@Test
	public void translate() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, -1.0),
				new Vector2(1.0, -1.0)
			};
		Polygon p = new Polygon(vertices);
		
		p.translate(1.0, -0.5);
		
		TestCase.assertEquals( 1.000, p.vertices[0].x, 1.0e-3);
		TestCase.assertEquals( 0.500, p.vertices[0].y, 1.0e-3);
		
		TestCase.assertEquals( 0.000, p.vertices[1].x, 1.0e-3);
		TestCase.assertEquals(-1.500, p.vertices[1].y, 1.0e-3);
		
		TestCase.assertEquals( 2.000, p.vertices[2].x, 1.0e-3);
		TestCase.assertEquals(-1.500, p.vertices[2].y, 1.0e-3);
	}
	
	/**
	 * Tests the createAABB method.
	 * @since 3.1.0
	 */
	@Test
	public void createAABB() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 1.0),
				new Vector2(-1.0, -1.0),
				new Vector2(1.0, -1.0)
			};
		Polygon p = new Polygon(vertices);
		
		AABB aabb = p.createAABB(IDENTITY);
		TestCase.assertEquals(-1.0, aabb.getMinX(), 1.0e-3);
		TestCase.assertEquals(-1.0, aabb.getMinY(), 1.0e-3);
		TestCase.assertEquals( 1.0, aabb.getMaxX(), 1.0e-3);
		TestCase.assertEquals( 1.0, aabb.getMaxY(), 1.0e-3);
		
		// try using the default method
		AABB aabb2 = p.createAABB();
		TestCase.assertEquals(aabb.getMinX(), aabb2.getMinX());
		TestCase.assertEquals(aabb.getMinY(), aabb2.getMinY());
		TestCase.assertEquals(aabb.getMaxX(), aabb2.getMaxX());
		TestCase.assertEquals(aabb.getMaxY(), aabb2.getMaxY());
		
		Transform tx = new Transform();
		tx.rotate(Math.toRadians(30.0));
		tx.translate(1.0, 2.0);
		aabb = p.createAABB(tx);
		TestCase.assertEquals( 0.500, aabb.getMinX(), 1.0e-3);
		TestCase.assertEquals( 0.634, aabb.getMinY(), 1.0e-3);
		TestCase.assertEquals( 2.366, aabb.getMaxX(), 1.0e-3);
		TestCase.assertEquals( 2.866, aabb.getMaxY(), 1.0e-3);
	}

	/**
	 * Tests the contains method against a point that is coincident with
	 * the starting edge of the polygon.
	 */
	@Test
	public void containsPointCoIncidentStart() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(2.0, 0.0), 
				new Vector2(4.0, 0.0), 
				new Vector2(7.0, 3.0), 
				new Vector2(7.0, 5.0), 
				new Vector2(5.0, 7.0), 
				new Vector2(3.0, 7.0), 
				new Vector2(0.0, 4.0), 
				new Vector2(0.0, 2.0)
			};
		Polygon p = new Polygon(vertices);
		
		TestCase.assertFalse(p.contains(new Vector2(0.0, 0.0)));
	}
	
	/**
	 * Tests the contains method against a point that is coincident with
	 * the starting edge of the polygon.
	 */
	@Test
	public void containsPointCoIncidentMid() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(0.0, 4.0),
				new Vector2(0.0, 2.0),
				new Vector2(2.0, 0.0), 
				new Vector2(4.0, 0.0), 
				new Vector2(7.0, 3.0), 
				new Vector2(7.0, 5.0), 
				new Vector2(5.0, 7.0), 
				new Vector2(3.0, 7.0) 
			};
		Polygon p = new Polygon(vertices);
		
		TestCase.assertFalse(p.contains(new Vector2(0.0, 0.0)));
	}
	
	/**
	 * Tests the contains method against a point that is coincident with
	 * the starting edge of a polygon that has two coincident edges.
	 */
	@Test
	public void containsPointCoIncidentWithCoincidentEdges() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(2.0, 0.0), 
				new Vector2(4.0, 0.0), 
				new Vector2(5.0, 0.0), 
				new Vector2(7.0, 3.0), 
				new Vector2(7.0, 5.0), 
				new Vector2(5.0, 7.0), 
				new Vector2(3.0, 7.0), 
				new Vector2(0.0, 4.0), 
				new Vector2(0.0, 2.0)
			};
		Polygon p = new Polygon(vertices);
		
		TestCase.assertFalse(p.contains(new Vector2(0.0, 0.0)));
	}
	
	/**
	 * Tests the contains method against a point that is coincident with
	 * the starting edge of a polygon that has two coincident edges.
	 */
	@Test
	public void containsPointCoIncidentWithCoincidentEdges2() {
		Vector2[] vertices = new Vector2[] {
				new Vector2(2.0, 0.0), 
				new Vector2(4.0, 0.0), 
				new Vector2(5.0, 0.0), 
				new Vector2(7.0, 3.0), 
				new Vector2(7.0, 5.0), 
				new Vector2(5.0, 7.0), 
				new Vector2(3.0, 7.0), 
				new Vector2(0.0, 4.0), 
				new Vector2(0.0, 2.0)
			};
		Polygon p = new Polygon(vertices);
		
		TestCase.assertTrue(p.contains(new Vector2(4.5, 0.0)));
	}
	
	/**
	 * Tests the getNormals and getNormalIterator methods.
	 */
	@Test
	public void getNormals() {
		Vector2[] vertices = new Vector2[] {
			new Vector2(0.0, 1.0),
			new Vector2(-1.0, 0.0),
			new Vector2(1.0, 0.0)
		};
		Polygon p = new Polygon(vertices);
		
		Vector2[] normals = p.getNormals();
		
		TestCase.assertNotNull(normals);
		TestCase.assertEquals(3, normals.length);
		TestCase.assertEquals(-0.707, normals[0].x, 1e-3);
		TestCase.assertEquals( 0.707, normals[0].y, 1e-3);
		TestCase.assertEquals( 0.0, normals[1].x, 1e-3);
		TestCase.assertEquals(-1.0, normals[1].y, 1e-3);
		TestCase.assertEquals( 0.707, normals[2].x, 1e-3);
		TestCase.assertEquals( 0.707, normals[2].y, 1e-3);
		
		Iterator<Vector2> iterator = p.getNormalIterator();
		
		TestCase.assertNotNull(iterator);
		TestCase.assertEquals(WoundIterator.class, iterator.getClass());
		while (iterator.hasNext()) {
			iterator.next();
		}
	}

	/**
	 * Test case for the polygon createMass method.
	 */
	@Test
	public void createMass() {
		Polygon p = Geometry.createUnitCirclePolygon(5, 0.5);
		Mass m = p.createMass(1.0);
		// the polygon mass should be the area * d
		TestCase.assertEquals(0.594, m.getMass(), 1.0e-3);
		TestCase.assertEquals(0.057, m.getInertia(), 1.0e-3);
		
		m = p.createMass(0);
		TestCase.assertEquals(0.000, m.getMass(), 1e-3);
		TestCase.assertEquals(0.000, m.getInertia(), 1e-3);
		TestCase.assertEquals(0.000, m.getInverseMass(), 1e-3);
		TestCase.assertEquals(0.000, m.getInverseInertia(), 1e-3);
		TestCase.assertEquals(0.000, m.getCenter().x, 1e-3);
		TestCase.assertEquals(0.000, m.getCenter().y, 1e-3);
		TestCase.assertEquals(MassType.INFINITE, m.getType());
	}

	/**
	 * Test case for the polygon getArea method.
	 */
	@Test
	public void getArea() {
		Polygon p = Geometry.createUnitCirclePolygon(5, 0.5);
		TestCase.assertEquals(0.594, p.getArea(), 1.0e-3);
	}
	
}
