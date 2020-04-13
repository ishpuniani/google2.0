package com.mycompany.app.factory;

public enum AnalyzerType {
	
	Simple("Simple"),
	Standard("Standard") ,
	English("English"),
	Custom("Custom");

	private String analyzerName;
	private AnalyzerType(String analyzerName) {
		this.analyzerName = analyzerName;
	}

	@Override
	public String toString(){
		return analyzerName;
	}
}
