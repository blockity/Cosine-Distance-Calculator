package ie.gmit.dip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	private int key;
	private int col;
	private int offset;
	private String fileInput;
	private String urlInput;
	private RailFenceCypher rfc = new RailFenceCypher(key, col, offset);
	private Scanner s;
	public boolean keepRunning = true;
	private FileHandler fh = new FileHandler(new RailFenceCypher(key, col, offset));
	private Help h = new Help();

	public Menu() {
		s = new Scanner(System.in);
	}

	public void start() {

		while (keepRunning) { // to keep the main menu alive
			showMenu(); // opens the Main Menu
			int choice = Integer.parseInt(s.next()); // ask the user to choose an integer from 1-7
			try {
				if (choice == 1) {
					fileSelection();
				} else if (choice == 2) {
					enterKeyOffset();
				} else if (choice == 3) {
					encrypt();
				} else if (choice == 4) {
					decrypt();
				} else if (choice == 5) {
					displayRailFence();
				} else if (choice == 6) {
					help();
				} else if (choice == 7) {
					System.out.println("[INFO] Shutting down...");
					keepRunning = false; // closes the Main Menu, stops the program
				} else {
					System.out.println("[ERROR] Invalid selection.");
				}
			} catch (InputMismatchException e) {
				System.out.println("[ERROR] Input Mismatch! Please make a selection from [1-7]");
			} catch (NumberFormatException e) {
				System.out.println("[ERROR] Incorrect Input! Please make a selection from [1-7]");
			}
		}
	}

	public void fileSelection() {

		showFileSelectionMenu(); // Ask user to select a File or URL
		int choice = Integer.parseInt(s.next());

		if (choice == 1) { // if user want to select a file
			try {
				s = new Scanner(System.in);
				System.out.print("Enter File (followed by .txt) ->");
				fileInput = s.nextLine().trim();
				System.out.println("");
				fh.parse(new FileInputStream(new File(fileInput))); // invoking the parse method from the
																	// FileHandler
																	// Class to parse the selected file
			} catch (FileNotFoundException | NumberFormatException e) { // catches both exceptions, resulting in an
				System.out.println("\n[ERROR] No such file found."); // error message displayed and a chance for
				fileSelection(); // the user to select again
			}

		} else if (choice == 2) { // if user wants to select a URL
			try {
				s = new Scanner(System.in);
				System.out.print("Enter URL ->");
				urlInput = s.nextLine();
				fh.parse(new URL(urlInput).openStream()); // invoking the parse method from the FileHandler Class to
															// parse the selected URL.
			} catch (IOException e) { // catches IOexception,
				System.out.println("\n[ERROR] No such URL found.");// resulting in a error message displayed to the user
																	// // a chance for the user to select again
				fileSelection(); // keeps the file selection menu alive
			}
		} else {
			System.out.println("\n[ERROR] Sure you can't be doing that!");
		} // don't be at it
	}

	public void enterKeyOffset() { // ask user to select key & offset
		System.out.print("Enter key -> ");
		key = s.nextInt(); // input saved to the integer key
		System.out.println("Key set as: " + key);

		System.out.print("Enter offset -> ");
		offset = s.nextInt(); // input saved to the integer
		System.out.println("Offset set as: " + offset);

	}

	public void encrypt() { // encrypts the file or URL entered by the user, using the key also entered by
							// the user

		if (key == 0) { // ensures that a key must be entered to proceed

			System.out.println("\n[ERROR] Please enter a key before trying to encrypt.");
		} else if (fh.plainText == null) { // ensures that a file or url must be entered to proceed
			System.out.println("\n[ERROR] Please enter a file or url before trying to encrypt.");
		} else
			rfc.encrypt(fh.plainText, key, offset); // calls the encrypt method from the RailFenceCypher class
	}

	public void decrypt() {
		if (key == 0) {
			System.out.println("\n[ERROR] Please enter a key before trying to decrypt."); // ensures that a key must be
																						  // entered to
			// proceed
		} else if (fh.plainText == null) {
			System.out.println("\n[ERROR] Please enter a file or url before trying to decrypt."); // ensures that a file
																								  	// or url
			// must be entered to proceed
		} else
			rfc.decrypt(rfc.cipherText, key, offset); // calls the decrypt method from the RailFenceCypher class

	}

	public void displayRailFence() {
		rfc.displayRailFence(rfc.matrix); // calls the displayRailFence method from the class RailFenceCypher
	}

	public void help() {
		boolean open = true; // keeps the help menu alive
		while (open) {
			showHelpMenu(); // opens the help menu
			int choice = Integer.parseInt(s.next());
			try {
				if (choice == 1) {
					h.railFenceCypher(); // calls the railFenceCypher method from the help class
				} else if (choice == 2) {
					h.key(); 	// calls the key method from the help class
				} else if (choice == 3) {
					h.offset(); // calls the offset method from the help class
				} else if (choice == 4) {
					h.encrypt(); // calls the encrypt method from the help class
				} else if (choice == 5) {
					h.decrypt(); // calls the decrypt method from the help class
				} else if (choice == 6) {
					h.somebody(); // calls the somebody method from the help class
				} else if (choice == 7) {
					open = false; // closes the help menu and return to the main menu
				} else {
					System.out.println("[ERROR] Invalid selection.");
				}
			} catch (InputMismatchException e) {
				System.out.println("[ERROR] Input Mismatch! Please make a selection from [1-7]");
			} catch (NumberFormatException e) {
				System.out.println("[ERROR] Incorrect Input! Please make a selection from [1-7]");
			}
		}
	}

	public void quit() {

	}

	private void showMenu() { // Main menu for Rail Fence Cypher
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓ ╦═╗┌─┐┬┬    ╔═╗┌─┐┌┐┌┌─┐┌─┐  ╔═╗┬ ┬┌─┐┬ ┬┌─┐┬─┐ ▓▓▓");
		System.out.println("▓▓▓ ╠╦╝├─┤││    ╠╣ ├┤ ││││  ├┤   ║  └┬┘├─┘├─┤├┤ ├┬┘ ▓▓▓");
		System.out.println("▓▓▓ ╩╚═┴ ┴┴┴─┘  ╚  └─┘┘└┘└─┘└─┘  ╚═╝ ┴ ┴  ┴ ┴└─┘┴└─ ▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(1) Select a File or URL           ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(2) Enter Rail Fence Key & Offset  ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(3) Encrypt                        ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(4) Decrypt                        ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(5) Display Rail Fence             ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(6) Help                           ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(7) Quit                           ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.print("Select an option [1-6]> ");
	}

	private void showFileSelectionMenu() { // Secondary menu for file selection
		System.out.println("");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒  FILE SELECTION  ▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(1) Enter File                     ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(2) Enter URL                      ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.print("Select an option [1-2]> ");
	}

	private void showHelpMenu() { // Menu for Help
		System.out.println("");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒       HELP       ▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(1) What is a Rail Fence Cypher?   ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(2) What is the key?               ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(3) What is the offset?            ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(4) How the encryption works       ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(5) How the decryption works       ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(6) I need somebody....            ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░(7) Main Menu                      ░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.println("▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓▓▓▒▒▒▒░░░░░▒▒▒▒▓▓▓");
		System.out.print("Select an option [1-6]> ");
	}
}
