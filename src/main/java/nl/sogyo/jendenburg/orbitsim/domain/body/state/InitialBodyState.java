package nl.sogyo.jendenburg.orbitsim.domain.body.state;

import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public final class InitialBodyState extends BaseBodyState
{
	/**
	 * 
	 * @param position
	 * @param velocity The velocity in units/hour
	 */
	public InitialBodyState(Vector3 position, Vector3 velocity)
	{
		super(position, velocity);
	}
}
