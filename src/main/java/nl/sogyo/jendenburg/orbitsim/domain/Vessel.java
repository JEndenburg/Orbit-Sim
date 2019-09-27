package nl.sogyo.jendenburg.orbitsim.domain;

import java.time.*;

public class Vessel extends Body
{
	private Planet parentBody;
	private double distanceToParent;
	
	public Vessel(Planet parentBody, double mass, double distanceToParent)
	{
		super(mass);
		this.parentBody = parentBody;
		this.distanceToParent = distanceToParent;
	}
	
	public double getVelocityAt(LocalDateTime dateTime)
	{
		return Math.sqrt(parentBody.getMass() / distanceToParent);
	}
}
