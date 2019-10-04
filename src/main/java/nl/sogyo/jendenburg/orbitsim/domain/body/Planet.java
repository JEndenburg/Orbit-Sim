package nl.sogyo.jendenburg.orbitsim.domain.body;

import nl.sogyo.jendenburg.orbitsim.domain.body.state.InitialBodyState;

public class Planet extends Body
{
	private double radius;
	
	public Planet(InitialBodyState initState, double mass, double radius)
	{
		super(initState, mass);
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
