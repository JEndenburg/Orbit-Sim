package nl.sogyo.jendenburg.orbitsim.domain.math;

public class Vector3
{
	private final double x, y, z;
	
	public Vector3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3()
	{
		this(0,0,0);
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getZ()
	{
		return z;
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3 normalize()
	{
		double magnitude = getMagnitude();
		return new Vector3(
				x / magnitude,
				y / magnitude,
				z / magnitude
				);
	}
}
