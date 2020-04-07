package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.app.Constants.Constants;
import com.mycompany.app.DocumentLoader.fbis.FBISLoader;
import com.mycompany.app.DocumentLoader.fr94.FR94Loader;
import com.mycompany.app.DocumentLoader.ft.FTLoader;
import com.mycompany.app.DocumentLoader.laTimes.LATimesLoader;
import com.mycompany.app.Index.Indexer;
import com.mycompany.app.factory.AnalyzerType;
import com.mycompany.app.factory.SimilarityType;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.document.Document;

public class IndexDocuments {

    private static ArrayList<Document> docs;
    private static Indexer indexer;

    public void Index(Pair<AnalyzerType, SimilarityType> pair) {

        // boolean validAnalyzer = false;
        // boolean validSimilarity = false;
        // AnalyzerType analyzerType = null;
        // SimilarityType similarityType = null;

        // while (!validAnalyzer && !validSimilarity) {
        // // clear the screen
        // System.out.print("\033[H\033[2J");
        // System.out.flush();

        // System.out.println("Please choose an Analyser by selecting a number");
        // System.out.println("1. Simple");
        // System.out.println("2. English");
        // System.out.println("3. Custom");
        // System.out.println("4. Standard");

        // String input = System.console().readLine();

        // switch (input) {
        // case "1":
        // analyzerType = AnalyzerType.Simple;
        // validAnalyzer = true;
        // break;
        // case "2":
        // analyzerType = AnalyzerType.English;
        // validAnalyzer = true;
        // break;
        // case "3":
        // analyzerType = AnalyzerType.Custom;
        // validAnalyzer = true;
        // break;
        // case "4":
        // analyzerType = AnalyzerType.Standard;
        // validAnalyzer = true;
        // break;
        // default:
        // break;
        // }

        // System.out.println("Please choose a Similarity by selecting a number");
        // System.out.println("1. BM25Similarity");
        // System.out.println("2. BooleanSimilarity");
        // System.out.println("3. MultiSimilarity");
        // System.out.println("4. PerFieldSimilarityWrapper");
        // System.out.println("5. SimilarityBase");
        // System.out.println("6. TFIDFSimilarity");

        // input = System.console().readLine();

        // switch (input) {
        // case "1":
        // similarityType = SimilarityType.BM25;
        // validSimilarity = true;
        // break;
        // case "2":
        // similarityType = SimilarityType.Boolean;
        // validSimilarity = true;
        // break;
        // case "3":
        // similarityType = SimilarityType.Multi;
        // validSimilarity = true;
        // break;
        // case "4":
        // similarityType = SimilarityType.PerField;
        // validSimilarity = true;
        // break;
        // case "5":
        // similarityType = SimilarityType.Base;
        // validSimilarity = true;
        // break;
        // case "6":
        // similarityType = SimilarityType.TFIDF;
        // validSimilarity = true;
        // break;
        // default:
        // break;
        // }
        // }

        // if (analyzerType != null)
        // loadAndIndexDocs(analyzerType, similarityType);

        loadAndIndexDocs(pair.getLeft(), pair.getRight());
    }

    private static void loadAndIndexDocs(AnalyzerType analyzerType, SimilarityType similarityType) {
        try {
            FBISLoader fbisLoader = new FBISLoader();
            docs = fbisLoader.loadFBISDocs(Constants.DATASET_FILE_PATH + "fbis");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType, similarityType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FBIS documents");
        }

        try {
            FR94Loader fr94Loader = new FR94Loader();
            docs = fr94Loader.loadFR94Docs(Constants.DATASET_FILE_PATH + "fr94");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType, similarityType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FR94 documents");
        }

        try {
            FTLoader ftLoader = new FTLoader();
            docs = ftLoader.loadFTDocs(Constants.DATASET_FILE_PATH + "ft");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType, similarityType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FT documents");
        }

        try {
            LATimesLoader laTimesLoader = new LATimesLoader();
            docs = laTimesLoader.loadLaTimesDocs(Constants.DATASET_FILE_PATH + "latimes");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType, similarityType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading LATimes documents");
        }
    }
}