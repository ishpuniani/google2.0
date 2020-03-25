package com.mycompany.app.Search;

public class Result {

    private int queryId;
    private String documentId;
    private float score;

    public Result() {}

    public Result(int queryId, String documentId, float score) {
        this.queryId = queryId;
        this.documentId = documentId;
        this.score = score;
    }

    public int getQueryId() {
        return queryId;
    }

    public void setQueryId(int queryId) {
        this.queryId = queryId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%d\tITER\t%s\tRANK\t%f\tRUN\n", queryId, documentId, score);
    }
}