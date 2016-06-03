package hr.fer.apt;

import hr.fer.apt.helpers.Classes;
import hr.fer.apt.helpers.FileTuple;
import hr.fer.apt.helpers.NBTestType;
import hr.fer.apt.interfaces.NBTest;
import hr.fer.apt.tests.NBTestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			

			NBTest test = Main.prepareTest(
					"comp.graphics", Classes.COMP,
					"sci.space", Classes.SCI,
					NBTestType.MULTINOMIAL_NB
			);
			test.runTest();

			System.out.println( test.getTruePositives() + " | " + test.getFalsePositives());
			System.out.println("-----------------------------------------------------");
			System.out.println( test.getFalseNegatives() + " | " + test.getTrueNegatives());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static NBTest prepareTest(String sourceDomain, Classes sourceClass, String additionalDomain, Classes additionalClass, NBTestType type) {
		InvertedIndex main = new InvertedIndex("data/" + sourceDomain + "/train");
		InvertedIndex diff = new InvertedIndex("data/" + additionalDomain + "/train");

		NBTestBuilder builder = new NBTestBuilder();

		NBTest test = builder.build(
				type,
				main,
				diff,
				Classes.COMP);

		File folder = new File("data/" + additionalDomain + "/test");
		File[] listOfFiles = folder.listFiles();

		ArrayList<FileTuple> diffDomainTuplles = new ArrayList<>();
		for (File file : listOfFiles) {
			diffDomainTuplles.add( new FileTuple(file, additionalClass));
		}
		test.addTestingMaterial( diffDomainTuplles );



		folder = new File("data/comp.graphics/test");
		listOfFiles = folder.listFiles();

		ArrayList<FileTuple> mainDomainTuples = new ArrayList<>();
		for (File file : listOfFiles) {
			mainDomainTuples.add( new FileTuple(file, sourceClass));
		}
		test.addTestingMaterial( mainDomainTuples );

		return test;
	}
 
}
