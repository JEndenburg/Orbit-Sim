package nl.sogyo.jendenburg.orbitsim.domain.body;

import java.time.*;

public class Vessel extends Body
{
	public Vessel(Planet parentBody, double mass)
	{
		super(parentBody, mass);
		this.parentBody = parentBody;
	}
}
