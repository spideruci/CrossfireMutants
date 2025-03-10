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
package org.dyn4j.dynamics.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dyn4j.collision.CollisionBody;
import org.dyn4j.collision.CollisionItem;
import org.dyn4j.collision.CollisionPair;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.manifold.ManifoldPoint;
import org.dyn4j.collision.manifold.ManifoldPointId;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.PhysicsBody;
import org.dyn4j.dynamics.Settings;
import org.dyn4j.exception.ValueOutOfRangeException;
import org.dyn4j.geometry.Matrix22;
import org.dyn4j.geometry.Shiftable;
import org.dyn4j.geometry.Vector2;

/**
 * Represents a {@link SolvableContact} constraint for each {@link PhysicsBody} pair.  
 * @author William Bittle
 * @version 5.0.0
 * @since 1.0.0
 * @param <T> The {@link PhysicsBody} type
 */
public final class ContactConstraint<T extends PhysicsBody> implements Shiftable {
	/** The collision pair */
	protected final CollisionPair<CollisionItem<T, BodyFixture>> pair;
	
	/** The {@link Contact}s */
	protected final List<SolvableContact> contacts;
	
	/** An unmodifiable view of the {@link Contact}s */
	protected final List<SolvableContact> contactsUnmodifiable;
	
	/** The penetration normal */
	protected final Vector2 normal;
	
	/** The tangent of the normal */
	protected final Vector2 tangent;
	
	/** The coefficient of friction */
	protected double friction;
	
	/** The coefficient of restitution */
	protected double restitution;
	
	/** The minimum velocity at which to apply restitution */
	protected double restitutionVelocity;

	/** Whether the contact is a sensor contact or not */
	protected boolean sensor;
	
	/** The surface speed of the contact patch */
	protected double tangentSpeed;
	
	/** True if the contact should be evaluated */
	protected boolean enabled;
	
	/** The number of contacts to solve */
	protected int size;
	
	/** The K matrix for block solving a contact pair */
	Matrix22 K;
	
	/** The inverse of the {@link #K} matrix */
	Matrix22 invK;
	
	/**
	 * Full constructor.
	 * @param pair the pair
	 */
	public ContactConstraint(CollisionPair<CollisionItem<T, BodyFixture>> pair) {
		// set the pair
		this.pair = pair;
		// create contact array
		this.contacts = new ArrayList<SolvableContact>(2);
		this.contactsUnmodifiable = Collections.unmodifiableList(this.contacts);
		// set the normal
		this.normal = new Vector2();
		// set the tangent
		this.tangent = new Vector2();
		// set coefficients
		this.friction = BodyFixture.DEFAULT_FRICTION;
		this.restitution = BodyFixture.DEFAULT_RESTITUTION;
		this.restitutionVelocity = BodyFixture.DEFAULT_RESTITUTION_VELOCITY;
		// set the sensor flag (if either fixture is a sensor then the
		// contact constraint between the fixtures is a sensor)
		this.sensor = false;
		// by default the tangent speed is zero
		this.tangentSpeed = 0;
		this.enabled = true;
		this.size = 0;
	}
	
	/**
	 * Updates this {@link ContactConstraint} with the new {@link Manifold} information and reports
	 * begin, persist, and end events to the handler.
	 * @param manifold the new manifold
	 * @param settings the settings
	 * @param handler the handler
	 */
	public void update(Manifold manifold, Settings settings, ContactUpdateHandler handler) {
		double maxWarmStartDistanceSquared = settings.getMaximumWarmStartDistanceSquared();
		boolean isWarmStartEnabled = settings.isWarmStartingEnabled();
		
		T body1 = this.pair.getFirst().getBody();
		T body2 = this.pair.getSecond().getBody();
		BodyFixture fixture1 = this.pair.getFirst().getFixture();
		BodyFixture fixture2 = this.pair.getSecond().getFixture();
		
		// reset all other data
		// NOTE: we need to do this before any listeners are called because the user
		// may want to update some of these and we don't want to reset them
		Vector2 normal = manifold.getNormal();
		this.normal.x = normal.x;
		this.normal.y = normal.y;
		
		// inlined this.normal.getLeftHandOrthogonalVector();
		this.tangent.x = normal.y;
		this.tangent.y = -normal.x;
		
		// get mixed values from the two colliding fixtures
		this.friction = handler.getFriction(fixture1, fixture2);
		this.restitution = handler.getRestitution(fixture1, fixture2);
		this.restitutionVelocity = handler.getRestitutionVelocity(fixture1, fixture2);
		this.sensor = fixture1.isSensor() || fixture2.isSensor();
		
		this.tangentSpeed = 0;
		this.enabled = true;
		
		List<ManifoldPoint> points = manifold.getPoints();
		// get the manifold point size
		int mSize = points.size();
		// create contact array
		List<SolvableContact> contacts = new ArrayList<SolvableContact>(mSize);
		// create contacts for each point
		for (int l = 0; l < mSize; l++) {
			// get the manifold point
			ManifoldPoint point = points.get(l);
			// create a contact from the manifold point
			SolvableContact newContact = new SolvableContact(point.getId(),
                  point.getPoint(), 
                  point.getDepth(), 
                  body1.getLocalPoint(point.getPoint()), 
                  body2.getLocalPoint(point.getPoint()));
			// add the contact to the array
			contacts.add(newContact);
			
			// find a matching contact
			boolean found = false;
			int cSize = this.contacts.size();
			for (int j = cSize - 1; j >= 0; j--) {
				SolvableContact oldContact = this.contacts.get(j);
				if ((newContact.id == ManifoldPointId.DISTANCE && newContact.p.distanceSquared(oldContact.p) <= maxWarmStartDistanceSquared) || newContact.id.equals(oldContact.id)) {
					found = true;
					// notify that this contact was persisted from
					// an existing contact
					handler.persist(oldContact, newContact);
					
					// only warm start if it's enabled
					if (isWarmStartEnabled) {
						// copy last time's data over
						newContact.jn = oldContact.jn;
						newContact.jt = oldContact.jt;
					}
					
					// remove this contact from the current list
					// so that only "end" contacts are left in the list
					this.contacts.remove(oldContact);
					break;
				}
			}
			
			if (!found) {
				// notify that this contact is new
				handler.begin(newContact);
			}
		}
		
		// notify of contacts that have "ended"
		int cSize = this.contacts.size();
		for (int j = cSize - 1; j >= 0; j--) {
			SolvableContact oldContact = this.contacts.get(j);
			handler.end(oldContact);
		}
		
		// clear all the old contacts and add
		// the new ones
		this.contacts.clear();
		this.contacts.addAll(contacts);
		this.size = this.contacts.size();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ContactConstraint[").append(super.toString())
		  .append("|Body1=").append(this.pair.getFirst().getBody().hashCode())
		  .append("|Fixture1=").append(this.pair.getFirst().getFixture().hashCode())
		  .append("|Body2=").append(this.pair.getSecond().getBody().hashCode())
		  .append("|Fixture2=").append(this.pair.getSecond().getFixture().hashCode())
		  .append("|Normal=").append(this.normal)
		  .append("|Tangent=").append(this.tangent)
		  .append("|Friction=").append(this.friction)
		  .append("|Restitution=").append(this.restitution)
		  .append("|RestitutionVelocity=").append(this.restitutionVelocity)
		  .append("|IsSensor=").append(this.sensor)
		  .append("|TangentSpeed=").append(this.tangentSpeed)
		  .append("|Enabled=").append(this.enabled)
		  .append("|Contacts={");
		int size = contacts.size();
		for (int i = 0; i < size; i++) {
			if (i != 0) sb.append(",");
			sb.append(this.contacts.get(i));
		}
		sb.append("}]");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shiftable#shift(org.dyn4j.geometry.Vector2)
	 */
	@Override
	public void shift(Vector2 shift) {
		int size = this.contacts.size();
		// loop over the contacts
		for (int i = 0; i < size; i++) {
			SolvableContact c = this.contacts.get(i);
			// translate the world space contact point
			c.p.add(shift);
			// c.p1 and c.p2 are in local coordinates
			// and don't need to be shifted
		}
	}
	
	/**
	 * Returns the collision normal.
	 * @return {@link Vector2} the collision normal
	 */
	public Vector2 getNormal() {
		return this.normal;
	}
	
	/**
	 * Returns the collision tangent.
	 * @return {@link Vector2} the collision tangent
	 */
	public Vector2 getTangent() {
		return this.tangent;
	}
	
	/**
	 * Returns the list of {@link SolvableContact}s.
	 * <p>
	 * Modification of the list is permitted.
	 * @return List&lt;{@link SolvableContact}&gt; the list of {@link SolvableContact}s
	 */
	public List<? extends SolvedContact> getContacts() {
		return this.contactsUnmodifiable;
	}
	
	/**
	 * Returns the {@link CollisionPair}.
	 * @return {@link CollisionPair}
	 * @since 4.0.0
	 */
	public CollisionPair<CollisionItem<T, BodyFixture>> getCollisionPair() {
		return this.pair;
	}
	
	/**
	 * Returns the first {@link PhysicsBody}.
	 * @return {@link PhysicsBody}
	 */
	public T getBody1() {
		return this.pair.getFirst().getBody();
	}
	
	/**
	 * Returns the second {@link PhysicsBody}.
	 * @return {@link PhysicsBody}
	 */
	public T getBody2() {
		return this.pair.getSecond().getBody();
	}

	/**
	 * Returns the first {@link PhysicsBody}'s {@link BodyFixture}.
	 * @return {@link BodyFixture} the first {@link PhysicsBody}'s {@link BodyFixture}
	 */
	public BodyFixture getFixture1() {
		return this.pair.getFirst().getFixture();
	}
	
	/**
	 * Returns the second {@link PhysicsBody}'s {@link BodyFixture}.
	 * @return {@link BodyFixture} the second {@link PhysicsBody}'s {@link BodyFixture}
	 */
	public BodyFixture getFixture2() {
		return this.pair.getSecond().getFixture();
	}
	
	/**
	 * Returns the body that matches the given body.
	 * <p>
	 * If the given body is neither body1 or body2, null is returned.
	 * @param body the body
	 * @return T
	 */
	public T getBody(CollisionBody<?> body) {
		T body1 = this.pair.getFirst().getBody();
		T body2 = this.pair.getSecond().getBody();
		if (body1 == body) {
			return body1;
		} else if (body2 == body) {
			return body2;
		}
		return null;
	}
	
	/**
	 * Returns the fixture for the body that matches the given body.
	 * <p>
	 * If the given body is neither body1 or body2, null is returned.
	 * @param body the body
	 * @return E
	 */
	public BodyFixture getFixture(CollisionBody<?> body) {
		T body1 = this.pair.getFirst().getBody();
		T body2 = this.pair.getSecond().getBody();
		if (body1 == body) {
			return this.pair.getFirst().getFixture();
		} else if (body2 == body) {
			return this.pair.getSecond().getFixture();
		}
		return null;
	}
	
	/**
	 * Returns the body that does not match the given body.
	 * <p>
	 * If the given body is neither body1 or body2, null is returned.
	 * @param body the body
	 * @return T
	 */
	public T getOtherBody(CollisionBody<?> body) {
		T body1 = this.pair.getFirst().getBody();
		T body2 = this.pair.getSecond().getBody();
		if (body1 == body) {
			return body2;
		} else if (body2 == body) {
			return body1;
		}
		return null;
	}
	
	/**
	 * Returns the fixture for the body that does not match the given body.
	 * <p>
	 * If the given body is neither body1 or body2, null is returned.
	 * @param body the body
	 * @return E
	 */
	public BodyFixture getOtherFixture(CollisionBody<?> body) {
		T body1 = this.pair.getFirst().getBody();
		T body2 = this.pair.getSecond().getBody();
		if (body1 == body) {
			return this.pair.getSecond().getFixture();
		} else if (body2 == body) {
			return this.pair.getFirst().getFixture();
		}
		return null;
	}
	
	/**
	 * Returns the coefficient of friction for this contact constraint.
	 * @return double
	 */
	public double getFriction() {
		return this.friction;
	}
	
	/**
	 * Sets the coefficient of friction for this contact constraint.
	 * @param friction the friction; must be 0 or greater
	 * @since 3.0.2
	 */
	public void setFriction(double friction) {
		if (friction < 0) 
			throw new ValueOutOfRangeException("friction", friction, ValueOutOfRangeException.MUST_BE_GREATER_THAN_OR_EQUAL_TO, 0.0);
		
		this.friction = friction;
	}
	
	/**
	 * Returns the coefficient of restitution for this contact constraint.
	 * @return double
	 */
	public double getRestitution() {
		return this.restitution;
	}

	/**
	 * Sets the coefficient of restitution for this contact constraint.
	 * @param restitution the restitution; must be zero or greater
	 * @since 3.0.2
	 */
	public void setRestitution(double restitution) {
		if (restitution < 0) 
			throw new ValueOutOfRangeException("restitution", restitution, ValueOutOfRangeException.MUST_BE_GREATER_THAN_OR_EQUAL_TO, 0.0);
		
		this.restitution = restitution;
	}
	
	/**
	 * Returns the minimum velocity required for restitution to be applied.
	 * @return double
	 * @since 4.2.0
	 */
	public double getRestitutionVelocity() {
		return this.restitutionVelocity;
	}
	
	/**
	 * Sets the minimum velocity required for restitution to be applied.
	 * @param restitutionVelocity the restitution velocity
	 * @since 4.2.0
	 */
	public void setRestitutionVelocity(double restitutionVelocity) {
		this.restitutionVelocity = restitutionVelocity;
	}
	
	/**
	 * Returns true if this {@link ContactConstraint} is a sensor.
	 * <p>
	 * By default a contact constraint is a sensor if either of the
	 * two {@link BodyFixture}s are sensor fixtures.  This can be
	 * overridden using the {@link #setSensor(boolean)} method.
	 * @return boolean
	 * @since 1.0.1
	 */
	public boolean isSensor() {
		return this.sensor;
	}
	
	/**
	 * Sets this contact constraint to a sensor if flag is true.
	 * <p>
	 * A sensor constraint is not solved.
	 * @param flag true if this contact constraint should be a sensor
	 * @since 3.0.2
	 */
	public void setSensor(boolean flag) {
		this.sensor = flag;
	}
	
	/**
	 * Returns the surface speed of the contact constraint.
	 * <p>
	 * This will always be zero unless specified manually. This can
	 * be used to set the target velocity at the contact to simulate
	 * a conveyor belt type effect.
	 * @return double
	 * @since 3.0.2
	 */
	public double getTangentSpeed() {
		return this.tangentSpeed;
	}
	
	/**
	 * Sets the target surface speed of the contact constraint.
	 * <p>
	 * The surface speed, in meters / second, is used to simulate a
	 * conveyor belt.
	 * <p>
	 * A value of zero deactivates this feature.
	 * @param speed the speed in Meters / Second
	 * @since 3.0.2
	 */
	public void setTangentSpeed(double speed) {
		this.tangentSpeed = speed;
	}
	
	/**
	 * Sets the enabled flag.
	 * <p>
	 * A value of true would enable the contact to be processed by the
	 * collision resolution step. A value of false would disable the
	 * processing of this constraint for this step only.
	 * <p>
	 * True by default.
	 * @param flag true if the contact should be enabled
	 * @since 3.3.0
	 */
	public void setEnabled(boolean flag) {
		this.enabled = flag;
	}
	
	/**
	 * Returns true if this contact constraint is enabled for processing
	 * by the collision resolution step.
	 * @return boolean
	 * @since 3.3.0
	 */
	public boolean isEnabled() {
		return this.enabled;
	}
}
