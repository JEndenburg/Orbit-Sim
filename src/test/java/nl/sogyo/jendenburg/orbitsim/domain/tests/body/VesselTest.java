package nl.sogyo.jendenburg.orbitsim.domain.tests.body;

import org.junit.jupiter.api.*;
import java.time.*;

import nl.sogyo.jendenburg.orbitsim.domain.*;
import nl.sogyo.jendenburg.orbitsim.domain.body.Planet;
import nl.sogyo.jendenburg.orbitsim.domain.body.Vessel;

public class VesselTest
{
	private static Planet planet;
	
	@BeforeAll
	public static void setup()
	{
		planet = new Planet(50.0, 10.0);
	}
}
