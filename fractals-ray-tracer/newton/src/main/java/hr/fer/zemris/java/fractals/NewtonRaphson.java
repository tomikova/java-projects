package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

/**
 * Class for Newton-Raphson fractal calculation.
 * Sequential and parallel calculations are supported.
 * @author Tomislav
 *
 */

public class NewtonRaphson {

	/**
	 * Method starts sequential fractal calculation.
	 * @param complexPolynomial Polynomial in rooted form for calculations.
	 */
	public static void showSequential(ComplexRootedPolynomial complexPolynomial) {
		FractalViewer.show(getSequentialFractalproducer(complexPolynomial));
	}

	/**
	 * Method starts parallel fractal calculation.
	 * @param complexPolynomial Polynomial in rooted form for calculations.
	 */
	public static void showParallel(ComplexRootedPolynomial complexPolynomial) {
		FractalViewer.show(getParallelFractalproducer(complexPolynomial));
	}

	/**
	 * Method calculates Newton-Raphson fractal.
	 * @param reMin Minimal real axis value.
	 * @param reMax Maximal real axis value.
	 * @param imMin Minimal imaginary axis value.
	 * @param imMax Maximal imaginary axis value.
	 * @param width Screen width.
	 * @param height Screen height.
	 * @param m Maximum number of iterations.
	 * @param ymin Y line from where data array will be filled (included).
	 * @param ymax Y line to where data array will be filled (included).
	 * @param data Array for storing result.
	 * @param complexPolynomial Polynomial in rooted form for calculations.
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax,
			int width, int height, int m, int ymin, int ymax, short[] data, ComplexRootedPolynomial complexPolynomial) {

		int offset = ymin * width;
		ComplexPolynomial polynomial = complexPolynomial.toComplexPolynom();
		ComplexPolynomial derived = polynomial.derive();

		for(int y = ymin; y <= ymax; y++) {
			for(int x = 0; x < width; x++) {

				double cre = x/(width-1.0)*(reMax-reMin) + reMin;
				double cim = ((height-1)-y)/(height-1.0)*(imMax-imMin) + imMin;

				Complex zn = new Complex(cre, cim);

				int iters = 0;
				double module = 0;

				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					Complex zn1 = zn.sub(fraction);
					module = zn1.sub(zn).module();
					zn = zn1;
					iters++;
				} while(iters<m && module > 1e-3);

				int index = complexPolynomial.indexOfClosestRootFor(zn, 2e-3);

				if(index==-1) { 
					data[offset++] = 0; 
				}
				else {
					data[offset++] = (short) (index+1);
				}
			}
		}
	}

	/**
	 * Method retrieves object capable to generate data needed for fractal visualization.
	 * Retrieved object is sequential implementation.
	 * @param complexPolynomial Polynomial in rooted form for calculations.
	 * @return Object capable to generate data needed for fractal visualization.
	 */
	private static IFractalProducer getSequentialFractalproducer(ComplexRootedPolynomial complexPolynomial) {
		return new IFractalProducer() {

			@Override
			public void produce(double reMin, double reMax, double imMin, double imMax,
					int width, int height, long requestNo,
					IFractalResultObserver observer) {
				long t0 = System.currentTimeMillis();
				System.out.println("Započinjem izračune...");
				int m = 16*16*16;
				short[] data = new short[width*height];
				calculate(reMin, reMax, imMin, imMax, width, height, m, 0, height-1, data, complexPolynomial);
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(data, (short)(complexPolynomial.toComplexPolynom().order()+1), requestNo);
				System.out.println("Dojava gotova...");
				long t1 = System.currentTimeMillis();
				System.out.println("Vrijeme operacije: " + (t1-t0) + " ms");
			}

		};
	}

	/**
	 * Method retrieves object capable to generate data needed for fractal visualization.
	 * Retrieved object is parallel implementation.
	 * @param complexPolynomial Polynomial in rooted form for calculations.
	 * @return Object capable to generate data needed for fractal visualization.
	 */
	private static IFractalProducer getParallelFractalproducer(ComplexRootedPolynomial complexPolynomial) {
		return new IFractalProducer() {

			private int numProc = Runtime.getRuntime().availableProcessors();

			private final ExecutorService pool = Executors.newFixedThreadPool(numProc,
					new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(true);
					return thread;
				}
			});

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void produce(double reMin, double reMax, double imMin, double imMax,
					int width, int height, long requestNo,
					IFractalResultObserver observer) {	

				long t0 = System.currentTimeMillis();
				System.out.println("Započinjem izračune...");
				int m = 16*16*16;

				/**
				 * Class represents job which is one part of what needs to be performed. 
				 * @author Tomislav
				 *
				 */
				class Job implements Callable<Void> {

					/**
					 * Start index on y axis.
					 */
					private int startIndex;
					/**
					 * End index on y axis.
					 */
					private int endIndex;
					/**
					 * Data array.
					 */
					private short[] data;

					/**
					 * Constructor with three parameters
					 * @param startIndex Start index on y axis.
					 * @param endIndex End index on y axis.
					 * @param data Data array.
					 */
					public Job(int startIndex, int endIndex, short[]data) {
						super();
						this.startIndex = startIndex;
						this.endIndex = endIndex;
						this.data = data;
					}
					
					/**
					 * {@inheritDoc}
					 */
					@Override
					public Void call() {
						calculate(reMin, reMax, imMin, imMax, width, height, m, startIndex, endIndex, data, complexPolynomial);
						return null;
					}			
				}

				short[] data = new short[width*height];
				List<Future<Void>> results = new ArrayList<>();
				int numOfJobs = 8*numProc;
				int part = height/numOfJobs;
				int count = 0;
				for (int i = 0; i < numOfJobs-1; i++) {
					results.add(pool.submit(new Job(count,count+part-1,data)));
					count += part;
				}
				results.add(pool.submit(new Job(count,height-1,data)));

				for(Future<Void> f : results) {
					while(true) {
						try {
							f.get();
							break;
						} catch (InterruptedException | ExecutionException e) {
						}
					}
				}
				observer.acceptResult(data, (short)(complexPolynomial.toComplexPolynom().order()+1), requestNo);
				System.out.println("Izračuni gotovi...");
				System.out.println("Dojava gotova...");
				long t1 = System.currentTimeMillis();
				System.out.println("Vrijeme operacije: " + (t1-t0) + " ms");
			}		
		};
	}
}
