package ie.gmit.dip;

public class RailFenceCypher {

	public char[][] matrix = null;
	private int j;
	private int row;
	private int col;
	public String cipherText = "";
	public String plainText = "";

	public RailFenceCypher(int key, int col, int offset) {
		matrix = new char[key][col];

	}

	public String encrypt(String s, int key, int offset) {

		boolean direction = false; // to check if direction is up or down
		j = offset;
		row = key;
		col = s.length();

		matrix = new char[row][col]; // Initializing the array

		// Looping over the matrix to input file/URL
		if (j > 0) direction = true; 	// ensures that the direction of input is always down, regardless of the offset
		for (int i = 0; i < col; i++) {

			if (j == 0 || j == row - 1)
				direction = !direction;

			matrix[j][i] = s.charAt(i);
			if (direction) {
				j++;
			} else
				j--;
		}
		// Looping over the matrix to retrieve the encrypted data
		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++) {
				if (matrix[i][k] != 0)
					cipherText = cipherText + matrix[i][k];
			}
		}
		System.out.println("Cypher Text:");
		System.out.println(cipherText);		// prints the cypher text to the console
		System.out.println("Encryption sucessful!");

		return (cipherText); // printing the ciphertext

	}

	public String decrypt(String s, int key, int offset) {
		boolean direction = false; // to check if direction is up or down
		j = offset;
		row = key;
		col = s.length();
		matrix = new char[row][col]; // Initializing the array

		// inserting '*' into the matrix where encoded data is located
		if (j > 0) direction = true;	// ensures that the direction of input is always down, regardless of the offset
		for (int i = 0; i < col; i++) {
			if (j == 0 || j == row - 1)
				direction = !direction;

			matrix[j][i] = '*';
			if (direction)
				j++;
			else
				j--;

		}
		// now replacing the '*' with the cipher text
		int index = 0;

		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++) {

				if (matrix[i][k] == '*' && index < col) {
					matrix[i][k] = s.charAt(index++);

				}
			}
		}
		// Looping over the matrix to retrieve the decrypted data
		direction = false;
		j = offset;
		if (j > 0) direction = true;	// ensures that the direction of input is always down, regardless of the offset
		for (int i = 0; i < col; i++) {
			if (j == 0 || j == row - 1)
				direction = !direction;

			plainText += matrix[j][i];

			if (direction)
				j++;
			else
				j--;

		}
		System.out.println("Plain Text:");
		System.out.println(plainText);	//printing the plain text to the console
		System.out.println("Decryption sucessful!");

		return (plainText);
	}

	public void displayRailFence(char[][] matrix) {
		if (matrix.length == 0) {	//if the array is empty, tell the user. 
			System.out.println("Such empty!");
		}	// looping over the matrix to display the cypher text in the array
		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++) {
				System.out.print(matrix[i][k] + "\t");
			}
			System.out.println();
		}

	}

}