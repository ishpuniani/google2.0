package com.mycompany.app.factory;

public enum SimilarityType {
	
	BM25("BM25"),
	Boolean("Boolean"),
	Multi("Multi"),
	PerField("PerField"),
	Base("Base"),
	TFIDF("TFIDF");

	private String similarityName;
	private SimilarityType(String similarityName) {
		this.similarityName = similarityName;
	}

	@Override
	public String toString(){
		return similarityName;
	}
}
