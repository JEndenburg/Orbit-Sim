package nl.sogyo.jendenburg.orbitsim.domain.math;

public class Vector3
{
	private final double x, y, z;
	
	public static final Vector3 zero = new Vector3(0,0,0);
	public static final Vector3 up = new Vector3(0,1,0);
	public static final Vector3 right = new Vector3(1,0,0);
	public static final Vector3 forward = new Vector3(0,0,1);
	
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
	
	public Vector3 multiply(double amount)
	{
		return new Vector3(
				x * amount,
				y * amount,
				z * amount
				);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Vector3)
		{
			Vector3 otherV3 = (Vector3)other;
			return Math.abs(x - otherV3.x) < 0.01 && Math.abs(y - otherV3.y) < 0.01 && Math.abs(z - otherV3.z) < 0.01;
		}
		else
			return false;
	}
	
	@Override
	public String toString()
	{
		return String.format("( %.2f ; %.2f ; %.2f )", x, y, z);
	}
}
