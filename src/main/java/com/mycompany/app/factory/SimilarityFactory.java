package com.mycompany.app.factory;

import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;

public class SimilarityFactory {
	
	public static Similarity getSimilarity(SimilarityType type) {
		
		switch(type) {
			case BM25:
				return new BM25Similarity();
			
			default:
				return new ClassicSimilarity();
		}
		
	}

}
