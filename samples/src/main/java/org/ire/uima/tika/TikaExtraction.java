package org.ire.uima.tika;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class TikaExtraction {

	private static final String RAWDATA = "./rawdata";
	private File out;
	private PrintWriter writer;

	public void extractAndSaveInFile(File file, int filenum) {
		Tika tika = new Tika();
		try {
			String filecontent = tika.parseToString(file);
			out = new File("./data/" + filenum + ".txt");
			writer = new PrintWriter(out);
			writer.println(file.getName());
			writer.println(filecontent);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}

	}

	public void extractTextFromRawData() {
		File folder = new File(RAWDATA);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			extractAndSaveInFile(listOfFiles[i], i);
		}
	}

	public static void main(final String[] args) throws IOException,
			TikaException {
		TikaExtraction obj = new TikaExtraction();
		obj.extractTextFromRawData();

	}

}
