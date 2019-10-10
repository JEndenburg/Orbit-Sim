package nl.sogyo.jendenburg.orbitsim.domain.body;

import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class Planet extends Body
{
	private double radius;
	
	public Planet(Vector3 position, Vector3 velocity, double mass, double radius)
	{
		super(position, velocity, mass);
		this.radius = radius;
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public double getGravity()
	{
		return mass / (radius * radius);
	}
}
