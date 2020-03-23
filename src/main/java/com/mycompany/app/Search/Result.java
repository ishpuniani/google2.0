package com.mycompany.app.Search;

public class Result {

    private int qid;
    private String did;
    private float score;

    public Result() {}

    public Result(int qid, String did, float score) {
        this.qid = qid;
        this.did = did;
        this.score = score;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%d\tITER\t%s\tRANK\t%f\tRUN\n", qid, did, score);
    }
}