package hr.fer.apt.tests;

import hr.fer.apt.Index;
import hr.fer.apt.InvertedIndex;
import hr.fer.apt.helpers.Classes;
import hr.fer.apt.helpers.FileTuple;
import hr.fer.apt.interfaces.NBTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vilimstubican on 02/06/16.
 */
public abstract class BaseNBTest implements NBTest {
    protected List<FileTuple> files;

    protected InvertedIndex sourceDomain;
    protected InvertedIndex diffSourceDomain;

    protected Classes targetClass;

    protected int truePositives;
    protected int trueNegatives;
    protected int falsePositives;
    protected int falseNegatives;

    public BaseNBTest(){
        this.files = new ArrayList<>();
        this.truePositives  = 0;
        this.trueNegatives  = 0;
        this.falsePositives = 0;
        this.falseNegatives = 0;
    }

    @Override
    public void addTestingMaterial(List<FileTuple> materials) {
        this.files.addAll( materials );
    }


    @Override
    public void runTest() {
        for(  FileTuple f: this.files ) {
            try {
                this.runSingleTest( f );
            } catch (Exception e) {
                // Just go over it
            }
        }
    }

    protected void runSingleTest(FileTuple tuple) throws Exception {
        File f = tuple.getFile();
        Index idx = new Index( f );

        this.calculateResult(idx, tuple.getFileClass());

    }

    abstract void calculateResult(Index idx, Classes testableClass);

    @Override
    public int getTruePositives() {
        return this.truePositives;
    }

    @Override
    public int getTrueNegatives() {
        return this.trueNegatives;
    }

    @Override
    public int getFalsePositives() {
        return this.falsePositives;
    }

    @Override
    public int getFalseNegatives() {
        return this.falseNegatives;
    }

    public void setSourceDomain(InvertedIndex sourceDomain) {
        this.sourceDomain = sourceDomain;
    }

    public void setDiffSourceDomain(InvertedIndex diffSourceDomain) {
        this.diffSourceDomain = diffSourceDomain;
    }

    public void setTargetClass(Classes targetClass) {
        this.targetClass = targetClass;
    }
}
