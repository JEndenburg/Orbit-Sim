package nl.sogyo.jendenburg.orbitsim.domain.tests.math;

import org.junit.jupiter.api.*;
import nl.sogyo.jendenburg.orbitsim.domain.math.*;

public class Vector3Test
{
	private static Vector3 vectorA;
	private static Vector3 vectorB;
	private static Vector3 vectorC;
	
	@BeforeEach
	public void setup()
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
	
	@Test
	public void testCrossAB()
	{
		double[] expectedValues = {350.005, -1339.425, 10.25};
		Vector3 obtainedVector = Vector3.cross(vectorA, vectorB);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testCrossAC()
	{
		double[] expectedValues = {-387.5, 1638.1875, -70.5};
		Vector3 obtainedVector = Vector3.cross(vectorA, vectorC);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testAddAB()
	{
		double[] expectedValues = {2.375, 2.0, 180.2525};
		Vector3 obtainedVector = vectorA.add(vectorB);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testAddAC()
	{
		double[] expectedValues = {5.25, -8.0, -214.75};
		Vector3 obtainedVector = vectorA.add(vectorC);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testSubtractAB()
	{
		double[] expectedValues = {12.625, 2.0, -169.7525};
		Vector3 obtainedVector = vectorA.subtract(vectorB);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testSubtractAC()
	{
		double[] expectedValues = {9.75, 12.0, 225.25};
		Vector3 obtainedVector = vectorA.subtract(vectorC);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testVectorMultiplicationsATimes2_5()
	{
		double[] expectedValues = {18.75, 5.0, 13.125};
		Vector3 obtainedVector = vectorA.multiply(2.5);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testVectorMultiplicationsBTimes7_25()
	{
		double[] expectedValues = {-37.15625, 0.0, 1268.768125};
		Vector3 obtainedVector = vectorB.multiply(7.25);
		double[] obtainedValues = {obtainedVector.getX(), obtainedVector.getY(), obtainedVector.getZ()};
		Assertions.assertArrayEquals(expectedValues, obtainedValues, 0.0001);
	}
	
	@Test
	public void testVectorsEqual()
	{
		Vector3 v1 = new Vector3(16, 32.0, 11.52);
		Vector3 v2 = new Vector3(16, 32.0, 11.52);
		Assertions.assertEquals(v1, v2);
	}
	
	@Test
	public void testVectorsDoNotEqual()
	{
		Vector3 v1 = new Vector3(53, 12.63, -53.0);
		Vector3 v2 = new Vector3(-21, 12.63, -11.1);
		Assertions.assertNotEquals(v2, v1);
	}
}
