package com.mycompany.app;

import java.util.ArrayList;

import com.mycompany.app.Constants.Constants;
import com.mycompany.app.DocumentLoader.Loader;
import com.mycompany.app.Index.Indexer;

import org.apache.lucene.document.Document;

public class IndexDocuments {

    public static void main(String[] args) {
        // load all the docs
        Loader loader = new Loader();
        ArrayList<Document> allDocs = loader.LoadAllDocs();

        // indexing
        Indexer indexer = new Indexer(Constants.INDEXED_DOCS_FILE_PATH);
        indexer.IndexDocs(allDocs);
        
    }
}