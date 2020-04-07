package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.app.factory.AnalyzerType;
import com.mycompany.app.factory.SimilarityType;

import org.apache.commons.lang3.tuple.Pair;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<AnalyzerType> analyzers = new ArrayList<>();
        analyzers.add(AnalyzerType.Simple);
        analyzers.add(AnalyzerType.English);
        analyzers.add(AnalyzerType.Custom);
        analyzers.add(AnalyzerType.Standard);

        ArrayList<SimilarityType> similarities = new ArrayList<>();
        similarities.add(SimilarityType.BM25);
        similarities.add(SimilarityType.Boolean);
        similarities.add(SimilarityType.Classic);

        ArrayList<Pair<AnalyzerType, SimilarityType>> pairs = new ArrayList<Pair<AnalyzerType, SimilarityType>>();

        for (AnalyzerType analyzer : analyzers) {
            for (SimilarityType similarity : similarities) {
                Pair<AnalyzerType, SimilarityType> pair = Pair.of(analyzer, similarity);
                pairs.add(pair);
            }
        }

        IndexDocuments indexer = new IndexDocuments();
        SearchIndex searcher = new SearchIndex();

        for (Pair<AnalyzerType, SimilarityType> pair1 : pairs) {
            for (Pair<AnalyzerType, SimilarityType> pair2 : pairs) {
                indexer.Index(pair1);
                searcher.Search(pair2);
                if (pair1 != pair2) {
                    indexer.Index(pair2);
                    searcher.Search(pair1);
                }
            }

            File dir = new File("Indexed-Docs");
            for (File file : dir.listFiles()) {
                if (!file.isDirectory())
                    file.delete();
            }
        }
    }
}