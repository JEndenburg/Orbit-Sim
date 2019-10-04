package nl.sogyo.jendenburg.orbitsim.domain.body.state;

import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public abstract class BaseBodyState
{
	private Vector3 position;
	private Vector3 velocity;
	
	protected BaseBodyState(BaseBodyState state)
	{
		this.position = state.position;
		this.velocity = state.velocity;
	}
	
	protected BaseBodyState(Vector3 position, Vector3 velocity)
	{
		this.position = position;
		this.velocity = velocity;
	}
	
	public Vector3 getPosition()
	{
		return position;
	}
	
	public Vector3 getVelocity()
	{
		return velocity;
	}
}
