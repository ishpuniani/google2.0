package com.mycompany.app.Index;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import com.mycompany.app.factory.AnalyzerFactory;
import com.mycompany.app.factory.AnalyzerType;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

    private String indexPath;

    public Indexer(String indexPath) {
        super();
        this.indexPath = indexPath;
    }

    public void IndexDocs(ArrayList<Document> docs, AnalyzerType analyzerType) {
        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");

            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = AnalyzerFactory.getAnalyzer(analyzerType);
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            IndexWriter writer = new IndexWriter(dir, iwc);

            for (Document document : docs) {
                writer.addDocument(document);
            }

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() + "\n while indexing with message: " + e.getMessage());
        }
    }
}