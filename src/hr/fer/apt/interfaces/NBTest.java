package hr.fer.apt.interfaces;

import hr.fer.apt.helpers.FileTuple;

import java.util.List;

/**
 * Created by vilimstubican on 02/06/16.
 */
public interface NBTest {

    public void addTestingMaterial(List<FileTuple> materials);

    public void runTest();

    public int getTruePositives();
    public int getTrueNegatives();
    public int getFalsePositives();
    public int getFalseNegatives();
}
