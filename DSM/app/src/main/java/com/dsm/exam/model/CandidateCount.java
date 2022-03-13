package com.dsm.exam.model;

public class CandidateCount {
    private final String candidate;
    private final int count;
    private final double percent;

    public CandidateCount(String candidate, int count, double percent) {
        this.candidate = candidate;
        this.count = count;
        this.percent = percent;
    }

    public int getCount() {
        return count;
    }

    public String getCandidate() {
        return candidate;
    }

    public double getPercent() {
        return percent;
    }
}
