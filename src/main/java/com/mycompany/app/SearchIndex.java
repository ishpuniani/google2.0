package com.mycompany.app;

import com.mycompany.app.Constants.Constants;
import com.mycompany.app.Search.Result;
import com.mycompany.app.Search.ResultWriter;
import com.mycompany.app.Search.Searcher;
import com.mycompany.app.Search.Topic;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;

import java.util.List;

/** Simple command-line based search demo. */
public class SearchIndex {

    private static final Logger logger = Logger.getLogger(SearchIndex.class);

    public static void main(String[] args) throws Exception {
        String indexPath = "index";
        String resultsPath = "results.txt";
        Analyzer analyzer = new SimpleAnalyzer();
        Similarity similarity = new BM25Similarity();

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