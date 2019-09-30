package nl.sogyo.jendenburg.orbitsim.domain.tests.math;

import org.junit.jupiter.api.*;
import nl.sogyo.jendenburg.orbitsim.domain.math.*;

public class Vector3Test
{
	private static Vector3 vectorA;
	private static Vector3 vectorB;
	private static Vector3 vectorC;
	
	@BeforeAll
	public static void setup()
	{
		vectorA = new Vector3(7.5, 2.0, 5.25);
		vectorB = new Vector3(-5.125, 0.0, 175.0025);
		vectorC = new Vector3(-2.25, -10.0, -220.0);
	}
	
	@Test
	public void testIfVectorAHasCorrectValues()
	{
		double[] expectedValues = { 7.5, 2.0, 5.25 };
		double[] obtainedValues = { vectorA.getX(), vectorA.getY(), vectorA.getZ() };
		Assertions.assertArrayEquals(expectedValues, obtainedValues);
	}
	
	@Test
	public void testIfVectorBHasCorrectValues()
	{
		double[] expectedValues = { -5.125, 0.0, 175.0025 };
		double[] obtainedValues = { vectorB.getX(), vectorB.getY(), vectorB.getZ() };
		Assertions.assertArrayEquals(expectedValues, obtainedValues);
	}
	
	@Test
	public void testIfGetCorrectMagnitudeFromA()
	{
		double expectedValue = 9.37083;
		Assertions.assertEquals(expectedValue, vectorA.getMagnitude(), 0.0001);
	}
	
	@Test
	public void testIfGetCorrectMagnitudeFromB()
	{
		double expectedValue = 175.07752;
		Assertions.assertEquals(expectedValue, vectorB.getMagnitude(), 0.0001);
	}
	
	@Test
	public void testNormalizeA()
	{
		double[] expectedVectorValues = {0.80035, 0.21342, 0.56024};
		Vector3 normalizedVector = vectorA.normalize();
		double[] obtainedVectorValues = { normalizedVector.getX(), normalizedVector.getY(), normalizedVector.getZ() };
		Assertions.assertArrayEquals(expectedVectorValues, obtainedVectorValues, 0.0001);
	}
	
	@Test
	public void testNormalizeB()
	{
		double[] expectedVectorValues = {-0.02927, 0.0, 0.99957};
		Vector3 normalizedVector = vectorB.normalize();
		double[] obtainedVectorValues = { normalizedVector.getX(), normalizedVector.getY(), normalizedVector.getZ() };
		Assertions.assertArrayEquals(expectedVectorValues, obtainedVectorValues, 0.0001);
	}
	
	@Test
	public void testDotAB()
	{
		double expectedValue = 880.325625;
		Assertions.assertEquals(expectedValue, Vector3.dot(vectorA, vectorB), 0.0001);
	}
	
	@Test
	public void testDotAC()
	{
		double expectedValue = -1191.875;
		Assertions.assertEquals(expectedValue, Vector3.dot(vectorA, vectorC), 0.0001);
	}
}
