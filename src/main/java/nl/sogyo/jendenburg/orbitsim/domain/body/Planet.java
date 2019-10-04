package nl.sogyo.jendenburg.orbitsim.domain.body;

public class Planet extends Body
{
	private double radius;
	
	public Planet(double mass, double radius)
	{
		this(null, mass, radius);
	}
	
	public Planet(Planet parentBody, double mass, double radius)
	{
		super(parentBody, mass);
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
