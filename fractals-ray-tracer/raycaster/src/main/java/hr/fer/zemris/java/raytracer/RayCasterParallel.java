package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.*;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program is a simplification of a ray-tracer for rendering of 3D scenes, parallel implementation.
 * @author Tomislav
 *
 */
public class RayCasterParallel {
	
	/**
	 * Eye position point.
	 */
	static Point3D eye = new Point3D(10,0,0);

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	/**
	 * Method returns implementation of interface that specifies objects which are 
	 * capable to create scene snapshots by using ray-tracing technique.
	 * @return Object which implements IRayTracerProducer interface.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer) {
				
				long t0 = System.currentTimeMillis();
				System.out.println("Započinjem izračune...");

				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D zAxis = view.sub(eye);
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(
						yAxis.scalarMultiply(vertical / 2));
				
				ForkJoinPool pool = new ForkJoinPool();
				
				pool.invoke(new Job(eye, yAxis, xAxis, screenCorner, horizontal,
						vertical, width, height, red, green, blue, 0, height));
				
				pool.shutdown();
				 
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
				long t1 = System.currentTimeMillis();
				System.out.println("Vrijeme operacije: " + (t1-t0) + " ms");
			}
		};
	}
	
	/**
	 * Class represents job which is one part of what needs to be performed.
	 * Job will be performed directly if it satisfies condition otherwise new jobs
	 * are created with less data.
	 * @author Tomislav
	 *
	 */
	public static class Job extends RecursiveAction {
		
		private static final long serialVersionUID = 1L;
		
		/**
		 * Eye position.
		 */
		private Point3D eye;
		/**
		 * Y axis.
		 */
		private Point3D yAxis;
		/**
		 * X axis.
		 */
		private Point3D xAxis;
		/**
		 * Screen corner.
		 */
		private Point3D screenCorner;
		/**
		 * Horizontal width of observed space.
		 */
		private double horizontal;
		/**
		 * Vertical height of observed space
		 */
		private double vertical;
		/**
		 * Number of pixels per screen row.
		 */
		private int width;
		/**
		 * Number of pixel per screen column.
		 */
		private int height;
		/**
		 * Red color component.
		 */
		private short[] red;
		/**
		 * Green color component.
		 */
		private short[] green;
		/**
		 * Blue color component.
		 */
		private short[] blue;
		/**
		 * Calculation start index.
		 */
		private int startIndex;
		/**
		 * Calculation end index.
		 */
		private int endIndex;
		
		/**
		 * Default constructor.
		 * @param eye Eye position.
		 * @param yAxis Y axis.
		 * @param xAxis X axis.
		 * @param screenCorner Screen corner.
		 * @param horizontal Horizontal width of observed space.
		 * @param vertical Vertical height of observed space
		 * @param width Number of pixels per screen row.
		 * @param height Number of pixel per screen column.
		 * @param red Red color component.
		 * @param green Green color component.
		 * @param blue Blue color component.
		 * @param startIndex Calculation start index.
		 * @param endIndex Calculation end index.
		 */
		public Job(Point3D eye, Point3D yAxis, Point3D xAxis, Point3D screenCorner,
				double horizontal, double vertical, int width, int height,
				short[] red, short[] green, short[] blue, int startIndex, int endIndex) {
			this.eye = eye;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void compute() {
			if (endIndex - startIndex < 20) {
				computeDirect();
				return;
			}
			Job job1 = new Job(eye, yAxis, xAxis, screenCorner, horizontal, vertical,
					width, height, red, green, blue, startIndex, startIndex + (endIndex - startIndex)/ 2);
			Job job2 = new Job(eye, yAxis, xAxis, screenCorner, horizontal, vertical,
					width, height, red, green, blue, startIndex + (endIndex - startIndex)/ 2, endIndex);
			invokeAll(job1, job2);		
		}

		/**
		 * Method calculates rgb color components for pixels.
		 */
		private void computeDirect() {
			
			Scene scene = RayTracerViewer.createPredefinedScene();
			short[] rgb = new short[3];
			int offset = startIndex * width;
			
			for (int y = startIndex; y < endIndex; y++) {
				for (int x = 0; x < width; x++) {
					Point3D screenPoint = screenCorner.add(
							xAxis.scalarMultiply(x * horizontal / (width - 1)))
							.sub(yAxis.scalarMultiply(y * vertical
									/ (height - 1)));
					Ray ray = Ray.fromPoints(eye, screenPoint);
					tracer(scene, ray, rgb);
					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					offset++;
				}
			}
		}	
	}
	
	/**
	 * Method determines closest intersection of ray and any object in the scene (in front of observer).
	 * @param scene Scene object.
	 * @param ray Ray from eye position to pixel.
	 * @param rgb Red, green, blue color values.
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection first = null;
		for (GraphicalObject obj : scene.getObjects()) {
			
			RayIntersection intersection = obj.findClosestRayIntersection(ray);
			
			if (intersection == null) {
				continue;
			}

			if (first == null || intersection.getDistance() < first.getDistance()) {
				first = intersection;
			}
		}

		if (first != null) {
			determineColorFor(scene, first, rgb);
		}
	}
	
	/**
	 * Method for determining color based on Phong model.
	 * @param scene Scene object.
	 * @param S Intersection of ray with sphere.
	 * @param rgb Red, green, blue color values.
	 */
	private static void determineColorFor(Scene scene, RayIntersection S, short[] rgb) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		for (LightSource ls : scene.getLights()) {
			
			Ray rLight = Ray.fromPoints(ls.getPoint(), S.getPoint());

			RayIntersection first = null;
			for (GraphicalObject obj : scene.getObjects()) {
				
				RayIntersection intersection = obj.findClosestRayIntersection(rLight);
				if (intersection == null) {
					continue;
				}

				if (first == null || intersection.getDistance() < first.getDistance()) {
					first = intersection;
				}
			}

			if (first != null
					&& first.getDistance() < (ls.getPoint().sub(S.getPoint())).norm()
					&& Math.abs(first.getDistance()
							- (ls.getPoint().sub(S.getPoint())).norm()) > 1e-10) {
				continue;
			} 
			else {
				
				Point3D n = S.getNormal();
				Point3D l = ls.getPoint().sub(S.getPoint()).normalize();
				Point3D r = n.scalarMultiply(2).scalarMultiply(n.scalarProduct(l) / n.norm()).sub(l).normalize();
				Point3D v = eye.sub(S.getPoint()).normalize();

				double rv = r.scalarProduct(v);
				double ln = l.scalarProduct(n);
				
				
				rgb[0] += ls.getR() * S.getKdr() * ln;
				rgb[1] += ls.getG() * S.getKdg() * ln;
				rgb[2] += ls.getB() * S.getKdb() * ln;
				
				
				if (rv > 0) {
					rgb[0] += ls.getR() * S.getKrr() * Math.pow(rv, S.getKrn());
					rgb[1] += ls.getG() * S.getKrg() * Math.pow(rv, S.getKrn());
					rgb[2] += ls.getB() * S.getKrb() * Math.pow(rv, S.getKrn());
				}
			}
		}
	}
}
