package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.app.Constants.Constants;
import com.mycompany.app.DocumentLoader.fbis.FBISLoader;
import com.mycompany.app.DocumentLoader.fr94.FR94Loader;
import com.mycompany.app.DocumentLoader.ft.FTLoader;
import com.mycompany.app.DocumentLoader.laTimes.LATimesLoader;
import com.mycompany.app.Index.Indexer;

import org.apache.lucene.document.Document;

public class IndexDocuments {

    public static void main(String[] args) {
        // load all the docs
        // Loader loader = new Loader();
        // ArrayList<Document> allDocs = loader.LoadAllDocs();
        ArrayList<Document> docs;
        Indexer indexer;

        try {
            FBISLoader fbisLoader = new FBISLoader();
            docs = fbisLoader.loadFBISDocs(Constants.DATASET_FILE_PATH + "fbis");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH + "fbis");
            indexer.IndexDocs(docs);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FBIS documents");
        }

        try {
            FR94Loader fr94Loader = new FR94Loader();
            docs = fr94Loader.loadFR94Docs(Constants.DATASET_FILE_PATH + "fr94");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH + "fr94");
            indexer.IndexDocs(docs);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FR94 documents");
        }

        try {
            FTLoader ftLoader = new FTLoader();
            docs = ftLoader.loadFTDocs(Constants.DATASET_FILE_PATH + "ft");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH + "ft");
            indexer.IndexDocs(docs);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FT documents");
        }

        try {
            LATimesLoader laTimesLoader = new LATimesLoader();
            docs = laTimesLoader.loadLaTimesDocs(Constants.DATASET_FILE_PATH + "latimes");
            indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH + "latimes");
            indexer.IndexDocs(docs);
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading LATimes documents");
        }
    }
}