package hr.fer.apt.tests;

import hr.fer.apt.InvertedIndex;
import hr.fer.apt.helpers.Classes;
import hr.fer.apt.helpers.NBTestType;
import hr.fer.apt.interfaces.NBTest;

/**
 * Created by vilimstubican on 02/06/16.
 */
public class NBTestBuilder {

    public NBTest build(NBTestType type, InvertedIndex mainDomain, InvertedIndex diffDomain, Classes targetClass) {

        switch (type) {
            case MULTINOMIAL_NB:
                default:
                    MultinomialNBTest t = new MultinomialNBTest();
                    t.setSourceDomain( mainDomain );
                    t.setDiffSourceDomain( diffDomain );
                    t.setTargetClass( targetClass );
                    return t;
        }
    }
}
