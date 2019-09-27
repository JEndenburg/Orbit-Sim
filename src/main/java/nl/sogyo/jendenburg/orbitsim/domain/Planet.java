package nl.sogyo.jendenburg.orbitsim.domain;

public class Planet
{
	private double mass;
	private double radius;
	
	public Planet(double mass, double radius)
	{
		this.mass = mass;
		this.radius = radius;
	}
	
	public double getMass()
	{
		return mass;
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
