package hr.fer.apt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by vilimstubican on 02/06/16.
 */
public class Index {
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

    private Map<String, Integer> words;

    public Index(File file) throws Exception {
        this.words = new HashMap<>();

        this.indexFile( file );
    }

    private void indexFile(File file) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader( file ));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            for (String _word : line.split("\\W+")) {
                String word = _word.toLowerCase();
                if (stopwords.contains(word))
                    continue;
                if( !this.words.containsKey( _word ) ){
                    this.words.put( _word, 0 );
                }

                this.words.put(
                        _word,
                        this.words.get( _word ) + 1
                );
            }
        }
    }

    public List<String> getWords(){
        return new ArrayList<>( this.words.keySet() );
    }

    public int getWordAmount( String word ) {
        if( ! this.words.containsKey( word )) return 0;

        return this.words.get( word );
    }

}
