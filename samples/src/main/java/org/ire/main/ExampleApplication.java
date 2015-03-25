package org.ire.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.FileUtils;
import org.apache.uima.util.XMLInputSource;
import org.ire.util.PrintAnnotations;
import org.ire.uima.tika.TikaExtraction;
import org.ire.uima.tokenizer.Sentence_Type;
import org.ire.util.FileExtractor;

/**
 * An example application that reads documents from files, sends them though an
 * Analysis Engine, and prints all discovered annotations to System.out.
 * <p>
 * The application takes two arguments:
 * <ol type="1">
 * <li>The path to an XML descriptor for the Analysis Engine to be executed</li>
 * <li>An input directory containing files to be processed</li>
 * </ol>
 */
public class ExampleApplication {
	/**
	 * Main program.
	 * 
	 * @param args
	 *            Command-line arguments - see class description
	 */

	private static FileExtractor  extractor;

	
	public static void main(String[] args) {
		try {
			File taeDescriptor = null;
			File inputDir = null;

			//TikaExtraction obj = new TikaExtraction();
			//obj.extractTextFromRawData();
			extractor = new FileExtractor();
			// Read and validate command line arguments
			boolean validArgs = true;// false;
			/*
			 * if (args.length == 2) { taeDescriptor = new File(args[0]);
			 * inputDir = new File(args[1]);
			 * 
			 * validArgs = taeDescriptor.exists() &&
			 * !taeDescriptor.isDirectory() && inputDir.isDirectory(); }
			 */
			taeDescriptor = new File(
					"analysis_engine/SimpleTokenAndSentenceAnnotator.xml");
			inputDir = new File("data");
			if (!validArgs) {
				printUsageMessage();
			} else {
				// get Resource Specifier from XML file
				XMLInputSource in = new XMLInputSource(taeDescriptor);
				ResourceSpecifier specifier = UIMAFramework.getXMLParser()
						.parseResourceSpecifier(in);

				// for debugging, output the Resource Specifier
				// System.out.println(specifier);

				// create Analysis Engine
				AnalysisEngine ae = UIMAFramework
						.produceAnalysisEngine(specifier);
				// create a CAS
				CAS cas = ae.newCAS();

				// get all files in the input directory
				File[] files = inputDir.listFiles();
				if (files == null) {
					System.out.println("No files to process");
				} else {
					// process documents
					for (int i = 0; i < files.length; i++) {
						if (!files[i].isDirectory()) {
							System.out.println("--------------"
									+ files[i].getName() + "----------");
							processFile(files[i], ae, cas);
						}
					}
				}
				ae.destroy();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints usage message.
	 */
	private static void printUsageMessage() {
		System.err
				.println("Usage: java org.apache.uima.example.ExampleApplication "
						+ "<Analysis Engine descriptor or PEAR file name> <input dir>");
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
	private static void processFile(File aFile, AnalysisEngine aAE, CAS aCAS)
			throws IOException, AnalysisEngineProcessException {
		
		String fileName = aFile.getName();
		System.out.println("Processing file " + aFile.getName());

		//String document = FileUtils.file2String(aFile);

		String document =extractor.getDocument(aFile.getName());

		//document = document.trim();

		// put document text in CAS
		aCAS.setDocumentText(document);

		// process
		aAE.process(aCAS);

		// print annotations to System.out
		PrintAnnotations.printAnnotations(aCAS, aCAS.getAnnotationType(),
				System.out, fileName);

		// reset the CAS to prepare it for processing the next document
		aCAS.reset();
	}

}
