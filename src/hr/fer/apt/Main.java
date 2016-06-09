package hr.fer.apt;

import hr.fer.apt.helpers.Classes;
import hr.fer.apt.helpers.FileTuple;
import hr.fer.apt.helpers.NBTestType;
import hr.fer.apt.interfaces.NBTest;
import hr.fer.apt.tests.NBTestBuilder;

import java.io.File;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
//		Main.MultinomialNBNoDomainAdaptation();
		Main.MultinomialNBDomainAdaptation();
	}


	public static void	MultinomialNBDomainAdaptation() {
		try {
			NBTest test = Main.prepareTest(
					"comp.graphics", Classes.COMP,
					"rec.autos", Classes.REC,
					"comp.windows.x", "rec.motorcycles",
					NBTestType.MULTINOMIAL_NBDA
			);

			System.out.println( "True Positive  | False Positive");
			System.out.println("--------------------------------");
			System.out.println( "False Negative | True Negative");


			System.out.println("");
			System.out.println("Calculating...");

			test.runTest();

			System.out.println( test.getTruePositives() + " | " + test.getFalsePositives());
			System.out.println("-----------------------------------------------------");
			System.out.println( test.getFalseNegatives() + " | " + test.getTrueNegatives());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void	MultinomialNBNoDomainAdaptation() {
		try {
			NBTest test = Main.prepareTest(
					"comp.graphics", Classes.COMP,
					"sci.crypt", Classes.SCI,
					"comp.windows.x", "sci.electronics",
					NBTestType.MULTINOMIAL_NB
			);

			System.out.println( "True Positive  | False Positive");
			System.out.println("--------------------------------");
			System.out.println( "False Negative | True Negative");


			System.out.println("");
			System.out.println("Calculating...");

			test.runTest();

			System.out.println( test.getTruePositives() + " | " + test.getFalsePositives());
			System.out.println("-----------------------------------------------------");
			System.out.println( test.getFalseNegatives() + " | " + test.getTrueNegatives());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static NBTest prepareTest(String sourceDomain, Classes sourceClass,
									 String additionalDomain, Classes additionalClass,
									 String sourceTestDomain, String additionalTestDomain,
									 NBTestType type) {
		InvertedIndex main = new InvertedIndex("data/" + sourceDomain + "/train");
		InvertedIndex diff = new InvertedIndex("data/" + additionalDomain + "/train");

		NBTestBuilder builder = new NBTestBuilder();

		NBTest test = builder.build(
				type,
				main,
				diff,
				Classes.COMP);

		File folder = new File("data/" + additionalTestDomain + "/test");
		File[] listOfFiles = folder.listFiles();

		ArrayList<FileTuple> diffDomainTuplles = new ArrayList<>();
		for (File file : listOfFiles) {
			diffDomainTuplles.add( new FileTuple(file, additionalClass));
		}
		test.addTestingMaterial( diffDomainTuplles );



		folder = new File("data/"+ sourceTestDomain + "/test");
		listOfFiles = folder.listFiles();

		ArrayList<FileTuple> mainDomainTuples = new ArrayList<>();
		for (File file : listOfFiles) {
			mainDomainTuples.add( new FileTuple(file, sourceClass));
		}
		test.addTestingMaterial( mainDomainTuples );

		return test;
	}
 
}
