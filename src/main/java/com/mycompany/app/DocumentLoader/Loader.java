package com.mycompany.app.DocumentLoader;

import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.app.Constants.Constants;
import com.mycompany.app.DocumentLoader.fbis.FBISLoader;
import com.mycompany.app.DocumentLoader.fr94.FR94Loader;
import com.mycompany.app.DocumentLoader.ft.FTLoader;
import com.mycompany.app.DocumentLoader.laTimes.LATimesLoader;

import org.apache.lucene.document.Document;

public class Loader {

    private FBISLoader fbisLoader;
    private FR94Loader fr94Loader;
    private FTLoader ftLoader;
    private LATimesLoader laTimesLoader;
    private ArrayList<Document> allDocuments =new ArrayList<>();
    
    public Loader() {
        super();
        fbisLoader = new FBISLoader();
        fr94Loader = new FR94Loader();
        ftLoader = new FTLoader();
        laTimesLoader = new LATimesLoader();
    }

    public ArrayList<Document> LoadAllDocs() {
        try {
            allDocuments.addAll(fbisLoader.loadFBISDocs(Constants.DATASET_FILE_PATH + "fbis"));
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FBIS documents");
        }

        try {
            allDocuments.addAll(fr94Loader.loadFR94Docs(Constants.DATASET_FILE_PATH + "fr94"));
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FR94 documents");
        }

        try {
            allDocuments.addAll(ftLoader.loadFTDocs(Constants.DATASET_FILE_PATH + "ft"));
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading FT documents");
        }

        try {
            allDocuments.addAll(laTimesLoader.loadLaTimesDocs(Constants.DATASET_FILE_PATH + "latimes"));
        } catch (IOException ex) {
            System.out.println("ERROR: IOException occurred while loading LATimes documents");
        }

        return allDocuments;
    }
}