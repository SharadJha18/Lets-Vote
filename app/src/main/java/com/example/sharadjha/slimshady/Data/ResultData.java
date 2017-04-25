package com.example.sharadjha.slimshady.Data;

/**
 * Created by Sharad jha on 21-04-2017.
 */
public class ResultData {

    private CandidateData candidateData;
    private String name;
    private String winningCandidate;
    private int noOfVotes;
    private int candidateImage;
    private boolean declared;

    public ResultData() {
        this.declared = false;
        this.name = "winner";
    }

    public CandidateData getCandidateData() {
        return candidateData;
    }

    public String getWinningCandidate() {
        return winningCandidate;
    }

    public void setWinningCandidate(String winningCandidate) {
        this.winningCandidate = winningCandidate;
    }

    public boolean isDeclared() {
        return declared;
    }

    public void setDeclared(boolean declared) {
        this.declared = declared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCandidateData(CandidateData candidateData) {
        this.candidateData = candidateData;
    }

    public int getNoOfVotes() {
        return noOfVotes;
    }

    public void setNoOfVotes(int noOfVotes) {
        this.noOfVotes = noOfVotes;
    }

    public int getCandidateImage() {
        return candidateImage;
    }

    public void setCandidateImage(int candidateImage) {
        this.candidateImage = candidateImage;
    }
}