package org.ire.main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.ire.uima.tika.TikaExtraction;
import org.ire.util.ClassType;
import org.ire.util.FileExtractor;
import org.ire.util.PrintAnnotations;
import org.ire.util.ScoreCalculator;

public class ExampleApplication {

	private static final String CAPITALIZE_ANNOTATOR = "analysis_engine/CapitalAnnotator.xml";
	private static final String UNIGRAM_ANNOTATOR = "analysis_engine/SimpleTokenAndSentenceAnnotator.xml";
	private static final String BIGRAM_ANNOTATOR ="analysis_engine/SimpleEmailRecognizer_RegEx_TAE.xml";
	private static final String INPUTDIRNAME="rawdata";
	private static final String EXTRACTEDDIRNAME="data";
	private static FileExtractor extractor;

	public static String getAnnotator(String value) {
		ClassType input=ClassType.values()[Integer.parseInt(value)];
		switch(input){
		case UNIGRAM: return UNIGRAM_ANNOTATOR;
		
		case BIGRAM: return BIGRAM_ANNOTATOR;
		
		case CAPITALIZE: return CAPITALIZE_ANNOTATOR;
		
		case SENTENCECOUNT: return CAPITALIZE_ANNOTATOR;
		
		default: return UNIGRAM_ANNOTATOR; 	
			
		}
	}

	public static ClassType getClassType(String value) {
		return ClassType.values()[Integer.parseInt(value)];
	}

	public static void main(String[] args) {
		try {
			File taeDescriptor = null;
			File extractedDir = null;
            File inputDir = null;
            inputDir = new File(INPUTDIRNAME);
            extractedDir = new File(EXTRACTEDDIRNAME);

			System.out
					.println("Enter the features 1. unigram \n 2. bigram \n 3.Capitalize ");
			Scanner scanner = new Scanner(System.in);
			String inp = scanner.nextLine();
			String featureList[] = inp.split(",");
			extractor = new FileExtractor();
			for (String feature : featureList) {

				String annotator = getAnnotator(feature);
				ClassType featureClass = getClassType(feature);
				System.out.println(annotator);
				taeDescriptor = new File(annotator);
				// CAPITALIZE_ANNOTATOR);
				
				// get Resource Specifier from XML file
				XMLInputSource in = new XMLInputSource(taeDescriptor);
				ResourceSpecifier specifier = UIMAFramework.getXMLParser()
						.parseResourceSpecifier(in);

				// create Analysis Engine
				AnalysisEngine ae = UIMAFramework
						.produceAnalysisEngine(specifier);
				// create a CAS
				CAS cas = ae.newCAS();
				TikaExtraction.extractTextFromRawData(INPUTDIRNAME,EXTRACTEDDIRNAME);
				
				File[] files = extractedDir.listFiles();
				if (files == null) {
					System.out.println("No files to process");
				} else {
					 //converting documents
					for (int i = 0; i < files.length; i++) {
						if (!files[i].isDirectory()) {
						   
							processFile(files[i], ae, cas,featureClass);
						}
					}
				}
				ae.destroy();
			}
			ScoreCalculator sc = new ScoreCalculator();
			sc.process();
			sc.printMatrix();
			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes a single XML file and prints annotations to System.out
	 * 
	 * @param aFile
	 *            file to process
	 * @param aAE
	 *            Analysis Engine that will process the file
	 * @param aCAS
	 *            CAS that will be used to hold analysis results
	 */
	private static void processFile(File aFile, AnalysisEngine aAE, CAS aCAS,ClassType classType)
			throws IOException, AnalysisEngineProcessException {

		String fileName = aFile.getName();
		System.out.println("Processing file " + aFile.getName());

		// String document = FileUtils.file2String(aFile);

		String document = extractor.getDocument(aFile.getName());

		// document = document.trim();

		// put document text in CAS
		aCAS.setDocumentText(document);

		// process
		aAE.process(aCAS);

		// print annotations to System.out
		PrintAnnotations.printAnnotations(aCAS, aCAS.getAnnotationType(),
				System.out, fileName,classType);

		// reset the CAS to prepare it for processing the next document
		aCAS.reset();
	}

}
