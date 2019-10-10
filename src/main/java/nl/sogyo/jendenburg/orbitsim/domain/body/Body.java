package nl.sogyo.jendenburg.orbitsim.domain.body;

import java.time.Duration;

import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public abstract class Body
{
	protected double mass;
	protected Vector3 position;
	protected Vector3 velocity;
	
	public Body(Vector3 position, Vector3 velocity, double mass)
	{
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public Vector3 getPosition()
	{
		return position;
	}
	
	public Vector3 getVelocity()
	{
		return velocity;
	}
	
	public void addVelocity(Vector3 impulse)
	{
		velocity = velocity.add(impulse);
	}
}
