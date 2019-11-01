package nl.sogyo.jendenburg.orbitsim.domain.tests.body;

import org.junit.jupiter.api.*;
import java.time.*;

import nl.sogyo.jendenburg.orbitsim.domain.*;
import nl.sogyo.jendenburg.orbitsim.domain.body.Planet;
import nl.sogyo.jendenburg.orbitsim.domain.body.Vessel;
import nl.sogyo.jendenburg.orbitsim.domain.math.Vector3;

public class VesselTest
{
	private static Planet planet;
	
	@BeforeEach
	public static void setup()
	{
		planet = new Planet(Vector3.zero, Vector3.zero, 50.0, 10.0);
	}
}
