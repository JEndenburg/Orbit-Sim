package nl.sogyo.jendenburg.orbitsim.domain.tests;

import org.junit.jupiter.api.*;
import java.time.*;

import nl.sogyo.jendenburg.orbitsim.domain.*;

public class VesselTest
{
	private static Planet planet;
	
	@BeforeAll
	public static void setup()
	{
		planet = new Planet(50.0, 10.0);
	}
	
	@Test
	public void testVesselVelocityWithMass0_005AtHeight60FromPlanet()
	{
		Vessel vessel = new Vessel(planet, 0.005, 60);
		double velocity = vessel.getVelocityAt(LocalDateTime.now());
		Assertions.assertEquals(0.9128709, velocity, 0.000001);
	}
	
	@Test
	public void testVesselVelocityWithMass0_005AtHeight100FromPlanet()
	{
		Vessel vessel = new Vessel(planet, 0.005, 100);
		double velocity = vessel.getVelocityAt(LocalDateTime.now());
		Assertions.assertEquals(0.7071067, velocity, 0.000001);
	}
}
