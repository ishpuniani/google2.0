//Reference:- https://github.com/kerinb/IR_proj2_group14/tree/master/src/com/kerinb/IR_proj2_group14

package com.mycompany.app.DocumentLoader.fr94;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FR94Loader {
	private static ArrayList<Document> fedRegisterDocList = new ArrayList<>();

    public ArrayList<Document> loadFR94Docs(String pathToFedRegister) throws IOException {
        System.out.println("Loading FR94 ...");
        File[] directories = new File(pathToFedRegister).listFiles(File::isDirectory);
        String docno,text;
        for (File directory : directories) {
            File[] files = directory.listFiles();
            for (File file : files) {
                org.jsoup.nodes.Document d = Jsoup.parse(file, null, "");

                Elements documents = d.select("DOC");

                for (Element document : documents) {

                    document.select("DOCTITLE").remove();
                    document.select("ADDRESS").remove();
                    document.select("SIGNER").remove();
                    document.select("SIGNJOB").remove();
                    document.select("BILLING").remove();
                    document.select("FRFILING").remove();
                    document.select("DATE").remove();
                    document.select("RINDOCK").remove();

                    docno = document.select("DOCNO").text();
                    text = document.select("TEXT").text();

                    addFedRegisterDoc(docno, text);
                }
            }
        }
        System.out.println("Loading FR94 Done!");
        return fedRegisterDocList;
    }

    private static void addFedRegisterDoc(String docno, String text) {
        Document doc = new Document();
        doc.add(new TextField("docno", docno, Field.Store.YES));
        doc.add(new TextField("text", text, Field.Store.YES));
        fedRegisterDocList.add(doc);
    }
}
