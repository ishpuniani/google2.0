package com.mycompany.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.jsoup.Jsoup;

public class DocLoader {

  public List<Document> loadFbisDocs(String dataSource) {

    List<Document> documents = new ArrayList<Document>();

    File folder = new File(dataSource);
    for (final File file : folder.listFiles()) {
      String docId = null;
      try {
        docId = addFileToDoc(documents, file);
      } catch (IOException e) {
        System.out.println("Error loading Fbis: " + docId);
      }

    }

    System.out.println("Finished loading Fbis docs in memory.");
    return documents;
  }

  public List<Document> loadFr94Docs(String dataSource) {

    List<Document> documents = new ArrayList<Document>();

    File folder = new File(dataSource);
    for (final File file : folder.listFiles()) {
      String docId = null;
      try {
        docId = addFileToDoc(documents, file);
      } catch (IOException e) {
        System.out.println("Error loading Fr94Doc: " + docId);
      }

    }

    System.out.println("Finished loading Fr94 docs in memory.");
    return documents;
  }

  public List<Document> loadFtDocs(String dataSource) {

    List<Document> documents = new ArrayList<Document>();

    File folder = new File(dataSource);
    for (final File file : folder.listFiles()) {
      String docId = null;
      try {
        docId = addFileToDoc(documents, file);
      } catch (IOException e) {
        System.out.println("Error loading FtDoc: " + docId);
      }

    }

    System.out.println("Finished loading Ft docs in memory.");
    return documents;
  }

  public List<Document> loadLatTimesDocs(String dataSource) {

    List<Document> documents = new ArrayList<Document>();

    File folder = new File(dataSource);
    for (final File file : folder.listFiles()) {
      String docId = null;
      try {
        docId = addFileToDoc(documents, file);
      } catch (IOException e) {
        System.out.println("Error loading LatTimesDoc: " + docId);
      }

    }

    System.out.println("Finished loading LatTimes docs in memory.");
    return documents;
  }

  private String addFileToDoc(List<Document> documents, File file) throws IOException {
    // String content = new
    // String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    org.jsoup.nodes.Document doc = Jsoup.parse(file, "UTF-8", "");
    String title = doc.select("title").text();
    String content = doc.select("content").text();
    String docId = file.getName();

    Document luceneDoc = new Document();
    luceneDoc.add(new StringField("docId", docId, Field.Store.YES));
    luceneDoc.add(new TextField("title", title, Field.Store.YES));

    FieldType myFieldType = new FieldType(TextField.TYPE_STORED);
    myFieldType.setStoreTermVectors(true);

    // luceneDoc.add(new TextField("content", content, myFieldType));
    luceneDoc.add(new Field("content", content, myFieldType));

    documents.add(luceneDoc);

    return docId;
  }

}
