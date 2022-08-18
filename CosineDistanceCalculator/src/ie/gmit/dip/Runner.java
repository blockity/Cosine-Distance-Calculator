package ie.gmit.dip;

/**
* The Cosine Distance Calculator program implements an application that
* takes two input text documents & calculates the similarity between them.
* The program returns to the user the angle between the documents, 1.0 being 
* identical and 0.0 being nothing alike.
*
* @author  Conor Brennan
* @version 1.0
* @since   1.8
*/
public class Runner {
	
	/**
	   * This is the main method which makes use of a thread to start the program.
	   * 
	   * @param args Unused.
	   * @exception NumberFormatException On input error.
	   * @exception Exception If you selection is invalid.
	   */
	public static void main(String[] args) {
		boolean done = false; //allows the menu to remain opened even after failure
		while (!done) {
			try {
				Thread t1 = new Thread(new Menu());    //create a new instance of the thread
				t1.start();							   //start the thread
				done = true;
			}catch (NumberFormatException e) {
				System.out.println("\n[ERROR] Invalid selection. Please select from [1-4]");
			} catch (Exception e) {
				System.out.println("\n[ERROR] Invalid selection.");
			}
		}
	}	
}
