package com.mycompany.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.Constants.Constants;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.wordnet.SynonymTokenFilter;
import org.tartarus.snowball.ext.EnglishStemmer;

public class MyAnalyzer extends Analyzer {

	Logger logger = Logger.getLogger(MyAnalyzer.class);

	private final Path currentRelativePath = Paths.get("").toAbsolutePath();
	public org.apache.lucene.wordnet.SynonymMap wnSynonymMap;
	public SynonymMap synonymMap;
	boolean mapFound = false;
	boolean useSynonyms = false;

	public MyAnalyzer() {};

	public MyAnalyzer(boolean useSynonyms) {
		this.useSynonyms = useSynonyms;
		if(useSynonyms) {
			File mapFile = new File(Constants.SYNONYM_FILE_PATH);
			try {
				wnSynonymMap = new org.apache.lucene.wordnet.SynonymMap(new FileInputStream(mapFile));
//				synonymMap = createCountrySynonymMap();
				mapFound = true;
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	//https://github.com/kerinb/IR_proj2_group14
	private SynonymMap createCountrySynonymMap() {
		SynonymMap synMap = new SynonymMap(null, null, 0);
		try {
			BufferedReader countries = new BufferedReader(new FileReader(currentRelativePath + "/DataSet/countries.txt"));

			final SynonymMap.Builder builder = new SynonymMap.Builder(true);
			String country = countries.readLine();

			while(country != null) {
				builder.add(new CharsRef("country"), new CharsRef(country), true);
				builder.add(new CharsRef("countries"), new CharsRef(country), true);
				country = countries.readLine();
			}

			synMap = builder.build();
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getLocalizedMessage() + "occurred when trying to create synonym map");
		}
		return synMap;
	}

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

		if(useSynonyms && mapFound) {
			logger.info("Adding SynonymTokenFilter to analyzer");
			tokenStream = new FlattenGraphFilter(new WordDelimiterGraphFilter(tokenStream, WordDelimiterGraphFilter.SPLIT_ON_NUMERICS |
					WordDelimiterGraphFilter.GENERATE_WORD_PARTS | WordDelimiterGraphFilter.GENERATE_NUMBER_PARTS |
					WordDelimiterGraphFilter.PRESERVE_ORIGINAL , null));

//			tokenStream = new SynonymGraphFilter(tokenStream, synonymMap, true);
//			tokenStream = new FlattenGraphFilter(tokenStream);
			tokenStream = new SynonymTokenFilter(tokenStream, wnSynonymMap, 3);

			// after replacing with wordnet synonyms
			tokenStream = new FlattenGraphFilter(new WordDelimiterGraphFilter(tokenStream, WordDelimiterGraphFilter.SPLIT_ON_NUMERICS |
					WordDelimiterGraphFilter.GENERATE_WORD_PARTS | WordDelimiterGraphFilter.GENERATE_NUMBER_PARTS |
					WordDelimiterGraphFilter.PRESERVE_ORIGINAL , null));
		}

		//Remove stop words
		tokenStream = new StopFilter(tokenStream, StopFilter.makeStopSet(createStopWordList(),true));

		//Apply Stemming
//		tokenStream = new PorterStemFilter(tokenStream);
		tokenStream = new SnowballFilter(tokenStream, new EnglishStemmer());

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