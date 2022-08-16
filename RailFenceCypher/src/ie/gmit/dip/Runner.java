package ie.gmit.dip;

public class Runner {

	public static void main(String[] args) {
		Menu m = new Menu();
		boolean done = false; //allows the menu to remain opened even after failure
		while (!done) {
			try {
				m.start();
				done = true;
			}catch (NumberFormatException e) {
				System.out.println("\n[ERROR] Invalid selection. Please select from [1-6]");
			} catch (Exception e) {
				System.out.println("\n[ERROR] Invalid selection.");
			}
		}
	}
}
