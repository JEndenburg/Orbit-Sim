package nl.sogyo.jendenburg.orbitsim.domain.tests.body;

import java.time.Duration;

import org.junit.jupiter.api.*;

import nl.sogyo.jendenburg.orbitsim.domain.body.*;
import nl.sogyo.jendenburg.orbitsim.domain.body.state.*;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class BodyTest
{
	private static InitialBodyState initStateA = new InitialBodyState(new Vector3(-7, 2, 0), new Vector3(0,5,0));
	private static InitialBodyState initStateB = new InitialBodyState(Vector3.zero, new Vector3(0,-2.5,7.25));
	
	@Test
	public void testVesselVelocityMatchesConstructedValueA()
	{
		Vector3 expected = new Vector3(0,5,0);
		Vessel vessel = new Vessel(initStateA, 20.0);
		Assertions.assertEquals(expected, vessel.getStateAfterTime(Duration.ZERO).getVelocity());
	}
	
	@Test
	public void testVesselVelocityMatchesConstructedValueB()
	{
		Vector3 expected = new Vector3(0,-2.5,7.25);
		Vessel vessel = new Vessel(initStateB, 20.0);
		Assertions.assertEquals(expected, vessel.getStateAfterTime(Duration.ZERO).getVelocity());
	}
	
	@Test
	public void testVesselPositionMatchesConstructedValue()
	{
		Vector3 expected = new Vector3(-7,2,0);
		Vessel vessel = new Vessel(initStateA, 20.0);
		Assertions.assertEquals(expected, vessel.getStateAfterTime(Duration.ZERO).getPosition());
	}
	
	@Test
	public void testVesselAPositionAfterDuration2Hours()
	{
		Vector3 expected = new Vector3(-7,12,0);
		Vessel vessel = new Vessel(initStateA, 20.0);
		Assertions.assertEquals(expected, vessel.getStateAfterTime(Duration.ofHours(2)).getPosition());
	}
	
	@Test
	public void testVesselBPositionAfterDuration3Hours30minutes()
	{
		Vector3 expected = new Vector3(0,-8.75,25.375);
		Vessel vessel = new Vessel(initStateB, 20.0);
		Assertions.assertEquals(expected, vessel.getStateAfterTime(Duration.ofHours(3).plusMinutes(30)).getPosition());
	}
}
