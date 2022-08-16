package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileHandler {

	@SuppressWarnings("unused") // used this because it of the error, cypher wasn't being used.
	private RailFenceCypher cypher;
	public String plainText;

	public FileHandler(RailFenceCypher c) {
		cypher = c;

	}

	public void parse(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		plainText = br.lines().collect(Collectors.joining("\n")); // adds each line of the file or url to the string
																  // plainText
		System.out.println("You entered :");
		System.out.println(plainText);
		System.out.println("File Selection Complete");
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
