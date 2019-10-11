package nl.sogyo.jendenburg.orbitsim.domain.tests;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.*;

import nl.sogyo.jendenburg.orbitsim.domain.Simulation;
import nl.sogyo.jendenburg.orbitsim.domain.body.*;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimulationTest
{
	private static Simulation sim;
	private static Planet planet, moon;
	private static Vessel vessel;
	
	@BeforeEach
	public void setup()
	{
		sim = new Simulation();
		planet = new Planet(Vector3.zero, Vector3.zero, 400, 25);
		moon = new Planet(new Vector3(0, 0, 60), new Vector3(5, 0, 0), 20, 10);
		vessel = new Vessel(new Vector3(0, 0, 75), new Vector3(15, 0, 0), 0.0002);
		sim.addBody(planet).addBody(moon).addBody(vessel);
	}
	
	@Test
	public void testSimulationReturnsReadOnlyList()
	{
		List<Body> bodyList = sim.getBodies();
		Assertions.assertThrows(UnsupportedOperationException.class, () -> bodyList.add(planet));
	}
	
	@Test
	public void testSimulationReturnsListOfAllBodies()
	{
		List<Body> obtained = sim.getBodies();
		assertThat(obtained, containsInAnyOrder(vessel, moon, planet));
	}
	
	@Test
	public void testPlanetPositionAfter1SecondSimulation()
	{
		sim.simulate(Duration.ofSeconds(1));
		Vector3 expected = new Vector3(0, 0, 1.0/3.0);
		Vector3 obtained = planet.getPosition();
		Assertions.assertEquals(expected, obtained);
	}
}
