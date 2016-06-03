package hr.fer.apt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class InvertedIndex {

	
	private final List<String> stopwords = Arrays.asList("a", "able", "about",
			"across", "after", "all", "almost", "also", "am", "among", "an",
			"and", "any", "are", "as", "at", "be", "because", "been", "but",
			"by", "can", "cannot", "could", "dear", "did", "do", "does",
			"either", "else", "ever", "every", "for", "from", "get", "got",
			"had", "has", "have", "he", "her", "hers", "him", "his", "how",
			"however", "i", "if", "in", "into", "is", "it", "its", "just",
			"least", "let", "like", "likely", "may", "me", "might", "most",
			"must", "my", "neither", "no", "nor", "not", "of", "off", "often",
			"on", "only", "or", "other", "our", "own", "rather", "said", "say",
			"says", "she", "should", "since", "so", "some", "than", "that",
			"the", "their", "them", "then", "there", "these", "they", "this",
			"tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
			"what", "when", "where", "which", "while", "who", "whom", "why",
			"will", "with", "would", "yet", "you", "your");
 
	private Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
	private List<String> files = new ArrayList<String>();

	private Double totalNumberOfWords;
 
	public InvertedIndex(String inPath){
		try {
			File folder = new File(inPath);
			File[] listOfFiles = folder.listFiles();
		
			for (File file : listOfFiles) {
				indexFile(file);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void indexFile(File file) throws IOException {
		int fileno = files.indexOf(file.getPath());
		if (fileno == -1) {
			files.add(file.getPath());
			fileno = files.size() - 1;
		}
 
		int pos = 0;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			for (String _word : line.split("\\W+")) {
				String word = _word.toLowerCase();
				pos++;
				if (stopwords.contains(word))
					continue;
				List<Tuple> idx = index.get(word);
				if (idx == null) {
					idx = new LinkedList<Tuple>();
					index.put(word, idx);
				}
				idx.add(new Tuple(fileno, pos));
			}
		}
		System.out.println("indexed " + file.getPath() + " " + pos + " words");
	}
 
//	public void searchForWordInFiles(List<String> words) {
//		for (String _word : words) {
//			Set<String> answer = new HashSet<String>();
//			String word = _word.toLowerCase();
//			List<Tuple> idx = index.get(word);
//			if (idx != null) {
//				for (Tuple t : idx) {
//					answer.add(files.get(t.fileno)+" position: "+t.position);
//				}
//			}
//			System.out.print(word);
//			for (String f : answer) {
//				System.out.println(" " + f);
//			}
//			System.out.println("");
//		}
//	}
	
	public List<Tuple> getFilesAndWordPosition(String word){
		return index.get(word.toLowerCase());
	}
	
	public List<Tuple> getWordPositionFromFile(String word, String filePath){
		List<Tuple> result = new ArrayList<InvertedIndex.Tuple>();
		List<Tuple> idx = index.get(word.toLowerCase());
		if (idx != null) {
			for (Tuple t : idx) {
				if(files.get(t.fileno).equals(filePath)){
					result.add(t);
				}
			}
		}
		return result;
	}
	
	public List<Tuple> getWordPositionFromFile(String word, int filePosition){
		List<Tuple> result = new ArrayList<InvertedIndex.Tuple>();
		List<Tuple> idx = index.get(word.toLowerCase());
		if (idx != null) {
			for (Tuple t : idx) {
				if(t.fileno == filePosition){
					result.add(t);
				}
			}
		}
		return result;
	}

	public double numberOfWords(){
		if(this.totalNumberOfWords != null) return this.totalNumberOfWords;

		double total = 0.0;
		for(List<Tuple> t : this.index.values()) {
			total += t.size();
		}
		this.totalNumberOfWords = total;
		return total;
	}
	public Set<String> getUniqueWords(){
		return this.index.keySet();
	}
	
	
	class Tuple {
		private int fileno;
		private int position;
 
		public Tuple(int fileno, int position) {
			this.fileno = fileno;
			this.position = position;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "file number: "+fileno+" position: "+position+"\n";
		}
	}
}