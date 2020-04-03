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

import org.apache.lucene.document.Document;

public class IndexDocuments {

    private static ArrayList<Document> docs;
    private static Indexer indexer;

    public static void main(String[] args) {

        boolean validInput = false;
        AnalyzerType analyzerType = null;

        while (!validInput) {
            // clear the screen
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Please choose an Analyser by selecting a number");
            System.out.println("1. Simple");
            System.out.println("2. English");
            System.out.println("3. Custom");
            System.out.println("4. Standard");

            String input = System.console().readLine();

            switch (input) {
                case "1":
                    analyzerType = AnalyzerType.Simple;
                    validInput = true;
                    break;
                case "2":
                    analyzerType = AnalyzerType.English;
                    validInput = true;
                    break;
                case "3":
                    analyzerType = AnalyzerType.Custom;
                    validInput = true;
                    break;
                case "4":
                    analyzerType = AnalyzerType.Standard;
                    validInput = true;
                    break;
                default:
                    break;
            }
        }
        if (analyzerType != null)
            loadAndIndexDocs(analyzerType);
    }

    private static void loadAndIndexDocs(AnalyzerType analyzerType) {
        try {
            FBISLoader fbisLoader = new FBISLoader();
            docs = fbisLoader.loadFBISDocs(Constants.DATASET_FILE_PATH + "fbis");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FBIS documents");
        }

        try {
            FR94Loader fr94Loader = new FR94Loader();
            docs = fr94Loader.loadFR94Docs(Constants.DATASET_FILE_PATH + "fr94");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FR94 documents");
        }

        try {
            FTLoader ftLoader = new FTLoader();
            docs = ftLoader.loadFTDocs(Constants.DATASET_FILE_PATH + "ft");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FT documents");
        }

        try {
            LATimesLoader laTimesLoader = new LATimesLoader();
            docs = laTimesLoader.loadLaTimesDocs(Constants.DATASET_FILE_PATH + "latimes");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
            indexer.IndexDocs(docs, analyzerType);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading LATimes documents");
        }
    }
}