package com.mycompany.app;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;

public class MyAnalyzer extends Analyzer {

  protected TokenStreamComponents createComponents(String s) {

    LetterTokenizer tokenizer = new LetterTokenizer();

    TokenStream tokenstream = new StopFilter(tokenizer, EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);

    tokenstream = new PorterStemFilter(tokenstream);

    return new TokenStreamComponents(tokenizer, tokenstream);
  }

}