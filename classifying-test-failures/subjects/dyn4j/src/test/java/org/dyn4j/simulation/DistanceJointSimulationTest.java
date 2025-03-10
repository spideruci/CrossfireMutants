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
package org.dyn4j.simulation;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.DistanceJoint;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

/**
 * Used to test the {@link DistanceJoint} class in simulation.
 * @author William Bittle
 * @version 4.2.0
 * @since 4.0.0
 */
public class DistanceJointSimulationTest {
	@BeforeAll
	public static void myBeforeAll() {

	}

	@AfterAll
	public static void myAfterAll() {

	}
	/**
	 * Tests the body separation as enforced by the distance joint.
	 */
	@Test
	public void fixedDistance() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		dj.setRestDistance(10.0);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		// the way the distance joint is currently working is that it will immediately try to solve
		// it to be the correct distance apart, but the position solver is bound by the default
		// correction factor, which is 0.2m. The world is also specified to run 10 position solving
		// iterations as well so this accounts for +2m difference each iteration, so after 4 iterations
		// we should be at 10m (we'll set these defaults below just in case they change in the future)
		w.getSettings().setMaximumLinearCorrection(0.2);
		w.getSettings().setPositionConstraintSolverIterations(10);
		w.step(4);
		
		double invdt = w.getTimeStep().getInverseDeltaTime();
		
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(v1.distance(v2), dj.getRestDistance(), 1e-5);	
		// always zero
		TestCase.assertEquals(0.0, dj.getReactionTorque(invdt));
		// position correction doesn't store impulses
		TestCase.assertEquals(0.0, dj.getReactionForce(invdt).x, 1e-3);
		TestCase.assertEquals(0.0, dj.getReactionForce(invdt).y, 1e-3);
		TestCase.assertEquals(0.0, dj.getSpringForce(invdt), 1e-3);
	}
	
	/**
	 * Tests the body separation as enforced by the distance joint with limits
	 */
	@Test
	public void limits() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		// NOTE: that I've set the rest distance to more than the limits
		dj.setRestDistance(10.0);
		dj.setLimitsEnabled(1.0, 5.0);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		w.step(1);
		
		// since the bodies are already 2.0 units apparent, nothing should happen
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2), 1e-5);
		
		dj.setLimitsEnabled(6.0, 7.0);
		w.step(1);
		
		// the bodies should be placed at the lower limit
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(6.0, v1.distance(v2), 1e-5);
		
		dj.setLimitsEnabled(1.0, 3.0);
		w.step(1);
		
		// the bodies should be placed at the upper limit
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(3.0, v1.distance(v2), 1e-5);
		
		dj.setLimitsEnabled(5.0, 10.0);
		dj.setLowerLimitEnabled(false);
		w.step(1);
		
		// the distance will be outside the limits, but because the lower
		// limit is disabled it should keep them at 3.0
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(3.0, v1.distance(v2), 1e-5);

		dj.setLimitsEnabled(0.0, 2.0);
		dj.setUpperLimitEnabled(false);
		w.step(1);
		
		// the distance will be outside the limits, but because the upper
		// limit is disabled it should keep them at 3.0
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(3.0, v1.distance(v2), 1e-5);
	}

	/**
	 * Tests the body separation as enforced by the distance joint with limits with a spring.
	 */
	@Test
	public void upperLimitWithSpring() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		// NOTE: that I've set the rest distance to more than the limits
		dj.setRestDistance(10.0);
		dj.setLimitsEnabled(1.0, 5.0);
		dj.setSpringEnabled(true);
		dj.setSpringFrequency(8.0);
		dj.setSpringDamperEnabled(true);
		dj.setSpringDampingRatio(0.2);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		w.step(10);
		
		// the spring, because the rest distance is 10, should push them to the
		// upper limit of 5.0
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(5.0, v1.distance(v2), 1e-5);
	}
	
	/**
	 * Tests the body separation as enforced by the distance joint with limits with a spring.
	 */
	@Test
	public void lowerLimitWithSpring() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		// NOTE: that I've set the rest distance to more than the limits
		dj.setRestDistance(1.0);
		dj.setLimitsEnabled(3.0, 5.0);
		dj.setSpringEnabled(true);
		dj.setSpringFrequency(8.0);
		dj.setSpringDamperEnabled(true);
		dj.setSpringDampingRatio(0.2);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		w.step(10);
		
		// the spring, because the rest distance is 1.0, should push them to the
		// lower limit of 3.0
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(3.0, v1.distance(v2), 1e-5);
	}

	/**
	 * Tests the body separation as enforced by the distance joint with limits with a spring.
	 */
	@Test
	public void limitsWithSpring() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		// NOTE: that I've set the rest distance to more than the limits
		dj.setRestDistance(3.0);
		dj.setLimitsEnabled(1.0, 5.0);
		dj.setSpringEnabled(true);
		dj.setSpringFrequency(8.0);
		dj.setSpringDamperEnabled(true);
		dj.setSpringDampingRatio(0.2);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		w.step(30);
		
		// the spring, because the rest distance is 3, should push them to the
		// 3.0 since it's between the upper and lower limit
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(3.0, v1.distance(v2), 1e-3);
	}
	
	/**
	 * Tests the body separation as enforced by the distance joint with limits with a spring.
	 */
	@Test
	public void springOnly() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		// NOTE: that I've set the rest distance to more than the limits
		dj.setRestDistance(3.0);
		dj.setSpringEnabled(true);
		dj.setSpringFrequency(8.0);
		dj.setSpringDamperEnabled(true);
		dj.setSpringDampingRatio(0.2);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		w.step(30);
		
		double invdt = w.getTimeStep().getInverseDeltaTime();
		
		// the spring, because the rest distance is 3, should push them to the
		// 3.0 since it's between the upper and lower limit
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertEquals(3.0, v1.distance(v2), 1e-3);
		// always zero
		TestCase.assertEquals(0.0, dj.getReactionTorque(invdt));
		// position correction doesn't store impulses
		TestCase.assertEquals(0.0, dj.getReactionForce(invdt).x, 1e-3);
		TestCase.assertEquals(-0.045, dj.getReactionForce(invdt).y, 1e-3);
	}
	

	/**
	 * Tests the body with a spring and maximum force.
	 */
	@Test
	public void springWithMaxForce() {
		World<Body> w = new World<Body>();
		// take gravity out the picture
		w.setGravity(World.ZERO_GRAVITY);
		
		// take friction and damping out of the picture
		
		Body g = new Body();
		BodyFixture gf = g.addFixture(Geometry.createRectangle(10.0, 0.5));
		gf.setFriction(0.0);
		g.setMass(MassType.INFINITE);
		g.setLinearDamping(0.0);
		g.setAngularDamping(0.0);
		w.addBody(g);
		
		Body b = new Body();
		BodyFixture bf = b.addFixture(Geometry.createCircle(0.5));
		bf.setFriction(0.0);
		b.setMass(MassType.NORMAL);
		b.translate(0.0, 2.0);
		b.setLinearDamping(0.0);
		b.setAngularDamping(0.0);
		w.addBody(b);
		
		DistanceJoint<Body> dj = new DistanceJoint<Body>(g, b, g.getWorldCenter(), b.getWorldCenter());
		
		// NOTE: that I've set the rest distance to more than the limits
		dj.setRestDistance(3.0);
		dj.setSpringEnabled(true);
		dj.setSpringFrequency(8.0);
		dj.setSpringDamperEnabled(true);
		dj.setSpringDampingRatio(0.2);
		dj.setMaximumSpringForceEnabled(true);
		dj.setMaximumSpringForce(200.0);
		w.addJoint(dj);
		
		Vector2 v1 = g.getWorldCenter();
		Vector2 v2 = b.getWorldCenter();
		TestCase.assertEquals(2.0, v1.distance(v2));
		
		w.step(1);
		
		double invdt = w.getTimeStep().getInverseDeltaTime();
		
		// the spring, because the rest distance is 3, should push them to the
		// 3.0 since it's between the upper and lower limit
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertTrue(v1.distance(v2) > 2.0);
		// always zero
		TestCase.assertEquals(0.0, dj.getReactionTorque(invdt));
		// position correction doesn't store impulses
		TestCase.assertEquals(0.0, dj.getReactionForce(invdt).x, 1e-3);
		TestCase.assertEquals(-200.0, dj.getReactionForce(invdt).y, 1e-3);
		TestCase.assertEquals(200.0, dj.getSpringForce(invdt), 1e-3);
		
		
		// apply limits and it should be the same
		dj.setLimitsEnabled(0.0, 4.0);
		w.step(1);
		
		// the spring, because the rest distance is 3, should push them to the
		// 3.0 since it's between the upper and lower limit
		v1 = g.getWorldCenter();
		v2 = b.getWorldCenter();
		TestCase.assertTrue(v1.distance(v2) > 2.0);
		// always zero
		TestCase.assertEquals(0.0, dj.getReactionTorque(invdt));
		// position correction doesn't store impulses
		TestCase.assertEquals(0.0, dj.getReactionForce(invdt).x, 1e-3);
		TestCase.assertEquals(-200.0, dj.getReactionForce(invdt).y, 1e-3);
	}
}
