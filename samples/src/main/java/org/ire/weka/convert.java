package org.ire.weka;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;
 
public class convert {
  private static final String ARFF_FILE = "wordScore/wordOut1.arff";
  private static final String ARFF_FILE1 = "wordScore/wordOut2.arff";
private static final String WORD_SCORE_WORD_OUT_TXT = "wordScore/word.txt";

/**
   * takes 2 arguments:
   * - CSV input file
   * - ARFF output file
   */
  public static void main(String[] args) throws Exception {
       
    // load CSV
    CSVLoader loader = new CSVLoader();
    loader.setSource(new File(WORD_SCORE_WORD_OUT_TXT));
    Instances data = loader.getDataSet();
 
    // save ARFF
   
   // File arff = new File(ARFF_FILE);
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);
    saver.setFile(new File(ARFF_FILE));
    saver.setDestination(new File(ARFF_FILE1));
    System.out.println("here");
    saver.writeBatch();
  }
}
