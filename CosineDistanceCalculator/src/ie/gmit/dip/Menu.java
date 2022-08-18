package ie.gmit.dip;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.net.URL;

/**
 * Menu displays a command line interface to the user allowing the user to input
 * text files and compare there similarity using the cosine similarity
 * calculator.
 * 
 * @see #Constructor.
 */
public class Menu implements Runnable {
	public boolean keepRunning = true;
	private Scanner s;
	private String query;
	private String subject;
	private FileHandler fh = new FileHandler();
	private Calculator cl = new Calculator();
	private Map<Integer, Integer> queryMap = new HashMap<>();
	private Map<Integer, Integer> subjectMap = new HashMap<>();

	
	/**
	 * Shows a command Menu allowing the user to make a selection
	 *
	 * @exception NumberFormatException  If string entered instead of int.
	 * @exception InputMismatchException If token retrieved does not match the
	 *                                   pattern for the expected type.
	 */
	@Override
	public void run() {
		s = new Scanner(System.in);
		while (keepRunning) { // to keep the main menu alive
			showMenu(); // opens the Main Menu
			int choice = Integer.parseInt(s.next()); // ask the user to choose an integer from 1-5
			try {
				if (choice == 1) {
					enterQueryFile();
					enterSubjectFile();
				} else if (choice == 2) {
					enterQueryUrl();
					enterSubjectUrl();
				} else if (choice == 3) {
					calculateCosineSimilarity();
				} else if (choice == 4) {
					System.out.println("[INFO] Shutting down...");
					keepRunning = false; // closes the Main Menu, stops the program
				} else {
					System.out.println("[ERROR] Invalid selection.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[ERROR] Incorrect Input! Please make a selection from [1-4]");
				showMenu(); // opens the Main Menu
			} catch (InputMismatchException e) {
				System.out.println("[ERROR] Input Mismatch! Please make a selection from [1-4]");
				showMenu(); // opens the Main Menu
			}
		}
	}

	/**
	 * Allows the user to enter a Query file which is sent to the File Handler to be
	 * converted to a HashMap.
	 * @exception NumberFormatException  If string entered instead of int.
	 * @exception InputMismatchException If token retrieved does not match the
	 *                                   pattern for the expected type.
	 */
	public void enterQueryFile() {
		try {
			s = new Scanner(System.in);
			System.out.print("Enter File Query File Name  ->");
			query = s.nextLine().trim();
			fh.hmap.clear();  // Ensures the hmap is empty. 0(n).
			queryMap.putAll(fh.fileParser(query)); // calls the method getMap and puts the result in the queryMap. 0(1).
		} catch (NumberFormatException e) { // catches both exceptions, resulting in an
			System.out.println("\n[ERROR] No such file found."); // error message displayed to the user.
			showMenu();											  // a chance for the user to select again.
		} catch (InputMismatchException e) {
			System.out.println("\n[ERROR] No such file found."); // error message displayed to the user.
			showMenu();											 // a chance for the user to select again.
		}
	}

	/**
	 * Allows the user to enter a Subject file which is sent to the File Handler to
	 * be converted to a HashMap.
	 * 
	 * @exception NumberFormatException  If string entered instead of int.
	 * @exception InputMismatchException If token retrieved does not match the
	 *                                   pattern for the expected type.
	 */
	public void enterSubjectFile() {
		try {
			s = new Scanner(System.in);
			System.out.print("\nEnter File Subject File Name ->");
			subject = s.nextLine().trim();
			fh.hmap.clear();     // Ensures the hmap is empty. 0(n).
			subjectMap.putAll(fh.fileParser(subject)); // calls the method getMap and puts the result in the queryMap
		} catch (NumberFormatException e) { // catches both exceptions, resulting in an
			System.out.println("\n[ERROR] No such file found."); // error message displayed and a chance for
			showMenu();											 // a chance for the user to select again
		}
	}

	/**
	 * Allows the user to enter a Query URL which is sent to the File Handler to
	 * be converted to a HashMap.

	 * @exception IOException if your input format is invalid.
	 */
	public void enterQueryUrl() {
		try {
			s = new Scanner(System.in);
			System.out.print("Enter URL ->");
			query = s.nextLine().trim();
			fh.hmap.clear();     // Ensure the hmap is empty.
			queryMap.putAll(fh.urlParser(new URL(query).openStream())); //sends the URL content in to the URL parser
		} catch (IOException e) { 								// and puts the result in the queryMap. 0(n).
			System.out.println("\n[ERROR] No such URL found."); // error message displayed to the user
			showMenu();											// a chance for the user to select again
		}
	}
	
	/**
	 * Allows the user to enter a Subject URL which is sent to the File Handler to
	 * be converted to a HashMap.
	 *
	 * @exception IOException if your input format is invalid
	 */
	public void enterSubjectUrl() {
		try {
			s = new Scanner(System.in);
			System.out.print("Enter URL ->");
			subject = s.nextLine().trim();
			fh.hmap.clear();    // Ensure the hmap is empty.
			subjectMap.putAll(fh.urlParser(new URL(subject).openStream())); //sends the URL content in to the URL parser 
																			// and puts the result in the queryMap. 0(n).
		} catch (IOException e) { // catches IOexception,
			System.out.println("\n[ERROR] No such URL found.");// resulting in a error message displayed to the user
															   // a chance for the user to select again
		}
	}

	/**
	 * Calculates the cosine similarity of two HashMaps,
	 * prints the result to the console along with the similarity as a percentage.
	 */
	public void calculateCosineSimilarity() {
		System.out.println("\nCosine Similarity: " + (cl.cosineSimilarity(queryMap, subjectMap)));// prints the cosine
																								// similarity to the
																								// console
	}

	/**
	 * User Interface
	 */
	private void showMenu() { // Main menu for Document Comparison System
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒░░░░░░░░▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.println("╔╦╗╔═╗╔═╗╦ ╦╔╦╗╔═╗╔╗╔╔╦╗  ╔═╗╔═╗╔╦╗╔═╗╔═╗╦═╗╦╔═╗╔═╗╔╗╔  ╔═╗╦ ╦╔═╗╔╦╗╔═╗╔╦╗");
		System.out.println(" ║║║ ║║  ║ ║║║║║╣ ║║║ ║   ║  ║ ║║║║╠═╝╠═╣╠╦╝║╚═╗║ ║║║║  ╚═╗╚╦╝╚═╗ ║ ║╣ ║║║");
		System.out.println("═╩╝╚═╝╚═╝╚═╝╩ ╩╚═╝╝╚╝ ╩   ╚═╝╚═╝╩ ╩╩  ╩ ╩╩╚═╩╚═╝╚═╝╝╚╝  ╚═╝ ╩ ╚═╝ ╩ ╚═╝╩ ╩");
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒░░░░░░░░▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒  (1) Enter Files for comparision   ▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒  (2) Enter URL's for comparision   ▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒  (3) Calculate Cosine Smililarity  ▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒  (4) Quit                          ▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.println("▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒░░░░░░░░▒▒▒▒▒▓▓▓▓▓▓▒▒▒▒▒░░░░░░▒▒▒▒▒▓▓▓▓▓▓");
		System.out.print("Select an option [1-4]> ");
	}
}
