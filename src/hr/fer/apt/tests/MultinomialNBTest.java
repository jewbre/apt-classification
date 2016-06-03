package hr.fer.apt.tests;

import hr.fer.apt.Index;
import hr.fer.apt.helpers.Classes;
import hr.fer.apt.helpers.FileTuple;
import hr.fer.apt.interfaces.NBTest;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by vilimstubican on 02/06/16.
 */
public class MultinomialNBTest extends BaseNBTest {

    @Override
    void calculateResult(Index idx, Classes testableClass) {

        double sourceDomainWords = this.sourceDomain.numberOfWords();
        double diffSourceDomainWords = this.diffSourceDomain.numberOfWords();

        Set<String> uniqueWords = new TreeSet<>();
        uniqueWords.addAll( this.sourceDomain.getUniqueWords() );
        uniqueWords.addAll( this.diffSourceDomain.getUniqueWords() );
        double totalUniqueWords = uniqueWords.size();

        double totalWords =  sourceDomainWords + diffSourceDomainWords;
        double Pc = (sourceDomainWords + 1) / totalWords;
        double Pnc = (diffSourceDomainWords + 1) / totalWords;

        double belongsToC = Math.log(Pc);
        double doesNotBelongsToC = Math.log(Pnc);

        for(String word : idx.getWords()) {
            double numberOfShowsInC     = 0.0;
            double numberOfShowsInNC    = 0.0;
            try{
                numberOfShowsInC = this.sourceDomain.getFilesAndWordPosition( word ).size();
            }  catch (Exception e) {}
            try{
                numberOfShowsInNC = this.diffSourceDomain.getFilesAndWordPosition( word ).size();
            } catch (Exception e) {}

            belongsToC += idx.getWordAmount(word) * Math.log(
                    (numberOfShowsInC + 1) /
                    ( sourceDomainWords + totalUniqueWords )
            );
            doesNotBelongsToC += idx.getWordAmount(word) * Math.log(
                    (numberOfShowsInNC + 1 ) /
                    ( diffSourceDomainWords + totalUniqueWords )
            );
        }

        if(belongsToC > doesNotBelongsToC) {
            // Belongs to class C
            if(testableClass == this.targetClass) {
                // True positive
                this.truePositives += 1.0;
            } else {
                // False positive
                this.falsePositives += 1.0;
            }
        } else {
            // Does not belongs to class C
            if(testableClass == this.targetClass) {
                // False negative
                this.falseNegatives += 1.0;
            } else {
                // True negative
                this.trueNegatives += 1.0;
            }
        }
    }
}
