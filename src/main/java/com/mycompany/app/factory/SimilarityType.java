package com.mycompany.app.factory;

public enum SimilarityType {

	BM25("BM25"), Boolean("Boolean"), Multi("Multi"), LMDirichlet("LMDirichlet");

	private String similarityName;

	private SimilarityType(String similarityName) {
		this.similarityName = similarityName;
	}

	@Override
	public String toString() {
		return similarityName;
	}
}
