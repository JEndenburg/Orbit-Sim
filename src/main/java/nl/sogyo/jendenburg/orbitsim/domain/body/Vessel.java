package nl.sogyo.jendenburg.orbitsim.domain.body;

import java.time.*;

import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class Vessel extends Body
{
	public Vessel(Vector3 position, Vector3 velocity, double mass)
	{
		super(position, velocity, mass);
	}
}
