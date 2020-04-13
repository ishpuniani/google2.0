package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class MyAnalyzer extends Analyzer {

	private final Path currentRelativePath = Paths.get("").toAbsolutePath();

	@Override
	protected TokenStreamComponents createComponents(String s) {

		//To break sentences into tokens
		Tokenizer tokenizer = new StandardTokenizer();

		//Removes punctuation marks such as an apostrophe
		TokenStream tokenStream = new ClassicFilter(tokenizer);

		//Normalizes token text to lower case.
		tokenStream = new LowerCaseFilter(tokenStream);

		//Trims leading and trailing whitespace from Tokens in the stream
		tokenStream = new TrimFilter(tokenStream);

		//Remove stop words
		tokenStream = new StopFilter(tokenStream, StopFilter.makeStopSet(createStopWordList(),true));

		//Apply Stemming
		tokenStream = new PorterStemFilter(tokenStream);

		return new TokenStreamComponents(tokenizer, tokenStream);
	}

	//https://github.com/kerinb/IR_proj2_group14
	// getting stopwords from https://www.ranks.nl/stopwords
	private List<String> createStopWordList() {
		ArrayList<String> stopWordList = new ArrayList<>();
		try {
			BufferedReader stopwords = new BufferedReader(new FileReader(currentRelativePath + "/Dataset/stopwords.txt"));
			String word = stopwords.readLine();
			while(word != null) {
				stopWordList.add(word);
				word = stopwords.readLine();
			}
			stopwords.close();
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getLocalizedMessage() + "occurred when trying to create stopword list");
		}
		return stopWordList;
	}

}