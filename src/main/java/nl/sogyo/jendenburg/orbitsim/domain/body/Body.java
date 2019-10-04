package nl.sogyo.jendenburg.orbitsim.domain.body;

public abstract class Body
{
	protected double mass;
	protected Planet parentBody;
	
	public Body(double mass)
	{
		this(null, mass);
	}
	
	public Body(Planet parentBody, double mass)
	{
		this.parentBody = parentBody;
		this.mass = mass;
	}
	
	public double getMass()
	{
		return mass;
	}
}
