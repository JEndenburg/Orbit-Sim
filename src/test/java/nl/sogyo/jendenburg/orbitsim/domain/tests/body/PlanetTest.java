package nl.sogyo.jendenburg.orbitsim.domain.tests.body;

import org.junit.jupiter.api.*;

import nl.sogyo.jendenburg.orbitsim.domain.*;
import nl.sogyo.jendenburg.orbitsim.domain.body.Planet;
import nl.sogyo.jendenburg.orbitsim.domain.body.state.InitialBodyState;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class PlanetTest
{
	private static InitialBodyState initState = new InitialBodyState(Vector3.zero, Vector3.zero);
	
	@Test
	public void testPlanetHasSpecifiedMass25()
	{
		Planet planet = new Planet(initState, 25.0, 5.0);
		double planetMass = planet.getMass();
		Assertions.assertEquals(25.0, planetMass, 0.0001);
	}
	
	@Test
	public void testPlanetHasSpecifiedMass0_5()
	{
		Planet planet = new Planet(initState, 0.5, 5.0);
		double planetMass = planet.getMass();
		Assertions.assertEquals(0.5, planetMass, 0.0001);
	}
	
	@Test
	public void testPlanetHasSpecifiedRadius10()
	{
		Planet planet = new Planet(initState, 20.0, 10.0);
		double planetRadius = planet.getRadius();
		Assertions.assertEquals(10.0, planetRadius, 0.0001);
	}
	
	@Test
	public void testPlanetHasSpecifiedRadius30_5()
	{
		Planet planet = new Planet(initState, 10.0, 30.5);
		double planetRadius = planet.getRadius();
		Assertions.assertEquals(30.5, planetRadius, 0.0001);
	}
	
	@Test
	public void testPlanetGravityWithMass20Radius30()
	{
		Planet planet = new Planet(initState, 20, 30);
		Assertions.assertEquals(0.02222222222, planet.getGravity(), 0.000001);
	}
	
	@Test
	public void testPlanetGravityWithMass40Radius10()
	{
		Planet planet = new Planet(initState, 40, 10);
		Assertions.assertEquals(0.4, planet.getGravity(), 0.000001);
	}
}
