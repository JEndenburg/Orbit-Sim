package nl.sogyo.jendenburg.orbitsim.domain;

import java.time.Duration;
import java.util.*;

import nl.sogyo.jendenburg.orbitsim.domain.body.*;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class Simulation
{
	private List<Body> simulatedBodies = new ArrayList<>();
	
	public Simulation addBody(Body body)
	{
		simulatedBodies.add(body);
		return this;
	}
	
	public void simulate(Duration duration)
	{
		long seconds = duration.getSeconds();
		while(seconds --> 0)
		{
			for(Body b : simulatedBodies)
				affectByGravity(b);
			for(Body b : simulatedBodies)
				b.simulate();
		}
	}
	
	public List<Body> getBodies()
	{
		return Collections.unmodifiableList(simulatedBodies);
	}
	
	private void affectByGravity(Body affectedBody)
	{
		for(Body otherBody : simulatedBodies)
		{
			if(otherBody instanceof Planet && affectedBody != otherBody)
				affectByGravity(affectedBody, (Planet)otherBody);
		}
	}
	
	private void affectByGravity(Body affectee, Planet affector)
	{
		Vector3 directionAndDistance = affector.getPosition().subtract(affectee.getPosition());
		double affectorGravity = affector.getMass() / (directionAndDistance.getMagnitude());
		Vector3 gravityVector = directionAndDistance.normalize().multiply(affectorGravity);
		affectee.addVelocity(gravityVector.multiply(0.001));
	}
}
