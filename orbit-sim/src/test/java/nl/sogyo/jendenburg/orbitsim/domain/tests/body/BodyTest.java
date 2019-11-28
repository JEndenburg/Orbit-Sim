package nl.sogyo.jendenburg.orbitsim.domain.tests.body;

import java.time.Duration;

import org.junit.jupiter.api.*;

import nl.sogyo.jendenburg.orbitsim.domain.body.*;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class BodyTest
{
	private static Body bodyA;
	private static Body bodyB;
	
	@BeforeEach
	public void setup()
	{
		bodyA = new Planet(new Vector3(2, -1.5, 4), new Vector3(-4, 1, 0), 200.0, 15.0);
		bodyB = new Vessel(new Vector3(-1, 0, 0), new Vector3(0, 0, 0), 0.5);
	}
	
	@Test
	public void testBodyHasSpecifiedMass25()
	{
		Planet planet = new Planet(Vector3.zero, Vector3.zero, 25.0, 5.0);
		double planetMass = planet.getMass();
		Assertions.assertEquals(25.0, planetMass, 0.0001);
	}
	
	@Test
	public void testBodyHasSpecifiedMass0_5()
	{
		Planet planet = new Planet(Vector3.zero, Vector3.zero, 0.5, 5.0);
		double planetMass = planet.getMass();
		Assertions.assertEquals(0.5, planetMass, 0.0001);
	}
	
	@Test
	public void testBodyHasSpecifiedPositionA()
	{
		Vector3 expected = new Vector3(2, -1.5, 4);
		Vector3 obtained = bodyA.getPosition();
		Assertions.assertTrue(expected.equals(obtained));
	}
	
	@Test
	public void testBodyHasSpecifiedPositionB()
	{
		Vector3 expected = new Vector3(-1, 0, 0);
		Vector3 obtained = bodyB.getPosition();
		Assertions.assertTrue(expected.equals(obtained));
	}
	
	@Test
	public void testBodyHasSpecifiedVelocityA()
	{
		Vector3 expected = new Vector3(-4, 1, 0);
		Vector3 obtained = bodyA.getVelocity();
		Assertions.assertTrue(expected.equals(obtained));
	}
	
	@Test
	public void testBodyHasSpecifiedVelocityB()
	{
		Vector3 expected = new Vector3(0, 0, 0);
		Vector3 obtained = bodyB.getVelocity();
		Assertions.assertTrue(expected.equals(obtained));
	}
	
	@Test
	public void testBodyHasSpecifiedVelocityAfterImpulse()
	{
		Vector3 expected = new Vector3(-6, 1, -1);
		bodyA.addVelocity(new Vector3(-2, 0, -1));
		Vector3 obtained = bodyA.getVelocity();
		Assertions.assertTrue(expected.equals(obtained));
	}
	
	@Test
	public void testBodyHasMovedAfterSimulationOccuredA()
	{
		Vector3 expected = new Vector3(2, -1.5, 4);
		bodyA.simulate();
		Vector3 obtained = bodyA.getPosition();
		Assertions.assertTrue(expected.equals(obtained));
	}
	
	@Test
	public void testBodyHasMovedAfterSimulationOccuredB()
	{
		Vector3 expected = new Vector3(-1, 0, 0);
		bodyB.simulate();
		Vector3 obtained = bodyB.getPosition();
		Assertions.assertTrue(expected.equals(obtained));
	}
}
