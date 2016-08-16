package hr.fer.zemris.java.simplecomp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import hr.fer.zemris.java.simplecomp.impl.*;
import hr.fer.zemris.java.simplecomp.models.*;
import hr.fer.zemris.java.simplecomp.parser.*;

/**
 * Program simulates work of a simple microprocessor.
 * Optional program argument is path to the file with microprocessor instructions.
 * @author Tomislav
 *
 */

public class Simulator {

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 * @throws Exception If error occurs while trying to read file.
	 * In case of any error occurred while parsing file.
	 */
	public static void main(String[] args) throws Exception {

		String path;

		if (args.length != 0) {
			path = args[0];
		}
		else {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(System.in)));
			while(true) {
				System.out.print("Upišite putanju do datoteke: ");
				path = reader.readLine().trim();
				if (!path.isEmpty()) {
					break;
				}
			}
		}

		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);

		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions");

		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		ProgramParser.parse(path, comp, creator);

		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();

		// Izvedi program
		exec.go(comp);
	}

}
