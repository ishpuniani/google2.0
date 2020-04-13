package com.mycompany.app.Search;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
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
import java.text.BreakIterator;
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
    //reference: https://github.com/kerinb/IR_proj2_group14
    private Query generateQueryFromTopic(Topic topic) {
        HashMap<String, Float> boosts = new HashMap<String, Float>();
        boosts.put("title", 0.1f);
        boosts.put("content",0.9f);

		MultiFieldQueryParser multiFieldQP = new MultiFieldQueryParser(new String[] {"headline","text" }, analyzer, boosts);
        
        Query query = null;
        try {
        	List<String> splitNarrative = splitNarrIntoRelNotRel(topic.getNarrative());
            String relevantNarr = splitNarrative.get(0).trim();
            String irrelevantNarr = splitNarrative.get(1).trim();

    		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

    		if (topic.getTitle().length() > 0) {

    			Query titleQuery = multiFieldQP.parse(QueryParser.escape(topic.getTitle()));
    			Query descriptionQuery = multiFieldQP.parse(QueryParser.escape(topic.getDesc()));
    			Query narrativeQuery = null;
    			Query negNarrativeQuery = null;
    			if(relevantNarr.length()>0) {
    				narrativeQuery = multiFieldQP.parse(QueryParser.escape(relevantNarr));
    			}
                if(irrelevantNarr.length()>0) {
                    negNarrativeQuery = multiFieldQP.parse(QueryParser.escape(irrelevantNarr));
                }

    			booleanQuery.add(new BoostQuery(titleQuery, (float) 4), BooleanClause.Occur.SHOULD);
    			booleanQuery.add(new BoostQuery(descriptionQuery, (float) 1.7), BooleanClause.Occur.SHOULD);

    			if (narrativeQuery != null) {
    				booleanQuery.add(new BoostQuery(narrativeQuery, (float) 1.2), BooleanClause.Occur.SHOULD);
    			}
    			
    			query = booleanQuery.build();
    		}
            
        } catch (ParseException e) {
            logger.error("Error parsing query.");
        }

        return query;
    }
    
    //https://github.com/kerinb/IR_proj2_group14    
    private static List<String> splitNarrIntoRelNotRel(String narrative) {
        StringBuilder relevantNarr = new StringBuilder();
        StringBuilder irrelevantNarr = new StringBuilder();
        List<String> splitNarrative = new ArrayList<>();

        BreakIterator bi = BreakIterator.getSentenceInstance();
        bi.setText(narrative);
        int index = 0;
        while (bi.next() != BreakIterator.DONE) {
            String sentence = narrative.substring(index, bi.current());

            if (!sentence.contains("not relevant") && !sentence.contains("irrelevant")) {
                relevantNarr.append(sentence.replaceAll(
                        "a relevant document identifies|a relevant document could|a relevant document may|a relevant document must|a relevant document will|a document will|to be relevant|relevant documents|a document must|relevant|will contain|will discuss|will provide|must cite",
                        ""));
            } else {
                irrelevantNarr.append(sentence.replaceAll("are also not relevant|are not relevant|are irrelevant|is not relevant|not|NOT", ""));
            }
            index = bi.current();
        }
        splitNarrative.add(relevantNarr.toString());
        splitNarrative.add(irrelevantNarr.toString());
        return splitNarrative;
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
