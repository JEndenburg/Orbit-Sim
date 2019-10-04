package nl.sogyo.jendenburg.orbitsim.domain.body;

import java.time.Duration;

import nl.sogyo.jendenburg.orbitsim.domain.body.state.BaseBodyState;
import nl.sogyo.jendenburg.orbitsim.domain.body.state.BodyState;
import nl.sogyo.jendenburg.orbitsim.domain.body.state.InitialBodyState;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public abstract class Body
{
	protected double mass;
	protected BaseBodyState baseState;
	
	public Body(InitialBodyState initState, double mass)
	{
		this.baseState = initState;
		this.mass = mass;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public BodyState getStateAfterTime(Duration duration)
	{
		return new BodyState(
				baseState.getPosition().add(getDisplacement(baseState.getVelocity(), duration)),
				baseState.getVelocity()
				);
	}
	
	private Vector3 getDisplacement(Vector3 velocity, Duration duration)
	{
		return velocity.multiply(
				duration.toSeconds() / 3600.0
				);
	}
}
