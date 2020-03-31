package com.mycompany.app.Search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {
    private static final int NUM_TOP_HITS = 50;
    private static final Logger logger = Logger.getLogger(Searcher.class);

    private Analyzer analyzer;
    private Similarity similarity;
    private IndexSearcher indexSearcher;

    public Searcher(Analyzer analyzer, Similarity similarity) {
        this.analyzer = analyzer;
        this.similarity = similarity;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Similarity getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Similarity similarity) {
        this.similarity = similarity;
    }

    public void readIndex(String indexPath) {
        try {
            DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
            indexSearcher = new IndexSearcher(reader);
            indexSearcher.setSimilarity(similarity);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Read index failed");
            System.exit(1);
        }
    }

    public List<Result> search(Topic topic, int topHitsCount) {
        List<Result> results = new ArrayList<>();

        try {
            Query query = generateQueryFromTopic(topic);
            ScoreDoc[] hits = indexSearcher.search(query, topHitsCount).scoreDocs;

            for (ScoreDoc hit: hits) {
                Document doc = indexSearcher.doc(hit.doc);
                String docId = doc.get("docno");
                float score = hit.score;

                Result res = new Result();
                res.setDocumentId(docId);
                res.setScore(score);
                res.setQueryId(Integer.parseInt(topic.getNum()));
                results.add(res);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return results;
    }

    /**
     * Generate Query object to search the index from a topic object.
     * @param topic
     * @return
     */
    private Query generateQueryFromTopic(Topic topic) {
        HashMap<String, Float> boosts = new HashMap<String, Float>();
        boosts.put("title", 1f);
        boosts.put("content",10f);

		MultiFieldQueryParser multiFieldQP = new MultiFieldQueryParser(new String[] {"headline","text" }, analyzer);
        Query qT;
        Query qD;

        Query query = null;
        try {
            //TODO: Experiment with combinations of other fields.
            qT = multiFieldQP.parse(topic.getTitle());
            qD = multiFieldQP.parse(topic.getDesc());

//            String q = "";
//            q = expandQuery(topic.getDesc());
//            q += topic.getTitle();
//            query = multiFieldQP.parse(q);

//            org.apache.lucene.search.BooleanQuery.Builder bq = new BooleanQuery.Builder();
//            bq.add(qT, BooleanClause.Occur.SHOULD);
//            bq.add(qD, BooleanClause.Occur.SHOULD);

//            query = bq.build();
            query = qT;
        } catch (ParseException e) {
            logger.error("Error parsing query.");
        }

        return query;
    }

    public List<Result> searchAll(List<Topic> topics) {
        return searchAll(topics, NUM_TOP_HITS);
    }

    public List<Result> searchAll(List<Topic> topics, int hits) {
        List<Result> results = new ArrayList<>();
        for (Topic topic : topics) {
            List<Result> searchRes = search(topic, hits);
            results.addAll(searchRes);
        }
        return results;
    }

    /**
     * Parsing topics into the topic object from the topic file.
     * @param path topics file path
     * @return list of parsed topics
     */
    public List<Topic> parseTopics(String path) throws IOException {
        List<Topic> topics = new ArrayList<>();
        File input = new File(path);
        org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8", "");

        Elements tops = doc.select("top");
        for(Element topicElement : tops) {
            String numberStr = topicElement.getElementsByTag("num").text();
            String titleStr = topicElement.getElementsByTag("title").text();
            String descStr = topicElement.getElementsByTag("desc").text();
            String narrativeStr = topicElement.getElementsByTag("narr").text();

            Pattern numberPattern = Pattern.compile("(\\d+)");
            Matcher numberMatcher = numberPattern.matcher(numberStr);
            String number = "";
            if(numberMatcher.find()) {
                number = numberMatcher.group().trim();
            }

            descStr = descStr.replace("\n","");
            Pattern descPattern = Pattern.compile("Description: (.*)Narrative");
            Matcher descMatcher = descPattern.matcher(descStr);
            String desc = "";
            if(descMatcher.find()) {
                desc = descMatcher.group(1).trim();
            }

            String narrative = narrativeStr.replace("\n","").replace("Narrative: ","").trim();

            Topic topic = new Topic(number, titleStr, desc, narrative);
            topics.add(topic);
        }
        return topics;
    }
}
