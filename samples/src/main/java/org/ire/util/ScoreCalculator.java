package org.ire.util;

import java.util.HashMap;
import java.util.TreeMap;

public class ScoreCalculator {
	private TreeMap<String,HashMap<String, Integer> > wordSet;
	
	public ScoreCalculator() {
		wordSet = new TreeMap<String, HashMap<String,Integer>>();
	}
}
