package hr.fer.zemris.java.student0036461026.hw12;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Program is a search engine for stored documents. User can search documents 
 * using keyword and program will give best match results for given query.
 * Program expects one argument: path to the documents folder (lib\clanci already provided).
 * @author Tomislav
 *
 */

public class Trazilica {
	
	/**
	 * Map of all words contained in some document. Document name is the key and 
	 * value is another map which key is word found in document and value is her number 
	 * of apperiances in current document.
	 */
	private static Map<String, Map<String, Integer>> articleWords = new HashMap<>();
	/**
	 * List of all stop words of croatian language.
	 */
	private static List<String> stopWords;
	/**
	 * Word vocabulary search engine works with.
	 */
	private static Set<String> vocabulary = new LinkedHashSet<>();
	/**
	 * Vocabulary as list.
	 */
	private static List<String> vocabularyList;
	/**
	 * List of inverse document frequency values for every word in vocabulary.
	 */
	private static List<Double> idf = new ArrayList<>();
	/**
	 * List of document/article objects program have in database.
	 */
	private static List<Article> articles = new ArrayList<>();
	/**
	 * List of documents that are results of processed query.
	 */
	private static List<Article> results = new ArrayList<>();
	/**
	 * List of resulting documents similarities with given query.
	 */
	private static List<Double> similarities = new ArrayList<>();
	/**
	 * Format of decimal numbers when they are printed.
	 */
	private static DecimalFormat df = new DecimalFormat("#.####"); 

	/**
	 * Method called at program start.
	 * @param args Command line arguments.
	 * @throws IOException If error occurs while reading documents or user input.
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			throw new IllegalArgumentException("No path to the articles provided");
		}
		df.setMinimumFractionDigits(4);
		File root = new File(args[0]);		
		if (!root.exists()) {
			throw new IllegalArgumentException(root+" does not exist");
		}
		stopWords = Files.readAllLines(Paths.get("lib/stop_rijeci.txt"),
				StandardCharsets. UTF_8);
		
		Pattern pattern = Pattern.compile("\\p{L}+", Pattern.CASE_INSENSITIVE);			
		Matcher matcher;
		File[] documents = root.listFiles();
		if (documents!= null) {
			for (File article : documents) {
				HashMap<String, Integer> map = new HashMap<>();			
				String[] lines = Files.readAllLines(Paths.get(article.getPath()),
						StandardCharsets. UTF_8).toArray(new String[0]);
				for (String line : lines) {
					matcher = pattern.matcher(line);
					while(matcher.find()) {
						String word = line.substring(matcher.start(),matcher.end()).toLowerCase();
						if (!stopWords.contains(word)) {
							Integer count = map.get(word);
							map.put(word, count == null ? 1 : count+1);
							vocabulary.add(word);
						}
					}
				}
				articleWords.put(article.toString(), map);
			}
		}
		vocabularyList = new ArrayList<String>(vocabulary);
		
		for (int i = 0; i < vocabularyList.size(); i++) {
			int count = 0;
			Iterator<Map<String, Integer>> it = articleWords.values().iterator();
			while(it.hasNext()) {
				if ((it.next()).containsKey(vocabularyList.get(i))) {
					count++;
				}
			}
			idf.add(Math.log(articleWords.size()/(double)count));
		}
		
		//calculating tf-idf
		Iterator<Entry<String, Map<String, Integer>>> it2 = articleWords.entrySet().iterator();
		while(it2.hasNext()) {
			Map.Entry<String, Map<String, Integer>> pair = (Entry<String, Map<String, Integer>>) it2.next();
			String documentPath = (String) pair.getKey();
			Map<String, Integer> map = (Map<String, Integer>) pair.getValue();		
			Article article = new Article(calculateTfIdfVector(map), Paths.get(documentPath));
			articles.add(article);
		}
		
		System.out.println("Dictionary size is "+vocabulary.size()+" words.");
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
		
		while(true) {
			System.out.print("Enter command > ");
			String line = reader.readLine();
			if (!line.isEmpty()) {
				String[] words = line.split("\\s+");
				if (words[0].equals("query")) {
					query(line);
				}
				else if(words[0].equals("type")) {
					Integer index;
					try{
						index = Integer.parseInt(words[1]);
					}catch (Exception ex){
						System.out.println("Illegal index format");
						continue;
					}
					if (index < 0 || index > results.size()-1) {
						System.out.println("Document for given index not found");
						continue;
					}
					System.out.println("Document: " + results.get(index).path+"\n");
					String[] documentLines = Files.readAllLines(results.get(index).path,
							StandardCharsets. UTF_8).toArray(new String[0]);
					for (String documenLine : documentLines) {
						System.out.println(documenLine);
					}
				}
				else if(words[0].equals("results")) {
					printResults();
				}
				else if(words[0].equals("exit")) {
					break;
				}
				else {
					System.out.println("Unknown command");
				}
			}
		}
	}
	
	/**
	 * Method processes given query. Searches for best document match and
	 * outputs resulting documents and similarities with given query. 
	 * @param line Query line.
	 */
	private static void query(String line) {
		results.clear();
		similarities.clear();
		Pattern pattern = Pattern.compile("\\p{L}+", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		boolean keywordRemoved = false;
		while(matcher.find()) {
			if (!keywordRemoved) {
				keywordRemoved = true;
				continue;
			}
			String word = line.substring(matcher.start(),matcher.end()).toLowerCase();
			if (!stopWords.contains(word) && vocabulary.contains(word)) {
				Integer count = map.get(word);
				map.put(word, count == null ? 1 : count+1);
			}
		}
		Article queryArticle = new Article(calculateTfIdfVector(map), null);
		for (Article article : articles) {
			double similarity = queryArticle.tfidfVector.scalarProduct(article.tfidfVector)
					/ (queryArticle.tfidfVector.norm() * article.tfidfVector.norm());
			if (similarity > 0) {
				if (results.isEmpty()) {
					results.add(article);
					similarities.add(similarity);
				}
				else {
					boolean inserted = false;
					for(int i = 0; i < similarities.size(); i++) {
						if(similarity > similarities.get(i)) {
							similarities.add(i, similarity);
							results.add(i, article);
							inserted = true;
							break;
						}
					}
					if (!inserted) {
						results.add(article);
						similarities.add(similarity);
					}
				}
			}
		}
		boolean first = true;
		String queryWords = "Query is: [";
		for (String key : map.keySet()) {
			if(first) {
				queryWords += key;
				first = false;
			}
			else {
				queryWords += ", " + key; 
			}
		}
		queryWords += "]";
		System.out.println(queryWords);
		System.out.println("Best 10 results:");
		printResults();
	}
	
	/**
	 * Method prints up to 10 resulting documents that matches query ordered by
	 * similarity starting from best match.
	 */
	private static void printResults() {
		for (int i = 0; i < (results.size() < 10 ? results.size() : 10); i++) {
			System.out.println("["+i+"] ("+df.format(similarities.get(i))+") "+results.get(i).path);
		}
	}
	
	/**
	 * Method calculates TF-IDF vector of document for words in vocabulary.
	 * @param map Map of documents words and their counts.
	 * @return TF-IDF vector of document.
	 */
	private static double[] calculateTfIdfVector(Map<String, Integer> map) {
		double[] vector = new double[vocabularyList.size()];
		for (int i = 0; i < vector.length; i++) {
			Integer tf = map.get(vocabularyList.get(i));
			vector[i] = tf == null ? 0 : tf*idf.get(i);
		}
		return vector;
	}
	
	/**
	 * Class that represents document once initial stage is complete.
	 * It has path to the document and TF-IDF document vector used when calculating
	 * similarities.
	 * @author Tomislav
	 *
	 */
	private static class Article {
		
		/**
		 * Path to the file.
		 */
		Path path;
		/**
		 * TF-IDF vector.
		 */
		private Vector tfidfVector;
		
		/**
		 * Default constructor with two parameters.
		 * @param tfidfVector TF-IDF vector.
		 * @param path Path to the file.
		 */
		public Article(double[] tfidfVector, Path path) {
			this.tfidfVector = new Vector(false,true,tfidfVector);
			this.path = path;
		}
	}
}
