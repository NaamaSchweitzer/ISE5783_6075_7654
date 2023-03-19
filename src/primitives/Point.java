package primitives;

import java.util.Objects;

public class Point {
	
	Double3 xyz;
	
	/**
	 * 
	 */
	public Point(double d1, double d2, double d3) {
	     xyz = new Double3(d1,d2,d3);
	}
	
	Point(Double3 x) {
	     xyz = x;
	}

	@Override
	public int hashCode() {
		return Objects.hash(xyz);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return Objects.equals(xyz, other.xyz);
	}

	@Override
	public String toString() {
		return "Point [xyz=" + xyz + "]";
	}	

	public Point add(Vector vec)	{
		return new Point(xyz.add(vec.xyz));
	}
	
	public Vector subtract(Point p)	{
		return new Vector(xyz.subtract(p.xyz));
	}
	
	public Double distance(Point p)	{
        return Math.sqrt(distanceSquared(p));
	}
	
	public Double distanceSquared(Point p) {
		return (xyz.d1 - p.xyz.d1)*(xyz.d1 - p.xyz.d1) 
				+ (xyz.d2 - p.xyz.d2)*(xyz.d2 - p.xyz.d2) 
				+ (xyz.d3 - p.xyz.d3)*(xyz.d3 - p.xyz.d3);
	}
}
