package nl.sogyo.jendenburg.orbitsim.domain.body;

import java.time.*;

import nl.sogyo.jendenburg.orbitsim.domain.body.state.InitialBodyState;

public class Vessel extends Body
{
	public Vessel(InitialBodyState initState, double mass)
	{
		super(initState, mass);
	}
}
