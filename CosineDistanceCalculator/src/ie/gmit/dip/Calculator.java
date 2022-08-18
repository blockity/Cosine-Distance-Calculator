package ie.gmit.dip;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
		
/**
 * All calculations are done in this class.
 *
 */
public class Calculator {

	/**
     * Calculates the cosine similarity for two given Maps.
     *
     * @param map1 subject file
     * @param map2 query file
     * @return similarity between the two documents
     */
	public double cosineSimilarity(Map<Integer, Integer> map1, Map<Integer, Integer> map2) {
		
		double similarity;
		//Get unique words from both sequences
		Set<Integer> intersection = new HashSet<>();
		intersection.addAll(map1.keySet()); 	// 0(1)
		intersection.retainAll(map2.keySet()); // 0(n)
		
		double dotProduct = 0;
		double magnitudeA = 0;
		double magnitudeB = 0;

		// Calculate dot product
		for (Integer item : intersection) {		// 0(n)
			dotProduct += map1.get(item) * map2.get(item); // Multiply the values to which the specified key is mapped
		}													 // and add them to dotProduct

		// Calculate magnitude a
		for (Integer k : map1.keySet()) {		// 0(n)
			magnitudeA += Math.pow(map1.get(k), 2);
		}

		// Calculate magnitude b
		for (Integer k : map2.keySet()) {		// 0(n) 
			magnitudeB += Math.pow(map2.get(k), 2);
		}
		double magA = Math.sqrt(magnitudeA);
		double magB = Math.sqrt(magnitudeB);
	
		// return cosine similarity
		similarity = dotProduct / (magA * magB);
		
		
		return similarity;
		
	}
	   
	
}
