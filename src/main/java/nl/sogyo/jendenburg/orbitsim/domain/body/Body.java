package nl.sogyo.jendenburg.orbitsim.domain.body;

public abstract class Body
{
	protected double mass;
	
	public Body(double mass)
	{
		this.mass = mass;
	}
	
	public double getMass()
	{
		return mass;
	}
}
