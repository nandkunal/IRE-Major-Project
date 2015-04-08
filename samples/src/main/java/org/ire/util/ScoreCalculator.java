package org.ire.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ScoreCalculator {
	private TreeMap<String, HashMap<String, Integer>> wordSet;
	private static final String OUTPUT_PATH = "wordScore/word.txt";
	private static final String INPUTFOLDER = "token-output";
	private PrintWriter pr;
	private List<String> fileNames;

	public ScoreCalculator() {
		wordSet = new TreeMap<String, HashMap<String, Integer>>();
		fileNames = new ArrayList<String>();
		try {
			pr = new PrintWriter(new File(OUTPUT_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void process() {
		File files = new File(INPUTFOLDER);
		String inpFiles[] = files.list();

		for (int i = 0; i < inpFiles.length; i++) {
			System.out.println("calculating frequency of " + inpFiles[i]);
			extractWordScoreFromFile(inpFiles[i]);
		}
	}

	public void extractWordScoreFromFile(String file) {
		try {
			Scanner sc = new Scanner(new File(INPUTFOLDER + "/" + file));

			String actualFileName = null, data = null;
			if (sc.hasNext()) {
				actualFileName = sc.nextLine();
				fileNames.add(actualFileName);
			}
			if (sc.hasNext())
				data = sc.nextLine();
			if (data != null) {
				String dataWord[] = data.split(",");
				HashMap<String, Integer> temp;
				for (int i = 0; i < dataWord.length; i++) {
					// System.out.println(dataWord[i]);
					if (wordSet.containsKey(dataWord[i])) {
						if (wordSet.get(dataWord[i])
								.containsKey(actualFileName)) {
							wordSet.get(dataWord[i]).put(
									actualFileName,
									wordSet.get(dataWord[i])
											.get(actualFileName) + 1);

						} else {
							wordSet.get(dataWord[i]).put(actualFileName, 1);
						}
					} else {
						HashMap<String, Integer> value = new HashMap<String, Integer>();
						value.put(actualFileName, 1);
						wordSet.put(dataWord[i], value);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void printResult() {
		System.out.println("-------result----------");
		System.out.print("FileNames,");

		for (String file : fileNames) {
			System.out.print(file + ",");
		}
		System.out.println();
		for (Map.Entry<String, HashMap<String, Integer>> entry : wordSet
				.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(",");
			for (String file : fileNames) {
				if (entry.getValue().containsKey(file)) {
					System.out.print(entry.getValue().get(file));
				} else
					System.out.print("0");
				System.out.print(",");
			}
			System.out.println();
		}
	}

	public void printOutPut() {
		StringBuilder br = new StringBuilder();
		;
		pr.print("FileNames,");
		for (String file : fileNames) {
			// pr.print(file+",");
			br.append(file + ",");
		}
		pr.print(br.toString().subSequence(0, br.toString().length() - 1));
		pr.println();
		for (Map.Entry<String, HashMap<String, Integer>> entry : wordSet
				.entrySet()) {
			pr.print(entry.getKey());
			pr.print(",");
			br = new StringBuilder();
			for (String file : fileNames) {
				if (entry.getValue().containsKey(file)) {
					// pr.print(entry.getValue().get(file));
					br.append(entry.getValue().get(file));
				} else {
					// pr.print("0");
					br.append("0");
				}
				br.append(",");
				// pr.print(",");
			}
			pr.print(br.toString().subSequence(0, br.toString().length() - 1));
			pr.println();
		}
	}

	public void close() {
		pr.close();
	}

	public static void main(String[] args) {
		ScoreCalculator sc = new ScoreCalculator();
		sc.process();
		// sc.printResult();
		sc.printOutPut();
		sc.close();
	}
}
