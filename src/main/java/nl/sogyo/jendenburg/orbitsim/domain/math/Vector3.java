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
	
	public static double dot(Vector3 a, Vector3 b)
	{
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public static Vector3 cross(Vector3 a, Vector3 b)
	{
		return new Vector3(
				a.y * b.z - a.z * b.y,
				a.z * b.x - a.x * b.z,
				a.x * b.y - a.y * b.x
				);
	}
	
	public Vector3 add(Vector3 v3)
	{
		return new Vector3(
				x + v3.x,
				y + v3.y,
				z + v3.z
				);
	}
	
	public Vector3 subtract(Vector3 v3)
	{
		return new Vector3(
				x - v3.x,
				y - v3.y,
				z - v3.z
				);
	}
}
