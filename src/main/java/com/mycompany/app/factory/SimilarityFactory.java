package com.mycompany.app.factory;

import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.MultiSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;

public class SimilarityFactory {

	public static Similarity getSimilarity(SimilarityType type) {

		switch (type) {
			case BM25:
				return new BM25Similarity();
			case Boolean:
				return new BooleanSimilarity();
			case Multi:
				return new MultiSimilarity(null);
			case LMDirichlet:
				return new LMDirichletSimilarity();
			default:
				return new ClassicSimilarity();
		}
	}
}
