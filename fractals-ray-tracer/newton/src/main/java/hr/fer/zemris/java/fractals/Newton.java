package hr.fer.zemris.java.fractals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Program for Newton-Raphson fractal calculation.
 * @author Tomislav
 *
 */

public class Newton {

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 * @throws IOException In case of error while reading user input.
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer."
				+ "\nPlease enter at least two roots, one root per line. Enter 'done' when done.");
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
		
		List<Complex> roots = new ArrayList<>();
		
		String input;
		int count = 1;
		while(true) {
			System.out.print("Root "+count+">");
			input = reader.readLine().trim();
			if (input.equals("done")) {
				break;
			}
			roots.add(Complex.parse(input));
			count++;
		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
	
		Complex[] rootsArray = roots.toArray(new Complex[roots.size()]);
		
		//Uncomment next line for sequential calculation
		
		//NewtonRaphson.showSequential(new ComplexRootedPolynomial(rootsArray));
		NewtonRaphson.showParallel(new ComplexRootedPolynomial(rootsArray));
	}	
}
