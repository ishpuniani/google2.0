package com.mycompany.app;

import com.mycompany.app.Constants.Constants;
import com.mycompany.app.Search.Result;
import com.mycompany.app.Search.ResultWriter;
import com.mycompany.app.Search.Searcher;
import com.mycompany.app.Search.Topic;
import com.mycompany.app.factory.AnalyzerFactory;
import com.mycompany.app.factory.AnalyzerType;
import com.mycompany.app.factory.SimilarityFactory;
import com.mycompany.app.factory.SimilarityType;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.similarities.Similarity;

import java.util.List;

/** Simple command-line based search demo. */
public class SearchIndex {

    private static final Logger logger = Logger.getLogger(SearchIndex.class);

    public static void main(String[] args) throws Exception {
        String indexPath = Constants.INDEXED_DOCS_FILE_PATH;
        String resultsPath = "results.txt";

        boolean validAnalyzer = false;
        boolean validSimilarity = false;
        AnalyzerType analyzerType = null;
        SimilarityType similarityType = null;

        /*while (!validAnalyzer && !validSimilarity) {
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
                    validAnalyzer = true;
                    break;
                case "2":
                    analyzerType = AnalyzerType.English;
                    validAnalyzer = true;
                    break;
                case "3":
                    analyzerType = AnalyzerType.Custom;
                    validAnalyzer = true;
                    break;
                case "4":
                    analyzerType = AnalyzerType.Standard;
                    validAnalyzer = true;
                    break;
                default:
                    analyzerType = AnalyzerType.Custom;
                    validAnalyzer = true;
                    break;
            }

            System.out.println("Please choose a Similarity by selecting a number");
            System.out.println("1. BM25Similarity");
            System.out.println("2. BooleanSimilarity");
            System.out.println("3. MultiSimilarity");
            System.out.println("4. PerFieldSimilarityWrapper");
            System.out.println("5. SimilarityBase");
            System.out.println("6. TFIDFSimilarity");
            System.out.println("7. LMDirichletSimilarity");

            input = System.console().readLine();

            switch (input) {
                case "1":
                    similarityType = SimilarityType.BM25;
                    validSimilarity = true;
                    break;
                case "2":
                    similarityType = SimilarityType.Boolean;
                    validSimilarity = true;
                    break;
                case "3":
                    similarityType = SimilarityType.Multi;
                    validSimilarity = true;
                    break;
                case "4":
                    similarityType = SimilarityType.PerField;
                    validSimilarity = true;
                    break;
                case "5":
                    similarityType = SimilarityType.Base;
                    validSimilarity = true;
                    break;
                case "6":
                    similarityType = SimilarityType.TFIDF;
                    validSimilarity = true;
                    break;
                case "7":
                    similarityType = SimilarityType.LMDirichlet;
                    validSimilarity = true;
                    break;
                default:
                    similarityType = SimilarityType.BM25;
                    validSimilarity = true;
                    break;
            }
        }*/

//        indexPath += (analyzerType + "-" + similarityType);
        indexPath = Constants.INDEXED_DOCS_FILE_PATH + "PerFieldAnalyzer-BM25";

//        Analyzer analyzer = AnalyzerFactory.getAnalyzer(analyzerType);
        Analyzer analyzer = new MyAnalyzer(true);
        Similarity similarity = SimilarityFactory.getSimilarity(SimilarityType.BM25);

        Searcher searcher = new Searcher(analyzer, similarity);
        List<Topic> parsedTopics = searcher.parseTopics(Constants.TOPIC_FILE_PATH);
        logger.info("Parsed Topics");
        searcher.readIndex(indexPath);
        logger.info("Index read");
        List<Result> results = searcher.searchAll(parsedTopics);
        logger.info("Results generated");
        ResultWriter.write(results, resultsPath);
        logger.info(results.size() + " results written");
    }
}