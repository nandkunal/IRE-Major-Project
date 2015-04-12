package org.ire.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.attribute.AclEntry.Builder;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileExtractor {
	
	private static final String SPACE = " ";
	private static final String STOPWORD = "resources/stopwords.txt";
	private String file;
	private Scanner sc;
	private String document;
	private Set<String> stopWordSet;
	private File input;
	
	public FileExtractor() {
		
		stopWordSet = new HashSet<String>();
		System.out.println("Loading Stopwords..");
		try {
			sc = new Scanner(new File(STOPWORD));
			String temp;
			while(sc.hasNext()){
				temp=sc.nextLine();
				if(!stopWordSet.contains(temp))
					stopWordSet.add(temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
	}
	
	public String getDocument(String inputTextFile){
		StringBuilder builder = null;
		try {
			File input = new File("data/"+inputTextFile);
			sc = new Scanner(input);
			String line,words[];
			builder = new StringBuilder();
			while(sc.hasNext()){
				line=sc.nextLine();
				line=line.trim();
				words=line.split(SPACE);
				for(int i=0;i<words.length;i++){
					//words[i]=words[i].toLowerCase();
					if(!stopWordSet.contains(words[i].toLowerCase())){
						words[i]=words[i].replaceAll("\\W", "");
						//System.out.print(words[i]+SPACE);;
						builder.append(words[i]);
						builder.append(SPACE);
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return builder.toString();
	}
}
