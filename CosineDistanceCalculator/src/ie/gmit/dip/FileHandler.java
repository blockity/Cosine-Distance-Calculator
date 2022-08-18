package ie.gmit.dip;

import java.io.*;
import java.util.*;

/**
 * Handles all files URL's sent from the user.
 */
public class FileHandler {
	

	
	Map<Integer, Integer> hmap = new HashMap<>(); // Maps string—hash—codes to frequencies
	String urlText;
	
	/**
     * Parse's text documents to a buffered reader, converts each word to hashcode,
     * and counts the frequency that each word appears.
     *
     * @param file Text be read into the buffered reader.
     * @return A hashmap of the input file and the frequency that each hashcode appears  .
     * @exception FileNotFoundException if your input file not found.
     * @exception IOException if your input format is invalid.
     */
	public Map<Integer, Integer> fileParser(String file) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line = null;

			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for (String word : words) {		//iterates through the word array.
					int hash = word.hashCode(); // save the hashcode of each string word to the int hash.
					int frequency = 1; // used to count the frequency each hashcode occurs.
					if (hmap.containsKey(hash)) { // Autoboxing converts the int to an Integer.
						frequency += hmap.get(hash); 
					}
					hmap.put(hash, frequency); //add all the hashcode and frequency of each to the map hmap. 
					
				}
			}
			System.out.println("File Entry Complete.");
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("\n[ERROR] No such file.");
		} catch (IOException e) {
			System.out.println("\n[ERROR] Please try again.");
		}
		return hmap;
	}

	/**
     * Parse's URL's to a buffered reader, removes all special characters,
     * converts each word to hashcode, and counts the frequency that each word appears.
     *
     * @param in URL input stream to be read in.
     * @return a hashmap of the input file and the frequency that each hashcode appears  
     * @exception FileNotFoundException if your input file not found.
     * @exception IOException if your input format is invalid.
     */
	public Map<Integer, Integer> urlParser(InputStream in) {
		try {BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
			while ((line = br.readLine()) != null) {
				String[] words = line.replaceAll("[^a-zA-Z0-9]", "").split(" "); //Removes all special characters.
				for (String word : words) {		//iterates through the word array.
					int hash = word.hashCode(); // save the hashcode of ecah string word to the int hash.
					int frequency = 1; // used to count the frequency each hashcode occurs.
					if (hmap.containsKey(hash)) { // Autoboxing converts the int to an Integer.
						frequency += hmap.get(hash); 
					}
					hmap.put(hash, frequency); //add all the hashcode and frequency of each to the map hmap. 
				}
			}
			System.out.println("URL Entry Complete.");
			br.close();
		} catch (IOException e) {
			System.out.println("\n[ERROR] Please try again.");
		}
		return hmap;
	}
}
