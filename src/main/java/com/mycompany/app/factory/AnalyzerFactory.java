package com.mycompany.app.factory;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.mycompany.app.MyAnalyzer;

public class AnalyzerFactory {
	
	public static Analyzer getAnalyzer(AnalyzerType type) {
		
		switch(type) {
			case Simple:
				return new SimpleAnalyzer();
			
			case Standard:
				return new StandardAnalyzer();
				
			case English:
				return new EnglishAnalyzer();
				
			case Custom:
				return new MyAnalyzer();
				
			default:
				return null;
		}		
	}
}