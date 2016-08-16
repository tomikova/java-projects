package hr.fer.zemris.java.raytracer.model;

/**
 * Class represents sphere as graphical object that can exist in scene.
 * @author Tomislav
 *
 */

public class Sphere extends GraphicalObject {

	/**
	 * Sphere center.
	 */
	private Point3D center;
	/**
	 * Sphere radius.
	 */
	private double radius;
	/**
	 * Coefficient for diffuse component for red color.
	 */
	private double kdr;
	/**
	 * Coefficient for diffuse component for green color.
	 */
	private double kdg;
	/**
	 * Coefficient for diffuse component for blue color.
	 */
	private double kdb;
	/**
	 * Coefficient for reflective component for red color.
	 */
	private double krr;
	/**
	 *  Coefficient for reflective component for green color.
	 */
	private double krg;
	/**
	 *  Coefficient for reflective component for blue color.
	 */
	private double krb;
	/**
	 * Coefficient <code>n</code> for reflective component.
	 */
	private double krn;

	/**
	 * 
	 * @param center Sphere center.
	 * @param radius Sphere radius.
	 * @param kdr Coefficient for diffuse component for red color.
	 * @param kdg Coefficient for diffuse component for green color.
	 * @param kdb  Coefficient for diffuse component for blue color.
	 * @param krr Coefficient for reflective component for red color.
	 * @param krg Coefficient for reflective component for green color.
	 * @param krb Coefficient for reflective component for blue color.
	 * @param krn Coefficient <code>n</code> for reflective component.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Method finds closest ray intersection with sphere.
	 * @return RayIntersection object that holds intersection data of ray and some object.
	 * If intersection doesn't exist null is returned.
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {

		double numer = -1*(ray.start.sub(center).scalarProduct(ray.direction));
		
		double underRoot = Math.pow(ray.start.sub(center).scalarProduct(ray.direction), 2) 
				- (Math.pow(ray.start.sub(center).norm(), 2) - radius*radius);

		if (underRoot < 0) {
			return null;
		}
		else {
			double root = Math.sqrt(underRoot);
			double solutionFirst = numer+root;
			double solutionSecond = numer-root;
			
			if (solutionSecond < 0) {
				Point3D intersect = ray.start.add(ray.direction.scalarMultiply(solutionFirst));
				return new RaySphereIntersection(intersect, solutionFirst, false);
			}
			else {
				Point3D intersect = ray.start.add(ray.direction.scalarMultiply(solutionSecond));
				return new RaySphereIntersection(intersect, solutionSecond, true);
			}
		}
	}

	/**
	 * Class implements RayIntersection interface and holds data of intersection of ray with sphere.
	 * @author Tomislav
	 *
	 */
	public class RaySphereIntersection extends RayIntersection {

		/**
		 * 
		 * @param point Intersection point.
		 * @param distance Distance between origin and intersection point.
		 * @param outer True/false value indicating that ray enters or exits sphere.
		 */
		protected RaySphereIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point3D getNormal() {
			return getPoint().sub(center);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdr() {
			return kdr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdg() {
			return kdg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdb() {
			return kdb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrr() {
			return krr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrg() {
			return krg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrb() {
			return krb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrn() {
			return krn;
		}
	}
}